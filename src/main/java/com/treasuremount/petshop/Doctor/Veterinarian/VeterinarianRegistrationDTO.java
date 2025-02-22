package com.treasuremount.petshop.Doctor.Veterinarian;

import com.treasuremount.petshop.DTO.UserDTO;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.utils.customMapper;
import org.springframework.beans.factory.annotation.Autowired;


public class VeterinarianRegistrationDTO {

    private UserDTO userDTO;
    private  VeterinarianDTO veterinarianDTO;


    public VeterinarianRegistrationDTO(UserDTO userDTO, VeterinarianDTO veterinarianDTO) {
        this.userDTO = userDTO;
        this.veterinarianDTO = veterinarianDTO;
    }


    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public VeterinarianDTO getVeterinarianDTO() {
        return veterinarianDTO;
    }

    public void setVeterinarianDTO(VeterinarianDTO veterinarianDTO) {
        this.veterinarianDTO = veterinarianDTO;
    }

    @Override
    public String toString() {
        return "VendorRegisterDTO{" +
                "userDTO=" + userDTO +
                ", vendorDTO=" + veterinarianDTO +
                '}';
    }
}
