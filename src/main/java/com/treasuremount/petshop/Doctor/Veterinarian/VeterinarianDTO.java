package com.treasuremount.petshop.Doctor.Veterinarian;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeterinarianDTO {
    private Long id;
    private String education;
    private Integer yearsOfExperience;
    private String Specializations;
    private String address;
    private String city;
    private Long CountryId;
    private Long StateId;
    private String language;
    private String animalType;
    private String aboutMe;
}
