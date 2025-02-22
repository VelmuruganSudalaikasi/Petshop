package com.treasuremount.petshop.Doctor.Appointment;
import com.treasuremount.petshop.DTO.AppointmentDTO;
import com.treasuremount.petshop.Doctor.DoctorScheduleService;
import com.treasuremount.petshop.Entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/api/public/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorScheduleService doctorScheduleService;


    @PostMapping("/add")
    public Appointment bookAppointment(
            @RequestBody Appointment appointment) {

        // Parse the startTime from Appointment object, and get other necessary data from the appointment
        LocalDate parsedStartDate = appointment.getAppointmentRequestedDate();
        LocalTime parsedStartTime=appointment.getStartTime();
        Long userId = appointment.getUserId();
        String petType = appointment.getPetType();
        String reason = appointment.getAppointmentReason();

        // Now use the updated service methoAppointment(appointment.getVeterinarianId(), userId, parsedStartDate,parsedStartTime, petType, reason);
        //    }d to book the appointment
        return doctorScheduleService.bookAppointment(appointment.getVeterinarianId(), userId, parsedStartDate,parsedStartTime, petType, reason);
    }

    @PutMapping("/appointments/status/{appointmentId}")
    public void updateAppointmentStatus(@PathVariable Long appointmentId, @RequestParam AppointmentStatus status) {
        appointmentService.updateAppointmentStatus(appointmentId, status);
    }



    // Update an appointment
  /*  @PutMapping("update/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        return appointmentService.update(appointment, id);
    }*/

    // Delete an appointment
    @DeleteMapping("/delete/{id}")
    public void cancelAppointment(@PathVariable Long id) {
        appointmentService.delete(id);
    }

    @GetMapping("/getAll/{Id}")
    public List<AppointmentDTO> getAllAppointmentsUserId(
            @PathVariable("Id") Long Id,
            @RequestParam(value = "isVeterinarianId", defaultValue = "true") Boolean isVeterinarianId,
            @RequestParam(value = "Status", required = false) AppointmentStatus status,
            @RequestParam (value = "startDate",required = false) String startDate,
            @RequestParam (value = "endDate",required = false) String endDate

            ) {

        LocalDate start=null;
        LocalDate end=null;
        if(startDate!= null){
            start=LocalDate.parse(startDate);
        }
        if(endDate!= null){
            end=LocalDate.parse(endDate);
        }

        return appointmentService.getAllByCustom(Id, isVeterinarianId, status,start,end);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Appointment> getOneUser(@PathVariable("id") Long id) {
        Appointment category = appointmentService.getOneById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(category);
    }

   /* @GetMapping("/getAllByUserId/{userId}")
    public List<Appointment>
    getAllAppointmentsUserId(@PathVariable("userId")Long userId) {

        return appointmentService.getAppointmentsByUserId(userId);
    }*/

    //patch method for updating the status
    //get method for respective doctor


//get All appointment By userId,VeterinarianId,Scheduled or not


//    @GetMapping("/getAll/{veterinarianId}")
//    public List<Appointment> getAllAppointmentsByVeterinarianId(@PathVariable("veterinarianId")Long veterinarianId) {
//        return appointmentService.get();
//    }


    // Fetch all appointments
    @GetMapping("/getAll")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAll();
    }
}
