package com.treasuremount.petshop.Doctor;
import com.treasuremount.petshop.Doctor.Appointment.Appointment;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/public/schedule")
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService doctorScheduleService;

    // Schedule an entire week for a veterinarian
    @PostMapping("/schedule/{vetId}")
    public ResponseEntity<?> createOrUpdateDaySchedule(
            @PathVariable Long vetId,
            @RequestBody DoctorScheduleConfig scheduleForDay) {


        if(scheduleForDay.getEndTime().isBefore(scheduleForDay.getStartTime())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("It works on 24 hour format it overlap with next day check the start and end"+scheduleForDay.getStartTime()
                            +scheduleForDay.getEndTime());
        }

        List<String> overlappingSchedules = doctorScheduleService.overlappingConfiguration(vetId,
                scheduleForDay.getId(),
                scheduleForDay);

        if (!overlappingSchedules.isEmpty()) {
            // Return overlapping schedules if conflicts exist
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Overlapping schedules detected.", "conflicts", overlappingSchedules));
        }

        doctorScheduleService.createOrUpdateDaySchedule(vetId, scheduleForDay);
        return ResponseEntity.ok().build();
    }

    //before deleting or updating schedule we will return any appointments has been placed in the future suppose it has means we don't
    // we notify the user some has been booked for the schedule so please select some other slot in that

    @PutMapping("update/{vetId}")
    public ResponseEntity<?> update(
            @PathVariable Long vetId,
            @RequestParam Long scheduleId,
            @RequestBody DoctorScheduleConfig scheduleForDay) {

        List<String> overlappingSchedules = doctorScheduleService.overlappingConfiguration(vetId, scheduleId, scheduleForDay);

        if (!overlappingSchedules.isEmpty()) {
            // Return overlapping schedules if conflicts exist
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Overlapping schedules detected.", "conflicts", overlappingSchedules));
        }


        List<String> overlappingIntervals = doctorScheduleService.checkOverlappingAppointments(vetId, scheduleForDay);

        if (!overlappingIntervals.isEmpty()) {
            // Return the overlapping intervals
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Between selected timing already appointment is booked");
        }

        doctorScheduleService.updateSchedule(vetId, scheduleId,scheduleForDay);
        return ResponseEntity.ok("Schedule updated successfully.");
    }

    @DeleteMapping("delete/{vetId}")
    public ResponseEntity<?> delete(
            @PathVariable("vetId") Long vetId,
            @RequestParam Long scheduleId) {
        DoctorScheduleConfig scheduleForDay=doctorScheduleService.getOneById(scheduleId);

        List<String> overlappingIntervals = doctorScheduleService.checkOverlappingAppointments(vetId, scheduleForDay);

        if (!overlappingIntervals.isEmpty()) {
            // Return the overlapping intervals
            return ResponseEntity.status(HttpStatus.CONFLICT).body(overlappingIntervals);
        }

        doctorScheduleService.deleteSchedule(vetId,scheduleId);
        return ResponseEntity.ok("Schedule deleted successfully.");
    }


    // Fetch the schedule for a specific day
    @GetMapping("/day-schedule/{vetId}")
    public List<DoctorScheduleConfig> getDaySchedule(@PathVariable Long vetId,
                                                     @Parameter(description = "Date for which the slots are to be fetched", required = true, example = "2025-01-03")
                                                     @RequestParam String date) {
        //create the pattern
        LocalDate parsedDate =LocalDate.parse(date);
        return doctorScheduleService.getDaySchedule(vetId, parsedDate.getDayOfWeek());
    }

    @GetMapping("/day-schedule/days/{vetId}")
    public List<DoctorScheduleConfig> getByDay(
            @PathVariable Long vetId,
            @RequestParam("Day")List<DayOfWeek> days){
        return doctorScheduleService.getSchedule(vetId,days);

    }



    // Get available slots for a specific date
    @GetMapping("/available-slots/{vetId}")
    public Map<String, List<String>> getAvailableSlots(@PathVariable Long vetId, @Parameter(description = "Date for which the slots are to be fetched", required = true, example = "2025-01-03")
    @RequestParam List<String> date) {
        try{
            List<LocalDate> dateList=date.stream().map(LocalDate::parse).toList();
            return doctorScheduleService.getAvailableSlotsForDates(vetId, dateList);
        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

    // List all booked slots for a veterinarian
    @GetMapping("/booked-slots/{vetId}")
    public List<Appointment> getAllBookedSlots(@PathVariable Long vetId) {
        return doctorScheduleService.getBookedSlots(vetId);
    }






}

