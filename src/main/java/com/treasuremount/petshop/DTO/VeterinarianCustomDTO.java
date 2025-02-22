package com.treasuremount.petshop.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeterinarianCustomDTO {


    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobileNumber;

    private String imageUrl;
    private String education;
    private String Specializations;
    private Integer yearsOfExperience;
    private String city;
    private String language;
    private String animalType;
    private String aboutMe;

}
