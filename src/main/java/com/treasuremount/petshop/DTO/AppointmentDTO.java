package com.treasuremount.petshop.DTO;
import com.treasuremount.petshop.Doctor.Appointment.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Long id;
    private Long veterinarianId;
    private Long userId;
    private String petType;
    private String appointmentReason;
    private LocalDate appointmentRequestedDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate createdDate;
    private AppointmentStatus status;

    private String firstName;
    private String mobileNumber;
    private String imageUrl;
    private String address;
    private String city;


}
