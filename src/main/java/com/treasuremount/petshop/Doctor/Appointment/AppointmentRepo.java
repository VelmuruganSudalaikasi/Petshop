package com.treasuremount.petshop.Doctor.Appointment;
import com.treasuremount.petshop.DTO.AppointmentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository

public interface AppointmentRepo extends JpaRepository<Appointment,Long> {


        List<Appointment> findByVeterinarianIdAndAppointmentRequestedDateAndStartTimeBetween(
                Long veterinarianId,
                LocalDate appointmentRequestedDate,
                LocalTime startTime,
                LocalTime endTime
        );
        //date filter for query after that we want to move the filter the

      /*  List<Appointment> findByVeterinarianIdAndAppointmentRequestedDateAndStartTimeBetweenList(
                Long veterinarianId,
                List<LocalDate> appointmentRequestedDate,
                LocalTime startTime,
                LocalTime endTime
        );*/


        List<Appointment> findByVeterinarianId(Long veterinarianId);
        List<Appointment> findByUserId(Long userId);

        @Query("SELECT a FROM Appointment a " +
                "WHERE a.veterinarianId = :vetId " +
                "AND a.appointmentRequestedDate >= :currentDate " + // Only future or present dates
                "AND DAYOFWEEK(a.appointmentRequestedDate) = :dayOfWeek " + // Match DayOfWeek
                "AND a.startTime < :newEndTime AND a.endTime > :newStartTime") // Check time overlap
        List<Appointment> findOverlappingAppointments(
                @Param("vetId") Long vetId,
                @Param("dayOfWeek") Integer dayOfWeek, // Integer for MySQL DAYOFWEEK
                @Param("newStartTime") LocalTime newStartTime,
                @Param("newEndTime") LocalTime newEndTime,
                @Param("currentDate") LocalDate currentDate);




        @Query(value = """
    SELECT new com.treasuremount.petshop.DTO.AppointmentDTO(
        a.id, 
        a.veterinarianId, 
        a.userId, 
        a.petType, 
        a.appointmentReason, 
        a.appointmentRequestedDate, 
        a.startTime, 
        a.endTime, 
        a.createdDate, 
        a.status, 
        u.firstName, 
        u.mobileNumber, 
        pf.imageUrl,
        v.address, 
        v.city
    ) 
    FROM Appointment a
    JOIN Veterinarian v ON a.veterinarianId = v.id
    JOIN User u ON v.userId = u.id
    LEFT JOIN ProfileImage pf ON pf.userId=u.id
    WHERE 
    (0 =:Id  OR(
    (:isVeterinarianId IS NULL OR (
    ((:isVeterinarianId = true AND a.veterinarianId = :Id) 
    OR (:isVeterinarianId = false AND a.userId = :Id))
    )
    ) 
    )
    )
    AND (:status IS NULL OR a.status = :status)
    AND (:start IS NULL OR a.appointmentRequestedDate >= :start)
    AND (:end IS NULL OR a.appointmentRequestedDate <= :end)
    ORDER BY
    a.appointmentRequestedDate
""")
        List<AppointmentDTO> getAllUserOrVeterinarianIdAndStatus(@Param("Id") Long Id,
                                                                 @Param("isVeterinarianId") Boolean isVeterinarianId,
                                                                 @Param("status") AppointmentStatus status,
                                                                 @Param("start") LocalDate start,
                                                                 @Param("end") LocalDate end

        );







}
