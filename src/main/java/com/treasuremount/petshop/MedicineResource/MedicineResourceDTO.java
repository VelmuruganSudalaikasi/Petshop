package com.treasuremount.petshop.MedicineResource;

import com.treasuremount.petshop.DTO.ProductResourceDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MedicineResourceDTO extends ProductResourceDTO {

    //medicine

    private String petType;
    private String brand;
    private String dosageUnit;
    private BigDecimal dosage;
    private Date expiryDate;



    //details
    private String activeIngredients;
    private String usageInstructions;
    private String storageInstructions;
    private String warnings;

    public MedicineResourceDTO(Long productId, Long categoryId, Long userId, String productName, String productImageUrl, Double productPrice, Long stockQuantity, Boolean activeStatus, Integer returnWithin, Date productCreatedDate, Date productUpdatedDate, Long subCategoryId, Long productStatusId, Double discount, Integer minStockLevel, String videoUrl, String barCode, String petType, String brand, String dosageUnit, BigDecimal dosage, Date expiryDate, String activeIngredients, String usageInstructions, String storageInstructions, String warnings) {
        super(productId, categoryId, userId, productName, productImageUrl, productPrice, stockQuantity, activeStatus, returnWithin, productCreatedDate, productUpdatedDate, subCategoryId, productStatusId, discount, minStockLevel, videoUrl, barCode);
        this.petType = petType;
        this.brand = brand;
        this.dosageUnit = dosageUnit;
        this.dosage = dosage;
        this.expiryDate = expiryDate;
        this.activeIngredients = activeIngredients;
        this.usageInstructions = usageInstructions;
        this.storageInstructions = storageInstructions;
        this.warnings = warnings;
    }

    public MedicineResourceDTO(String petType, String brand, String dosageUnit, BigDecimal dosage, Date expiryDate, String activeIngredients, String usageInstructions, String storageInstructions, String warnings) {
        this.petType = petType;
        this.brand = brand;
        this.dosageUnit = dosageUnit;
        this.dosage = dosage;
        this.expiryDate = expiryDate;
        this.activeIngredients = activeIngredients;
        this.usageInstructions = usageInstructions;
        this.storageInstructions = storageInstructions;
        this.warnings = warnings;
    }
}
