package com.treasuremount.petshop.Doctor;

import com.treasuremount.petshop.Doctor.Appointment.Appointment;
import com.treasuremount.petshop.Doctor.Appointment.AppointmentRepo;
import com.treasuremount.petshop.Doctor.Appointment.AppointmentStatus;
import com.treasuremount.petshop.Doctor.Veterinarian.SlotNotAvailableException;
import com.treasuremount.petshop.Doctor.Veterinarian.Veterinarian;
import com.treasuremount.petshop.Doctor.Veterinarian.VeterinarianRepo;

import com.treasuremount.petshop.Repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

@Slf4j
@Service
public class DoctorScheduleService {

    @Autowired
    private DoctorScheduleConfigRepo doctorScheduleConfigRepository;

    @Autowired
    private VeterinarianRepo veterinarianRepository;

    @Autowired
    private AppointmentRepo appointmentRepository;

    @Autowired
    private UserRepo userRepository;


    public List<Appointment> getBookedSlots(Long vetId) {
        List<Appointment> appointments = appointmentRepository.findByVeterinarianId(vetId);
       /* return appointments.stream()
                .map(appointment -> "Time: " + appointment.getStartTime() + ", Status: " + appointment.getStatus())
                .collect(Collectors.toList());*/

        return appointments;
    }

    //Time based sort
    //pet based sort


    public List<String> overlappingConfiguration(Long vetId, Long scheduleId, DoctorScheduleConfig newSchedule) {
        // Get the day of the week for the new schedule
        DayOfWeek dayOfWeek = newSchedule.getDaysOfWeek();

        // Fetch existing schedules for the given veterinarian and day of the week
        List<DoctorScheduleConfig> existingSchedules = doctorScheduleConfigRepository
                .findByVeterinarianIdAndDaysOfWeek(vetId, dayOfWeek);

        // List to store any overlapping schedules
        List<String> overlappingSchedules = new ArrayList<>();

        // Check each existing schedule for overlap with the new schedule
        for (DoctorScheduleConfig existingSchedule : existingSchedules) {
            // Skip comparing with the schedule that's being updated (i.e., current schedule ID)
            if (existingSchedule.getId().equals(scheduleId)) {
                continue;
            }

            // Check if the new schedule overlaps with the existing schedule
            boolean isOverlapping = isOverlapping(existingSchedule.getStartTime(), existingSchedule.getEndTime(),
                    newSchedule.getStartTime(), newSchedule.getEndTime());

            // If there's an overlap, add it to the overlapping schedules list
            if (isOverlapping) {
                overlappingSchedules.add(String.format("Conflicting Schedule: %s - %s",
                        existingSchedule.getStartTime(), existingSchedule.getEndTime()));
            }
        }

        // Return the list of overlapping schedules (if any)
        return overlappingSchedules;
    }

    private boolean isOverlapping(LocalTime existingStart, LocalTime existingEnd,
                                  LocalTime newStart, LocalTime newEnd) {
        // Check if the new schedule overlaps with the existing one
        return !(newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd));
    }

    public List<DoctorScheduleConfig> getSchedule(Long vetId,List<DayOfWeek> days){
        return doctorScheduleConfigRepository.findByVeterinarianIdAndDaysOfWeekIn(vetId,days);
    }

    public void createOrUpdateDaySchedule(Long vetId, DoctorScheduleConfig schedule) {
        // Delete the existing schedule for that specific day
//        doctorScheduleConfigRepository.deleteByVeterinarianIdAndDaysOfWeek(vetId, dayOfWeek);

        Veterinarian veterinarian=veterinarianRepository.findById(vetId).orElseThrow();


            schedule.setVeterinarian(veterinarian);
            schedule.setDaysOfWeek(schedule.getDaysOfWeek());
            doctorScheduleConfigRepository.save(schedule);
    }

    private int convertDayOfWeekToMySQL(DayOfWeek dayOfWeek) {
        // Java's DayOfWeek (Monday=1, Sunday=7) -> MySQL's DAYOFWEEK (Sunday=1, Saturday=7)
        return dayOfWeek == DayOfWeek.SUNDAY ? 1 : dayOfWeek.getValue() + 1;
    }

    public List<String> checkOverlappingAppointments(Long vetId, DoctorScheduleConfig scheduleForDay) {
        try {
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            // Convert DayOfWeek for MySQL compatibility
            int mysqlDayOfWeek = convertDayOfWeekToMySQL(scheduleForDay.getDaysOfWeek());

            // Fetch overlapping appointments
            List<Appointment> overlappingAppointments = appointmentRepository.findOverlappingAppointments(
                    vetId,
                    mysqlDayOfWeek, // Converted dayOfWeek
                    scheduleForDay.getStartTime(),
                    scheduleForDay.getEndTime(),
                    currentDate
            );

            if (overlappingAppointments.isEmpty()) {
                log.info("No overlapping appointments found.");
                return List.of(); // Return an empty list if no overlaps
            }

            // Transform appointments into readable strings
            return overlappingAppointments.stream()
                    .map(appointment -> String.format(
                            "Start Time: %s, End Time: %s, Date: %s",
                            appointment.getStartTime(),
                            appointment.getEndTime(),
                            appointment.getAppointmentRequestedDate()))
                    .toList();

        } catch (Exception e) {
            log.error("Error checking overlapping appointments: {}", e.getMessage());
            throw new RuntimeException("Failed to check overlapping appointments", e);
        }
    }


    public void updateSchedule(Long vetId, Long scheduleId, DoctorScheduleConfig scheduleForDay) {
        // Fetch the existing schedule from the database
        DoctorScheduleConfig existingSchedule = doctorScheduleConfigRepository.findById(scheduleId)
                .orElseThrow(() -> new SlotNotAvailableException(
                        "Schedule not found for veterinarian ID " + vetId + " with schedule ID " + scheduleId
                ));

        // Update fields of the existing entity
        existingSchedule.setStartTime(scheduleForDay.getStartTime());
        existingSchedule.setEndTime(scheduleForDay.getEndTime());
        existingSchedule.setSlotDurationMinutes(scheduleForDay.getSlotDurationMinutes());
        existingSchedule.setDaysOfWeek(scheduleForDay.getDaysOfWeek());

        // Save the updated entity
        doctorScheduleConfigRepository.save(existingSchedule);
    }


    public void deleteSchedule(Long vetId,Long id) {
        // Fetch the list of schedules
        Optional<DoctorScheduleConfig> existingSchedules = doctorScheduleConfigRepository
                .findById(id);

        // Check if no schedule was found for the given veterinarian and day
        if (existingSchedules.isEmpty()) {
            throw new IllegalArgumentException("Schedule not found for vet ID " + vetId);
        }

        // If schedule exists, you can delete the first one (or handle multiple schedules if needed)
        DoctorScheduleConfig existingSchedule = existingSchedules.get();  // or use a more specific logic to select the schedule to delete
        doctorScheduleConfigRepository.deleteById(id);
    }

    public DoctorScheduleConfig getOneById(Long scheduleId){
        return doctorScheduleConfigRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Not  a configuration found"));
    }


    /**
     * Get available slots for a veterinarian on a specific date.
     */


    public List<LocalTime> getAvailableSlots(Long vetId, LocalDate date) {
        // Get the day of the week

        //date involved
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // Fetch the static schedule for the given day
        List<DoctorScheduleConfig> scheduleConfigs = doctorScheduleConfigRepository
                .findByVeterinarianIdAndDaysOfWeek(vetId, dayOfWeek);


        System.out.println("Schedule for {} {}"+vetId+ dayOfWeek + scheduleConfigs);

        // Fetch booked appointments for the given date
        List<Appointment> bookedAppointments = appointmentRepository
                .findByVeterinarianIdAndAppointmentRequestedDateAndStartTimeBetween(
                        vetId,
                        date,
                        LocalTime.of(0, 0),
                        LocalTime.of(23, 59)
                );


        System.out.println("available slot for {} " + bookedAppointments);

        bookedAppointments = bookedAppointments.stream()
                .filter(ele ->
                        ele.getStatus().equals(AppointmentStatus.SCHEDULED) ||
                                ele.getStatus().equals(AppointmentStatus.UNAVAILABLE) || ele.getStatus().equals(AppointmentStatus.PENDING)
                )
                .toList();


        System.out.println("filtered after the  scheduled and unavailable slot for {} " + bookedAppointments);


        // Extract booked time slots
        Set<LocalTime> bookedSlots = bookedAppointments.stream()
                .map(Appointment::getStartTime)
                .collect(Collectors.toSet());

        // Generate available slots



        List<LocalTime> availableSlots = new ArrayList<>();
        for (DoctorScheduleConfig config : scheduleConfigs) {
            LocalTime slotStart = config.getStartTime();
            while (slotStart.isBefore(config.getEndTime())) {
                if (!bookedSlots.contains(slotStart)) {
                    availableSlots.add(slotStart);
                }
                slotStart = slotStart.plusMinutes(config.getSlotDurationMinutes());
            }
        }
        return availableSlots;


    }

    /**
     * Get available slots for a veterinarian on a specific date.
     */

    public Map<String, List<String>> getAvailableSlotsForDates(Long vetId, List<LocalDate> dates) {
        // Map to store available slots for each date
        Map<String, List<String>> availableSlotsByDate = new HashMap<>();

        for (LocalDate date : dates) {
            // Get the day of the week for the current date
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            // Fetch the static schedule for the given day
            List<DoctorScheduleConfig> scheduleConfigs = doctorScheduleConfigRepository
                    .findByVeterinarianIdAndDaysOfWeek(vetId, dayOfWeek);

            System.out.println("Schedule for " + dayOfWeek + ": " + scheduleConfigs);

            // Fetch booked appointments for the given date
            List<Appointment> bookedAppointments = appointmentRepository
                    .findByVeterinarianIdAndAppointmentRequestedDateAndStartTimeBetween(
                            vetId,
                            date,
                            LocalTime.of(0, 0),
                            LocalTime.of(23, 59)
                    );

            System.out.println("Booked slots for " + date + ": " + bookedAppointments);

            // Filter appointments to only include scheduled ones
            bookedAppointments = bookedAppointments.stream()
                    .filter(ele ->
                            ele.getStatus().equals(AppointmentStatus.SCHEDULED) ||
                                    ele.getStatus().equals(AppointmentStatus.PENDING)
                    )
                    .toList();

            // Extract booked time slots
            Set<LocalTime> bookedSlots = bookedAppointments.stream()
                    .map(Appointment::getStartTime)
                    .collect(Collectors.toSet());

            // Generate available slots for the current date
            List<String> availableSlots = generateAvailableSlot(scheduleConfigs,bookedSlots);

            availableSlotsByDate.put(date.toString(), availableSlots); // Use date as the key
        }

        return availableSlotsByDate;
    }
    private List<String> generateAvailableSlot(List<DoctorScheduleConfig> scheduleConfigs, Set<LocalTime> bookedSlots) {

        List<String> availableSlots = new ArrayList<>(); // Store slots as "Start Time - End Time"
        for (DoctorScheduleConfig config : scheduleConfigs) {
            // Use a temporary LocalDate to handle time calculations
            LocalDate tempDate = LocalDate.of(2000, 1, 1); // Arbitrary date for calculation
            LocalDate nextDay = tempDate.plusDays(1); // Represents the next day for overflow handling

            LocalTime slotStart = config.getStartTime();
            LocalTime slotEnd = config.getEndTime();
            int slotDurationMinutes = config.getSlotDurationMinutes();

            // Validate the configuration to prevent infinite loops
            if (slotDurationMinutes <= 0) {
                throw new IllegalArgumentException("Slot duration must be greater than 0 minutes.");
            }

            // Convert start and end times to LocalDateTime for overflow-safe comparison
            LocalDateTime startDateTime = LocalDateTime.of(tempDate, slotStart);
            LocalDateTime endDateTime = LocalDateTime.of(tempDate, slotEnd);
            if (!startDateTime.isBefore(endDateTime)) {
                // If times cross midnight, adjust end time to the next day
                endDateTime = LocalDateTime.of(nextDay, slotEnd);
            }

            // Generate slots until we reach the adjusted end time
            while (startDateTime.isBefore(endDateTime)) {
                LocalDateTime nextSlotEnd = startDateTime.plusMinutes(slotDurationMinutes);

                // Ensure the slot doesn't exceed the configuration's end time
                if (nextSlotEnd.isAfter(endDateTime)) {
                    nextSlotEnd = endDateTime;
                }

                // Add the slot only if it's not booked
                if (!bookedSlots.contains(startDateTime.toLocalTime())) {
                    availableSlots.add(
                            startDateTime.toLocalTime() + " - " + nextSlotEnd.toLocalTime()
                    );
                }

                startDateTime = nextSlotEnd; // Move to the next slot
            }
        }

        return availableSlots;
    }


    private List<LocalTime> generateAvailableSlot1(List<DoctorScheduleConfig> scheduleConfigs, Set<LocalTime> bookedSlots) {

        List<LocalTime> availableSlots = new ArrayList<>(); // Store slots as "Start Time - End Time"
        for (DoctorScheduleConfig config : scheduleConfigs) {
            // Use a temporary LocalDate to handle time calculations
            LocalDate tempDate = LocalDate.of(2000, 1, 1); // Arbitrary date for calculation
            LocalDate nextDay = tempDate.plusDays(1); // Represents the next day for overflow handling

            LocalTime slotStart = config.getStartTime();
            LocalTime slotEnd = config.getEndTime();
            int slotDurationMinutes = config.getSlotDurationMinutes();

            // Validate the configuration to prevent infinite loops
            if (slotDurationMinutes <= 0) {
                throw new IllegalArgumentException("Slot duration must be greater than 0 minutes.");
            }

            // Convert start and end times to LocalDateTime for overflow-safe comparison
            LocalDateTime startDateTime = LocalDateTime.of(tempDate, slotStart);
            LocalDateTime endDateTime = LocalDateTime.of(tempDate, slotEnd);
            if (!startDateTime.isBefore(endDateTime)) {
                // If times cross midnight, adjust end time to the next day
                endDateTime = LocalDateTime.of(nextDay, slotEnd);
            }

            // Generate slots until we reach the adjusted end time
            while (startDateTime.isBefore(endDateTime)) {
                LocalDateTime nextSlotEnd = startDateTime.plusMinutes(slotDurationMinutes);

                // Ensure the slot doesn't exceed the configuration's end time
                if (nextSlotEnd.isAfter(endDateTime)) {
                    nextSlotEnd = endDateTime;
                }

                // Add the slot only if it's not booked
                if (!bookedSlots.contains(startDateTime.toLocalTime())) {
                    availableSlots.add(
                            startDateTime.toLocalTime()
                    );
                }

                startDateTime = nextSlotEnd; // Move to the next slot
            }
        }

        return availableSlots;
    }


    /*public List<LocalTime> getListOfAvailableSlots(Long vetId, List<LocalDate>  dateList) {
        // Get the day of the week

        //date involved
        DayOfWeek dayOfWeek = dateList.getDayOfWeek();
        List<DayOfWeek>  dayOfWeekList=dateList.stream().map(LocalDate::getDayOfWeek).toList();

        // Fetch the static schedule for the given day
        List<DoctorScheduleConfig> scheduleConfigs = doctorScheduleConfigRepository
                .findByVeterinarianIdAndDaysOfWeekList(vetId, dayOfWeekList);

        System.out.println("Schedule for {} {}"+ dayOfWeek + scheduleConfigs);

        // Fetch booked appointments for the given date
        List<Appointment> bookedAppointments = appointmentRepository
                .findByVeterinarianIdAndAppointmentRequestedDateAndStartTimeBetweenList(
                        vetId,
                        dateList,
                        LocalTime.of(0, 0),
                        LocalTime.of(23, 59)
                );


        System.out.println("available slot for {} " + bookedAppointments);

        bookedAppointments = bookedAppointments.stream().
                filter((ele) -> ele.getStatus().equals(AppointmentStatus.SCHEDULED)).toList();

        // Extract booked time slots
        Map<LocalDate, LocalTime> bookedSlots = bookedAppointments.stream()
                .collect(Collectors.toMap(
                        Appointment::getAppointmentRequestedDate, // Key: DayOfWeek
                        Appointment::getStartTime,                                              // Value: LocalTime
                        (existing, replacement) -> existing // Merge function for duplicates (use existing in this case)
                ));

        // Generate available slots
        List<LocalTime> availableSlots = new ArrayList<>();
        for (DoctorScheduleConfig config : scheduleConfigs) {
            LocalTime slotStart = config.getStartTime();

            while (slotStart.isBefore(config.getEndTime())) {
                if (!bookedSlots.contains(slotStart)) {
                    availableSlots.add(slotStart);
                }
                slotStart = slotStart.plusMinutes(config.getSlotDurationMinutes());
            }
        }
        return availableSlots;
    }
*/


    /**
     * Book an appointment for a veterinarian.
     */
    public Appointment bookAppointment(Long vetId, Long userId, LocalDate startDate, LocalTime startTime, String petType, String reason) {
        // Validate slot availability
        LocalDate date = startDate;

        List<LocalTime> availableSlots = getAvailableSlots(vetId, date);
        System.out.println("Available Slots: " + availableSlots);
        if (!availableSlots.contains(startTime)) {
            throw new SlotNotAvailableException("The selected slot is not available: " + startTime + " on " + date);
        }

        // Fetch the veterinarian's schedule for the specific day
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<DoctorScheduleConfig> scheduleConfigs = doctorScheduleConfigRepository
                .findByVeterinarianIdAndDaysOfWeek(vetId, dayOfWeek);

        // Find the schedule configuration that matches the start time
        // List of appointment has previously booked
        //checking the overlap is there given start and end time in the list of schedule
        // which slot it comes under this is pu written in the calculate the slot duration

        DoctorScheduleConfig matchingConfig = scheduleConfigs.stream()
                .filter(config -> !startTime.isBefore(config.getStartTime()) && startTime.isBefore(config.getEndTime()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching schedule found for the selected time: " + startTime));

        // Get the slot duration from the matching schedule
        int slotDurationMinutes = matchingConfig.getSlotDurationMinutes();

        // Create and save the appointment
        Appointment appointment = new Appointment();
        appointment.setVeterinarianId(vetId);
        appointment.setUserId(userId);
        appointment.setStartTime(startTime);
        appointment.setEndTime(startTime.plusMinutes(slotDurationMinutes)); // Use dynamic slot duration
        appointment.setPetType(petType);
        appointment.setAppointmentReason(reason);
        appointment.setAppointmentRequestedDate(startDate);
        appointment.setCreatedDate(LocalDate.now());
        appointment.setStatus(AppointmentStatus.PENDING);

        return appointmentRepository.save(appointment);
    }


    public List<DoctorScheduleConfig> getDaySchedule(Long vetId, DayOfWeek dayOfWeek) {
        // Fetch the schedule for the given veterinarian and day of the week
        return doctorScheduleConfigRepository.findByVeterinarianIdAndDaysOfWeek(vetId, dayOfWeek);
    }



}
