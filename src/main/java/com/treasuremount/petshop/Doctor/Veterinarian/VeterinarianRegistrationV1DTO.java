package com.treasuremount.petshop.Doctor.Veterinarian;


import com.treasuremount.petshop.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeterinarianRegistrationV1DTO {
    private UserDTO userDTO;
    private  VeterinarianV1DTO veterinarianDTO;
}
