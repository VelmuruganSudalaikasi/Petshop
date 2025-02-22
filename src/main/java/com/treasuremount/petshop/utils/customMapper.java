package com.treasuremount.petshop.utils;

import com.treasuremount.petshop.DTO.*;
import com.treasuremount.petshop.Doctor.Veterinarian.Veterinarian;
import com.treasuremount.petshop.Doctor.Veterinarian.VeterinarianDTO;
import com.treasuremount.petshop.Doctor.Veterinarian.VeterinarianRegistrationDTO;
import com.treasuremount.petshop.Doctor.Veterinarian.VeterinarianV1DTO;
import com.treasuremount.petshop.Entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class customMapper {
    private final ModelMapper modelMapper;

    public customMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true).setSkipNullEnabled(true); ;
        configureMappings();
    }

    // Converts an Entity to a DTO
    public <T, U> U toDTO(T source, Class<U> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    // Converts a DTO to an Entity
    public <T, U> T toEntity(U source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public VendorRegisterDTO toVendorRegisterDTO(User user, Vendor vendor) {
        UserDTO userDTO = toDTO(user, UserDTO.class);
        VendorDTO vendorDTO = toDTO(vendor, VendorDTO.class);

        return new VendorRegisterDTO(userDTO, vendorDTO);
    }
    public VeterinarianRegistrationDTO toVeterinarianRegistrationDTO(User user, Veterinarian veterinarian){
        UserDTO userDTO = toDTO(user, UserDTO.class);
        VeterinarianDTO veterinarianDTO=toDTO(veterinarian,VeterinarianDTO.class);
        return new VeterinarianRegistrationDTO(userDTO,veterinarianDTO);
    }

    public UserDTO toUserDTO(User user){
        UserDTO userDTO = toDTO(user, UserDTO.class);
        return userDTO;
    }

    // Configuration for Custom Mappings
    private void configureMappings() {
        // Mapping for Vendor -> VendorDTO
        modelMapper.typeMap(Vendor.class, VendorDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Vendor::getId, VendorDTO::setId);
                    mapper.map(Vendor::getShopName, VendorDTO::setShopName);
                    mapper.map(Vendor::getContactDetails, VendorDTO::setContactDetails);
                    mapper.map(Vendor::getTaxId, VendorDTO::setTaxId);
                    mapper.map(Vendor::getRegistrationNumber, VendorDTO::setRegistrationNumber);
                    mapper.map(Vendor::getUserId, VendorDTO::setUserId);
                    mapper.map(Vendor::getAddress, VendorDTO::setAddress);
                    mapper.map(Vendor::getCountryId, VendorDTO::setCountryId);
                    mapper.map(Vendor::getCity, VendorDTO::setCity);
                    mapper.map(Vendor::getStateId, VendorDTO::setStateId);
                    mapper.map(Vendor::getGstNumber, VendorDTO::setGstNumber);
                    mapper.map(Vendor::getCreatedDate, VendorDTO::setCreatedDate);
                    mapper.map(Vendor::getModifiedDate, VendorDTO::setModifiedDate);
                    mapper.map(Vendor::getPostalCode, VendorDTO::setPostalCode);
                });

        // Mapping for VendorDTO -> Vendor
        modelMapper.typeMap(VendorDTO.class, Vendor.class)
                .addMappings(mapper -> {
                    mapper.map(VendorDTO::getId, Vendor::setId);
                    mapper.map(VendorDTO::getShopName, Vendor::setShopName);
                    mapper.map(VendorDTO::getContactDetails, Vendor::setContactDetails);
                    mapper.map(VendorDTO::getTaxId, Vendor::setTaxId);
                    mapper.map(VendorDTO::getRegistrationNumber, Vendor::setRegistrationNumber);
                    mapper.map(VendorDTO::getUserId, Vendor::setUserId);
                    mapper.map(VendorDTO::getAddress, Vendor::setAddress);
                    mapper.map(VendorDTO::getCountryId, Vendor::setCountryId);
                    mapper.map(VendorDTO::getCity, Vendor::setCity);
                    mapper.map(VendorDTO::getGstNumber, Vendor::setGstNumber);
                    mapper.map(VendorDTO::getStateId, Vendor::setStateId);
                    mapper.map(VendorDTO::getCreatedDate, Vendor::setCreatedDate);
                    mapper.map(VendorDTO::getModifiedDate, Vendor::setModifiedDate);
                    mapper.map(VendorDTO::getPostalCode,Vendor::setPostalCode);
                });

        // Mapping for User -> UserDTO
        modelMapper.typeMap(User.class, UserDTO.class)
                .addMappings(mapper -> {
                    mapper.map(User::getId, UserDTO::setId);
                    mapper.map(User::getFirstName, UserDTO::setFirstName);
                    mapper.map(User::getLastName, UserDTO::setLastName);
                    mapper.map(User::getEmailId, UserDTO::setEmailId);
                    mapper.map(User::getMobileNumber, UserDTO::setMobileNumber);
                    mapper.map(User::getActiveStatus, UserDTO::setActiveStatus);
                    mapper.map(User::getRoleId, UserDTO::setRoleId);
                    mapper.map(User::getCreatedDate, UserDTO::setCreatedDate);
                    mapper.map(User::getModifiedDate, UserDTO::setModifiedDate);
                });

        // Mapping for UserDTO -> User
        modelMapper.typeMap(UserDTO.class, User.class)
                .addMappings(mapper -> {
                    mapper.map(UserDTO::getId, User::setId);
                    mapper.map(UserDTO::getFirstName, User::setFirstName);
                    mapper.map(UserDTO::getLastName, User::setLastName);
                    mapper.map(UserDTO::getEmailId, User::setEmailId);
                    mapper.map(UserDTO::getMobileNumber, User::setMobileNumber);
                    mapper.map(UserDTO::getActiveStatus, User::setActiveStatus);
                    mapper.map(UserDTO::getRoleId, User::setRoleId);
                    mapper.map(UserDTO::getCreatedDate, User::setCreatedDate);
                    mapper.map(UserDTO::getModifiedDate, User::setModifiedDate);
                });

        // Mapping for Veterinarian -> VeterinarianDTO

        modelMapper.typeMap(Veterinarian.class, VeterinarianDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Veterinarian::getId, VeterinarianDTO::setId);
                    mapper.map(Veterinarian::getEducation, VeterinarianDTO::setEducation);
                    mapper.map(Veterinarian::getYearsOfExperience, VeterinarianDTO::setYearsOfExperience);
                    mapper.map(Veterinarian::getAddress, VeterinarianDTO::setAddress);
                    mapper.map(Veterinarian::getLanguage, VeterinarianDTO::setLanguage);
                    mapper.map(Veterinarian::getAnimalType, VeterinarianDTO::setAnimalType);
                    mapper.map(Veterinarian::getAddress, VeterinarianDTO::setAddress);
                    mapper.map(Veterinarian::getCountryId, VeterinarianDTO::setCountryId);
                    mapper.map(Veterinarian::getStateId, VeterinarianDTO::setStateId);
                    mapper.map(Veterinarian::getSpecializations, VeterinarianDTO::setSpecializations);
                    mapper.map(Veterinarian::getAboutMe, VeterinarianDTO::setAboutMe);

                });

        // Mapping for Veterinarian -> VeterinarianDTO

        modelMapper.typeMap(Veterinarian.class, VeterinarianV1DTO.class)
                .addMappings(mapper -> {
                    mapper.map(Veterinarian::getId, VeterinarianV1DTO::setId);
                    mapper.map(Veterinarian::getEducation, VeterinarianV1DTO::setEducation);
                    mapper.map(Veterinarian::getYearsOfExperience, VeterinarianV1DTO::setYearsOfExperience);
                    mapper.map(Veterinarian::getAddress, VeterinarianV1DTO::setAddress);
                    mapper.map(Veterinarian::getLanguage, VeterinarianV1DTO::setLanguage);
                    mapper.map(Veterinarian::getAnimalType, VeterinarianV1DTO::setAnimalType);
                    mapper.map(Veterinarian::getAddress, VeterinarianV1DTO::setAddress);
                    mapper.map(Veterinarian::getCountryId, VeterinarianV1DTO::setCountryId);
                    mapper.map(Veterinarian::getStateId, VeterinarianV1DTO::setStateId);
                    mapper.map(Veterinarian::getSpecializations, VeterinarianV1DTO::setSpecializations);
                    mapper.map(Veterinarian::getAboutMe, VeterinarianV1DTO::setAboutMe);

                });



        // Mapping for VeterinarianDTO -> Veterinarian
        modelMapper.typeMap(VeterinarianDTO.class, Veterinarian.class)
                .addMappings(mapper -> {
                    mapper.map(VeterinarianDTO::getId, Veterinarian::setId);
                    mapper.map(VeterinarianDTO::getEducation, Veterinarian::setEducation);
                    mapper.map(VeterinarianDTO::getYearsOfExperience, Veterinarian::setYearsOfExperience);
                    mapper.map(VeterinarianDTO::getAddress, Veterinarian::setAddress);
                    mapper.map(VeterinarianDTO::getLanguage, Veterinarian::setLanguage);
                    mapper.map(VeterinarianDTO::getAnimalType, Veterinarian::setAnimalType);
                    mapper.map(VeterinarianDTO::getAddress, Veterinarian::setAddress);
                    mapper.map(VeterinarianDTO::getCountryId, Veterinarian::setCountryId);
                    mapper.map(VeterinarianDTO::getStateId, Veterinarian::setStateId);
                    mapper.map(VeterinarianDTO::getSpecializations, Veterinarian::setSpecializations);
                    mapper.map(VeterinarianDTO::getAboutMe, Veterinarian::setAboutMe);
                });
    }
}
