package com.treasuremount.petshop.Doctor.Veterinarian;

import com.treasuremount.petshop.DTO.UserDTO;
import com.treasuremount.petshop.DTO.VendorDTO;
import com.treasuremount.petshop.DTO.VendorRegisterDTO;
import com.treasuremount.petshop.DTO.VeterinarianCustomDTO;
import com.treasuremount.petshop.Doctor.Appointment.Appointment;
import com.treasuremount.petshop.Doctor.Appointment.AppointmentRepo;
import com.treasuremount.petshop.Doctor.Appointment.AppointmentStatus;
import com.treasuremount.petshop.Doctor.DoctorScheduleConfigRepo;
import com.treasuremount.petshop.Doctor.DoctorScheduleService;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.Service.UserService;
import com.treasuremount.petshop.utils.customMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class VeterinarianServiceImpl implements VeterinarianService {

    @Autowired
    private VeterinarianRepo repository;

    @Autowired
    private AppointmentRepo appointmentRepository;

    @Autowired
    private DoctorScheduleService doctorScheduleService;

    @Autowired
    private customMapper mapper;

    @Autowired
    private UserService userService;



    public List<LocalTime> getAvailableSlots(Long vetId, LocalDate curDate) {
       return doctorScheduleService.getAvailableSlots(vetId,curDate);
    }


    public List<Appointment> getBookedSlots(Long vetId) {
        List<Appointment> appointments = appointmentRepository.findByVeterinarianId(vetId);
       /* return appointments.stream()
                .map(appointment -> "Time: " + appointment.getStartTime() + ", Status: " + appointment.getStatus())
                .collect(Collectors.toList());*/

        return appointments;
    }



    @Override
   public Veterinarian getOneByUserId(Long userId){
        try {
           return repository.findByUserId(userId);

        } catch (Exception ex) {
            System.out.println("From pet respository");
            ex.printStackTrace();
            return null;
        }
    }




    @Override
    public Veterinarian create(Veterinarian d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            System.out.println("From pet respository");
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Veterinarian> getAll() {
        try {

            return repository.findAll();


        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }
    public List<VeterinarianRegistrationDTO> getAllVendors() {
        // Fetch all veterinarians from the repository
        List<Veterinarian> veterinarians = repository.findAll();

        // Map the list of veterinarians to a list of VeterinarianRegistrationDTO
        List<VeterinarianRegistrationDTO> vendorDTOs = veterinarians.stream()
                .map(veterinarian -> {
                    // Fetch the user associated with each veterinarian
                    User user = userService.findById(veterinarian.getUserId());

                    // Create a new VeterinarianRegistrationDTO and map the necessary data
                    VeterinarianRegistrationDTO dto = new VeterinarianRegistrationDTO(
                            mapper.toDTO(user, UserDTO.class),  // Map User to UserDTO
                            mapper.toDTO(veterinarian, VeterinarianDTO.class)  // Map Veterinarian to VeterinarianDTO
                    );
                    return dto;
                })
                .collect(Collectors.toList());

        return vendorDTOs;
    }

/*

    public List<VeterinarianCustomDTO> getAllVendors() {
      return repository.getAllCustomDTO();

    }
*/



    public List<VeterinarianCustomDTO> getAllByFilter(LocalDate date, LocalTime start, LocalTime end) {
        return getMappedVeterinarians( date,  start,  end);
        /*// Call the custom repository method to filter veterinarians by schedule day and time range
        List<Veterinarian> veterinarians = repository.findVeterinariansBySchedule(day, start, end);

        // Map the result to VeterinarianRegistrationDTO
        return veterinarians.stream()
                .map(veterinarian -> {
                    User user = veterinarian.getUser(); // Fetch the associated user
                    VeterinarianRegistrationDTO dto = new VeterinarianRegistrationDTO(
                            mapper.toDTO(user, UserDTO.class),
                            mapper.toDTO(veterinarian, VeterinarianDTO.class)
                    );
                    return dto;
                })
                .collect(Collectors.toList());*/
    }

    private List<VeterinarianCustomDTO> getMappedVeterinarians(LocalDate date, LocalTime start, LocalTime end) {

        DayOfWeek day=null;
        String dayInString=null;
        if(date != null){
            day=date.getDayOfWeek();
            dayInString=day.toString();
        }

        List<Object[]> results = repository.getAllCustomDTO(dayInString, start, end);

        return results.stream().map(obj -> new VeterinarianCustomDTO(
                ((Number) obj[0]).longValue(),  // id
                (String) obj[1],  // firstName
                (String) obj[2],  // lastName
                (String) obj[3],  // emailId
                (String) obj[4],  // mobileNumber
                (String) obj[5],  // imageUrl
                (String) obj[6],  // education
                (String) obj[7],  // specializations
                (obj[8] != null) ? ((Number) obj[8]).intValue() : null,  // yearsOfExperience
                (String) obj[9],  // city
                (String) obj[10], // language
                (String) obj[11], // animalType
                (String) obj[12]  // aboutMe
        )).collect(Collectors.toList());


    }




    @Override
    public Veterinarian getOneById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }



    @Override
    public Veterinarian update(Veterinarian d, Long id) {
        try {
            if (repository.existsById(id)) {
                d.setId(id);
                return repository.saveAndFlush(d);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

 /*   public Veterinarian bookedSlots(Boolean isBooked){

    }
*/

}
