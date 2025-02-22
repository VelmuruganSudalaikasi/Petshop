package com.treasuremount.petshop.Doctor.Appointment;

import com.treasuremount.petshop.DTO.AppointmentDTO;
import com.treasuremount.petshop.Doctor.Veterinarian.VeterinarianServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepo repository;

    @Autowired
    private VeterinarianServiceImpl veterinarianService;

    @Override
    public Appointment create(Appointment appointment) {
        try {
            // Validate input
            if (appointment.getStartTime() == null || appointment.getEndTime() == null) {
                throw new IllegalArgumentException("Appointment start and end times must be provided.");
            }

            // Save and return the appointment
            return repository.save(appointment);
        } catch (Exception ex) {
            System.err.println("Error creating appointment:");
            ex.printStackTrace();
            return null;
        }
    }

    public List<Appointment> getAll(Long veterinarianId) {
        try {
            return repository.findByVeterinarianId(veterinarianId);
        } catch (Exception ex) {
            System.err.println("Error fetching all appointments:");
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Appointment> getAppointmentsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
    @Override
    public List<Appointment> getAll() {
        try {
            return repository.findAll();
        } catch (Exception ex) {
            System.err.println("Error fetching all appointments:");
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Appointment getOneById(Long id) {
        try {
            return repository.findById(id).orElse(null);
        } catch (Exception ex) {
            System.err.println("Error fetching appointment by ID:");
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Appointment update(Appointment appointment, Long id) {
        try {
            // Check if the appointment exists
            if (repository.existsById(id)) {
                // Set the ID to ensure the correct appointment is updated
                appointment.setId(id);

                // Save the updated appointment
                return repository.saveAndFlush(appointment);
            } else {
                System.err.println("Appointment with ID " + id + " does not exist.");
                return null;
            }
        } catch (Exception ex) {
            System.err.println("Error updating appointment:");
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        try {
            // Check if the appointment exists before attempting deletion
            if (repository.existsById(id)) {
                repository.deleteById(id);
            } else {
                System.err.println("Appointment with ID " + id + " does not exist.");
            }
        } catch (Exception ex) {
            System.err.println("Error deleting appointment:");
            ex.printStackTrace();
        }
    }


    @Override
    public void updateAppointmentStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = repository.findById(appointmentId).orElseThrow();
        appointment.setStatus(status);
        repository.save(appointment);
    }

    //userId or VeterinarianId
    public List<AppointmentDTO> getAllByCustom(Long Id, Boolean isVeterinarianId, AppointmentStatus status, LocalDate
                                               start,
                                               LocalDate end){
        List<AppointmentDTO> lst= repository.getAllUserOrVeterinarianIdAndStatus(Id,isVeterinarianId,status,start
        ,end);
        return lst ;
    }

}

