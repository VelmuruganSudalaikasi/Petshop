package com.treasuremount.petshop.PetResource;

import com.treasuremount.petshop.DTO.ProductResourceDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PetResourceDTO extends ProductResourceDTO {


    // Pet DTO
    private String breed;
    private Short month;
    private Short year;
    private String gender;
    private String color;
    private BigDecimal weight;
    private Boolean isTransportAvailable ;
    private Boolean isVeterinaryVerified;
    private String veterinaryCertificationUrl;
    private Boolean isInsured;
    private String insuredCertificationUrl;
    /*private String barCode;*/



    //petDetails
    private String about;
    private String healthInfo;
    private String careInstructions;
    private String specialRequirements;

    public PetResourceDTO(Long productId, Long categoryId, Long userId, String productName, String productImageUrl, Double productPrice, Long stockQuantity, Boolean activeStatus, Integer returnWithin, Date productCreatedDate, Date productUpdatedDate, Long subCategoryId, Long productStatusId, Double discount, Integer minStockLevel, String videoUrl, String barCode, String breed, Short month, Short year, String gender, String color, BigDecimal weight, Boolean isTransportAvailable, Boolean isVeterinaryVerified, String veterinaryCertificationUrl, Boolean isInsured, String insuredCertificationUrl, String about, String healthInfo, String careInstructions, String specialRequirements) {
        super(productId, categoryId, userId, productName, productImageUrl, productPrice, stockQuantity, activeStatus, returnWithin, productCreatedDate, productUpdatedDate, subCategoryId, productStatusId, discount, minStockLevel, videoUrl, barCode);
        this.breed = breed;
        this.month = month;
        this.year = year;
        this.gender = gender;
        this.color = color;
        this.weight = weight;
        this.isTransportAvailable = isTransportAvailable;
        this.isVeterinaryVerified = isVeterinaryVerified;
        this.veterinaryCertificationUrl = veterinaryCertificationUrl;
        this.isInsured = isInsured;
        this.insuredCertificationUrl = insuredCertificationUrl;
        this.about = about;
        this.healthInfo = healthInfo;
        this.careInstructions = careInstructions;
        this.specialRequirements = specialRequirements;
    }

    public PetResourceDTO(String breed, Short month, Short year, String gender, String color, BigDecimal weight, Boolean isTransportAvailable, Boolean isVeterinaryVerified, String veterinaryCertificationUrl, Boolean isInsured, String insuredCertificationUrl, String about, String healthInfo, String careInstructions, String specialRequirements) {
        this.breed = breed;
        this.month = month;
        this.year = year;
        this.gender = gender;
        this.color = color;
        this.weight = weight;
        this.isTransportAvailable = isTransportAvailable;
        this.isVeterinaryVerified = isVeterinaryVerified;
        this.veterinaryCertificationUrl = veterinaryCertificationUrl;
        this.isInsured = isInsured;
        this.insuredCertificationUrl = insuredCertificationUrl;
        this.about = about;
        this.healthInfo = healthInfo;
        this.careInstructions = careInstructions;
        this.specialRequirements = specialRequirements;
    }
}
