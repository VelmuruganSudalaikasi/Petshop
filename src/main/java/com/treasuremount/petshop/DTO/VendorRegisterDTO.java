package com.treasuremount.petshop.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

public class VendorRegisterDTO {
    private UserDTO userDTO;
    private VendorDTO vendorDTO;

    public VendorRegisterDTO(UserDTO userDTO, VendorDTO vendorDTO) {
        this.userDTO = userDTO;
        this.vendorDTO = vendorDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public VendorDTO getVendorDTO() {
        return vendorDTO;
    }

    public void setVendorDTO(VendorDTO vendorDTO) {
        this.vendorDTO = vendorDTO;
    }

    @Override
    public String toString() {
        return "VendorRegisterDTO{" +
                "userDTO=" + userDTO +
                ", vendorDTO=" + vendorDTO +
                '}';
    }
}
