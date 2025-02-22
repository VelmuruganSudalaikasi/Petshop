package com.treasuremount.petshop.DTO;


import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.Service.UserService;
import com.treasuremount.petshop.Service.VendorService;
import com.treasuremount.petshop.utils.customMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
@Slf4j
public class VendorReg {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private UserService userService;

    @Autowired
    private customMapper mapper;

    User savedUser=null;
    Vendor savedVendor=null;


    public VendorRegisterDTO registerVendor(VendorRegisterDTO registrationDTO) {
        try {
            UserDTO userDTO = registrationDTO.getUserDTO();
            System.out.println("UserDTO: " + userDTO);

            User user = mapper.toEntity(userDTO, User.class);
            saveEntities(user, null);
            System.out.println("Mapped User: " + savedUser);

            VendorDTO vendorDTO = registrationDTO.getVendorDTO();
            Vendor vendor = mapper.toEntity(vendorDTO, Vendor.class);
            vendor.setUser(null);
            vendor.setUserId(savedUser.getId()); // Set the saved user's ID to vendor
            // Save the Vendor entity
            saveEntities(null, vendor);
            System.out.println("Mapped Vendor: " + savedVendor);

            return mapper.toVendorRegisterDTO(savedUser, savedVendor);
        }
        catch (Exception e) {
            log.error("Error occurred while creating accessory: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to process accessory creation at this time.");
        }
    }

    // remove all other logic to

    public void saveEntities(User user,Vendor vendor){
        if(user != null){

             savedUser = userService.create(user);
        }
        if(vendor !=null){

            savedVendor = vendorService.create(vendor);
        }

    }



}