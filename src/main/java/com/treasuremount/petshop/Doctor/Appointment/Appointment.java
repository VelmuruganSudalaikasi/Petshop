package com.treasuremount.petshop.Doctor.Appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Doctor.Veterinarian.Veterinarian;
import com.treasuremount.petshop.Entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import java.time.LocalTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Many Orders can belong to one User
    @JoinColumn(name = "veterinarian_id", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private Veterinarian veterinarian;

    @Column(name = "veterinarian_id")
    private Long veterinarianId;

    @ManyToOne(fetch = FetchType.LAZY) // Many Orders can belong to one User
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private User user;

    @Column(name = "userId")
    private Long userId;

    private String petType;
    @Lob
    private String appointmentReason;

    private LocalDate appointmentRequestedDate;

    @JsonIgnore
    private LocalDate appointmentRequestEndDate;

    @Schema(description = "Appointment end time", example = "2025-01-03 11:59")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Schema(description = "Appointment end time", example = "2025-01-03 11:59")
    @JsonFormat(pattern = "HH:mm")
    @JsonIgnore
    private LocalTime endTime;


    private LocalDate createdDate;

  /*
    @JsonIgnore
    private LocalDate scheduledDate;*/

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;



}
