package com.treasuremount.petshop.Login;



import com.treasuremount.petshop.Doctor.Veterinarian.Veterinarian;
import com.treasuremount.petshop.Doctor.Veterinarian.VeterinarianService;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.Service.VendorService;
import com.treasuremount.petshop.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login-api")
public class LoginController {

    @Autowired
    private LoginService service;
    @Autowired
    Mapper map;
    @Autowired
    VendorService vendorService;

    @Autowired
    VeterinarianService veterinarianService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        User user = service.login(loginDTO);
        if (user != null) {
           LoginResponse response=map.toLoginDTO(user);
           Vendor vendor=vendorService.getOneUserById(response.getUserId());
            Veterinarian veterinarian=veterinarianService.getOneByUserId(response.getUserId());
           if(vendor!=null){
               //blunder mistake
               response.setVendorId(vendor.getId());
           }
           if(veterinarian !=null){
               response.setVeterinarianId(veterinarian.getId());
           }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED); // Or any other suitable response
        }
    }

//    private final Logger log = LoggerFactory.getLogger(LoginController.class);
//
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
//        try {
//            // Attempt login
//            LoginResponse response = service.login(loginRequest.getEmailId(), loginRequest.getPassword());
//
//            // Successful login
//            log.debug("Login successful for email: {}", loginRequest.getEmailId());
//            return ResponseEntity.ok(response);
//
//        } catch (UserNotFoundException e) {
//            // Handle user not found scenario
//            log.warn("User not found for email: {}", loginRequest.getEmailId());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//
//        } catch (IllegalArgumentException e) {
//            // Handle invalid credentials
//            log.warn("Invalid credentials for email: {}", loginRequest.getEmailId());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//
//        } catch (Exception e) {
//            // Handle unexpected errors
//            log.error("Unexpected error during login", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
//        }
//    }

}