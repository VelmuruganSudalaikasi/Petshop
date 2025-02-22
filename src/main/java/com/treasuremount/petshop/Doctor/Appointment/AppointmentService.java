package com.treasuremount.petshop.Doctor.Appointment;


import com.treasuremount.petshop.DTO.AppointmentDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AppointmentService {


    Appointment create(Appointment d);

    Appointment getOneById(Long id);

    List<Appointment> getAll();

    Appointment update(Appointment d, Long id);

     void updateAppointmentStatus(Long appointmentId, AppointmentStatus status);

     List<AppointmentDTO> getAllByCustom(Long Id, Boolean isVeterinarianId, AppointmentStatus status, LocalDate start, LocalDate end);

     List<Appointment> getAppointmentsByUserId(Long userId);

     void delete(Long id);
}
