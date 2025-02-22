package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.DTO.*;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.ExceptionHandler.DuplicateUserException;
import com.treasuremount.petshop.Service.UserService;
import com.treasuremount.petshop.Service.InventoryLocationService;
import com.treasuremount.petshop.Service.VendorService;
import com.treasuremount.petshop.utils.customMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/vendorReg")
public class VendorRegisterController {
    @Autowired
    private VendorService vendorService;

    @Autowired
    private UserService userService;

    @Autowired
    private customMapper mapper;

    @Autowired
    private VendorReg vendorReg;

    @Autowired
    private InventoryLocationService locationService;

    // Create Vendor
/*    @PostMapping("")
    public ResponseEntity<VendorRegisterDTO> createVendor(@RequestBody  VendorRegisterDTO vendorRegisterDTO) {
       try{
           VendorRegisterDTO savedVendorRegisterDTO=registerVendor(vendorRegisterDTO);
            return new ResponseEntity<>(savedVendorRegisterDTO, HttpStatus.CREATED);
        }catch (Exception e){
           e.printStackTrace();
           return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
       }

    }*/

    @PostMapping("/add")
    public ResponseEntity<?> createVendor(@RequestBody @Valid VendorRegisterDTO registrationDTO) {
        try {
            VendorRegisterDTO result =vendorReg.registerVendor(registrationDTO);
            return ResponseEntity.ok(result);
        } catch (DuplicateUserException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // Get All Vendors
    @GetMapping("/getAll")
    public ResponseEntity<List<VendorRegisterDTO>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAll();
        List<Long> userIds = vendors.stream()
                .map(Vendor::getUserId) // Assuming Vendor is the class and getUserId() is the method
                .collect(Collectors.toList());

// Step 2: Fetch the list of User objects using userService
            List<User> userList = userService.getAllByUserIds(userIds);

// Step 3: Create a map of userId -> User for quick lookup
        Map<Long, User> userIdToUserMap = userList.stream()
                .collect(Collectors.toMap(User::getId, user -> user));

// Step 4: Create a map of vendorId -> respective User
        Map<Long, User> vendorIdToUserMap = vendors.stream()
                .collect(Collectors.toMap(
                        Vendor::getId, // Key: vendorId (assuming Vendor has a getId() method)
                        vendor -> userIdToUserMap.get(vendor.getUserId()) // Value: respective User
                ));
        List<VendorRegisterDTO> vendorDTOs = vendors.stream().map(vendor -> {
//            User user = userService.findById(vendor.getUserId());
            VendorRegisterDTO dto = new VendorRegisterDTO(
                    mapper.toDTO(vendorIdToUserMap.get(vendor.getId()), UserDTO.class),
                    mapper.toDTO(vendor, VendorDTO.class)
            );

            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(vendorDTOs, HttpStatus.OK);
    }

    // Get Vendor by ID
    @GetMapping("/getOne/{id}")
    public ResponseEntity<VendorRegisterDTO> getVendorById(@PathVariable Long id) {
        Vendor vendor = vendorService.findById(id);
        User user = userService.findById(vendor.getUserId());

        VendorRegisterDTO dto = new VendorRegisterDTO(
                mapper.toDTO(user, UserDTO.class),
                mapper.toDTO(vendor, VendorDTO.class)
        );


        return ResponseEntity.ok(dto);
    }

    // Update Vendor
    @PutMapping("/update/{id}")
    public ResponseEntity<VendorRegisterDTO> updateVendor(
            @PathVariable Long id,
            @RequestBody @Valid VendorRegisterDTO vendorRegisterDTO) {
        User newUser=mapper.toDTO(vendorRegisterDTO.getUserDTO(),User.class);
        Vendor newVendor=mapper.toDTO(vendorRegisterDTO.getVendorDTO(),Vendor.class);

        Vendor vendor = vendorService.findById(id);
        User user = userService.findById(vendor.getUserId());
        System.err.println(vendor);
        System.err.println(user);

        newUser.setId(user.getId());
        User savedUser=userService.update(newUser, user.getId());

        newVendor.setUserId(user.getId());
        Vendor savedVendor=vendorService.update(newVendor, vendor.getId());
        vendorRegisterDTO=null;
        if(savedUser!=null && savedVendor!=null)
            vendorRegisterDTO = mapper.toVendorRegisterDTO(savedUser, savedVendor);

        if(vendorRegisterDTO==null){
            System.err.println(savedUser);
            System.err.println(savedVendor);
        }
        return ResponseEntity.ok(vendorRegisterDTO);
    }

    // Delete Vendor
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {
        Vendor vendor = vendorService.findById(id);
        userService.delete(vendor.getUserId());
        vendorService.delete(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }

    @GetMapping("/getAll/custom1")
    public ResponseEntity<List<VendorInfoDTO>> getAllCustom1(){
        List<VendorInfoDTO> response=vendorService.getAllCustom1();
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/getOne/userId/{id}")
    public ResponseEntity<Vendor> getAllCustom1(
            @PathVariable("id") Long userId
    ){
        Vendor response=vendorService.getOneUserById(userId);
        return  ResponseEntity.ok(response);
    }




}
