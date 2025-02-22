package com.treasuremount.petshop.PetResource;

import java.math.BigDecimal;
import java.util.Date;

public class PetResponseDTO extends PetResourceDTO  {

/*    private PetResponseDTO petResponseDTO;
    private VendorRegisterDTO vendorRegisterDTO;*/

    public PetResponseDTO() {
    }

    public PetResponseDTO(Long productId, Long categoryId, Long userId, String productName, String productImageUrl, Double productPrice, Long stockQuantity, Boolean activeStatus, Integer returnWithin, Date productCreatedDate, Date productUpdatedDate, Long subCategoryId, Long productStatusId, Double discount, Integer minStockLevel, String videoUrl, String barCode, String breed, Short month, Short year, String gender, String color, BigDecimal weight, Boolean isTransportAvailable, Boolean isVeterinaryVerified, String veterinaryCertificationUrl, Boolean isInsured, String insuredCertificationUrl, String about, String healthInfo, String careInstructions, String specialRequirements) {
        super(productId, categoryId, userId, productName, productImageUrl, productPrice, stockQuantity, activeStatus, returnWithin, productCreatedDate, productUpdatedDate, subCategoryId, productStatusId, discount, minStockLevel, videoUrl, barCode, breed, month, year, gender, color, weight, isTransportAvailable, isVeterinaryVerified, veterinaryCertificationUrl, isInsured, insuredCertificationUrl, about, healthInfo, careInstructions, specialRequirements);
    }

    public PetResponseDTO(String breed, Short month, Short year, String gender, String color, BigDecimal weight, Boolean isTransportAvailable, Boolean isVeterinaryVerified, String veterinaryCertificationUrl, Boolean isInsured, String insuredCertificationUrl, String about, String healthInfo, String careInstructions, String specialRequirements) {
        super(breed, month, year, gender, color, weight, isTransportAvailable, isVeterinaryVerified, veterinaryCertificationUrl, isInsured, insuredCertificationUrl, about, healthInfo, careInstructions, specialRequirements);
    }

}
