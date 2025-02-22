package com.treasuremount.petshop.Doctor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Doctor.Veterinarian.Veterinarian;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorScheduleConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    @JsonIgnore
    @ToString.Exclude
    private Veterinarian veterinarian;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private Integer slotDurationMinutes;

    @Enumerated(EnumType.STRING)
    private DayOfWeek daysOfWeek;

    @Override
    public String toString() {
        return "DoctorScheduleConfig{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", slotDurationMinutes=" + slotDurationMinutes +
                ", daysOfWeek=" + daysOfWeek +
                '}';
    }
}
