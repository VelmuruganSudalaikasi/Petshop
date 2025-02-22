package com.treasuremount.petshop.Doctor.Veterinarian;
import com.treasuremount.petshop.DTO.UserDTO;
import com.treasuremount.petshop.DTO.VeterinarianCustomDTO;
import com.treasuremount.petshop.Doctor.Appointment.Appointment;
import com.treasuremount.petshop.Doctor.Appointment.AppointmentStatus;
import com.treasuremount.petshop.Doctor.DoctorScheduleService;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.ExceptionHandler.DuplicateUserException;
import com.treasuremount.petshop.Profile.ProfileImage;
import com.treasuremount.petshop.Profile.ProfileImageService;
import com.treasuremount.petshop.Service.UserService;
import com.treasuremount.petshop.utils.customMapper;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/veterinarian")
public class VeterinarianController {

    @Autowired
    private VeterinarianServiceImpl service;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorScheduleService doctorScheduleService;


    @Autowired
    private customMapper mapper;

    @Autowired
    private ProfileImageService imageService;

    // Fetch all available slots for a veterinarian
  /*  @GetMapping("available-slots/{vetId}")
    public List<LocalTime> listAvailableSlots(@PathVariable Long vetId, @RequestParam LocalDate curDate) {
        return service.getAvailableSlots(vetId,curDate);
    }

    // Change the status of a booked slot

    // List all booked slots for a veterinarian
    @GetMapping("/booked-slots/{vetId}")
    public List<Appointment> getAllBookedSlots(@PathVariable Long vetId) {
        return service.getBookedSlots(vetId);
    }
*/


    @PostMapping("/add")
    @Transactional
    public ResponseEntity<?> createVendor(@RequestBody VeterinarianRegistrationDTO registrationDTO) {
        try {


            VeterinarianRegistrationDTO result = registrationVeterinarian(registrationDTO);
            return ResponseEntity.ok(result);
        } catch (DuplicateUserException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @GetMapping("/getAll")
    public ResponseEntity<List<VeterinarianCustomDTO>> getAllVendors(
            @Parameter(description = "Date for which the slots are to be fetched", example = "2025-01-03")
            @RequestParam(value = "date", required = false) LocalDate date,
            @RequestParam(value = "start", required = false) String start,   // Time as String
            @RequestParam(value = "end", required = false) String end        // Time as String
    ) {
        List<VeterinarianCustomDTO> veterinarianCustomDTOS;

        // Convert start and end times to LocalTime if provided
        LocalTime startTime = (start != null) ? LocalTime.parse(start, timeFormatter) : null;
        LocalTime endTime = (end != null) ? LocalTime.parse(end, timeFormatter) : null;

        // If no filter parameters are provided, fetch all veterinarians


        veterinarianCustomDTOS = service.getAllByFilter(date, startTime, endTime);  // Apply filter if parameters are provided


        return new ResponseEntity<>(veterinarianCustomDTOS, HttpStatus.OK);
    }



 /*   @GetMapping("/getAll")
    public ResponseEntity<List<VeterinarianCustomDTO>> getAllVendors(

    ) {
        List<VeterinarianCustomDTO> veterinarianCustomDTOS;

        // Convert start and end times to LocalTime if provided


        // If no filter parameters are provided, fetch all veterinarians

        veterinarianCustomDTOS = service.getAllByFilter(date, startTime, endTime);  // Apply filter if parameters are provided


        return new ResponseEntity<>(veterinarianCustomDTOS, HttpStatus.OK);
    }*/
    @GetMapping("/getOne/{id}")
    public ResponseEntity<VeterinarianRegistrationV1DTO> getVendorById(@PathVariable Long id) {
        Veterinarian vendor = service.getOneById(id);
        User user = userService.findById(vendor.getUserId());

        VeterinarianRegistrationV1DTO dto = new VeterinarianRegistrationV1DTO();
                dto.setUserDTO(mapper.toDTO(user, UserDTO.class));
                VeterinarianV1DTO veterinarian=mapper.toDTO(vendor, VeterinarianV1DTO.class);
                dto.setVeterinarianDTO(veterinarian);
        ProfileImage image= imageService.getOneByUserId(user.getId());
        if(image != null){

            veterinarian.setImageUrl(image.getImageUrl());
        }


        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VeterinarianRegistrationDTO> updateVendor(
            @PathVariable Long id,
            @RequestBody @Valid VeterinarianRegistrationDTO vendorRegisterDTO) {
        User newUser=mapper.toDTO(vendorRegisterDTO.getUserDTO(),User.class);
        Veterinarian newVendor=mapper.toDTO(vendorRegisterDTO.getVeterinarianDTO(),Veterinarian.class);

        Veterinarian vendor = service.getOneById(id);

        User user = userService.findById(vendor.getUserId());
        System.err.println(vendor);
        System.err.println(user);

        newUser.setId(user.getId());
        User savedUser=userService.update(newUser, user.getId());

        newVendor.setUserId(user.getId());
        Veterinarian savedVendor=service.update(newVendor, vendor.getId());
        vendorRegisterDTO=null;
        if(savedUser!=null && savedVendor!=null)
            vendorRegisterDTO = mapper.toVeterinarianRegistrationDTO(savedUser, savedVendor);

        if(vendorRegisterDTO==null){
            System.err.println(savedUser);
            System.err.println(savedVendor);
        }
        return ResponseEntity.ok(vendorRegisterDTO);
    }

    // Delete Vendor
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {
        Veterinarian vendor = service.getOneById(id);
        userService.delete(vendor.getUserId());
        vendor.setUserId(0L);
        service.delete(id);
        return ResponseEntity.ok("veterianan deleted successfully");
    }

    @Transactional
    private VeterinarianRegistrationDTO registrationVeterinarian( VeterinarianRegistrationDTO registrationDTO ) {
        UserDTO userDTO = registrationDTO.getUserDTO();

        System.out.println("UserDTO: " + userDTO);

        User user = mapper.toEntity(userDTO, User.class);
        User savedUser = userService.create(user); // Save the User entity


        // Logging after mapping
        System.out.println("Mapped User: " + savedUser);

        VeterinarianDTO veterinarianDTO = registrationDTO.getVeterinarianDTO();
        Veterinarian veterinarian = mapper.toEntity(veterinarianDTO, Veterinarian.class);

        veterinarian.setUserId(savedUser.getId()); // Set the saved user's ID to vendor
        Veterinarian savedVendor = service.create(veterinarian); // Save the Vendor entity

        System.out.println("Mapped Vendor: " + savedVendor);

        return mapper.toVeterinarianRegistrationDTO(savedUser, savedVendor);

    }


    //Get All the doctor based on available schedule













/*

    @PostMapping("/add")
    public ResponseEntity<Veterinarian> createUser(@RequestBody @Valid Veterinarian user) {
        Veterinarian createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Veterinarian>> getAllUsers() {
        List<Veterinarian> users = service.getAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Veterinarian> getOneUser(@PathVariable("id") Long id) {
        Veterinarian user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Veterinarian> updateUser(@RequestBody @Valid Veterinarian user, @PathVariable("id") Long id) {
        Veterinarian updatedUser = service.update(user, id);
        if (updatedUser != null) {
            return ResponseEntity.ok().body(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }
*/

}
