package com.treasuremount.petshop.Doctor;

import com.treasuremount.petshop.Doctor.Appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DoctorScheduleConfigRepo extends JpaRepository<DoctorScheduleConfig, Long> {

    List<DoctorScheduleConfig> findByVeterinarianIdAndDaysOfWeek(Long veterinarianId, DayOfWeek dayOfWeek);
    List<DoctorScheduleConfig> findByVeterinarianIdAndDaysOfWeekIn(Long veterinarianId, List<DayOfWeek> dayOfWeek);
/*
    @Query("SELECT d FROM DoctorScheduleConfig d WHERE d.veterinarianId = :veterinarianId AND d.daysOfWeek IN :daysOfWeek")
    List<DoctorScheduleConfig> findByVeterinarianIdAndDaysOfWeekList(@Param("veterinarianId") Long veterinarianId,
                                                                     @Param("daysOfWeek") List<DayOfWeek> dayOfWeek);*/

    void deleteByVeterinarianId(Long veterinarianId);
    void deleteByVeterinarianIdAndDaysOfWeek(Long veterinarianId, DayOfWeek dayOfWeek);



    List<Appointment> findByVeterinarianId(Long veterinarianId);
}
