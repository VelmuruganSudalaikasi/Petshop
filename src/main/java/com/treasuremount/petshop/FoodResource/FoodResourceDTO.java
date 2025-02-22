package com.treasuremount.petshop.FoodResource;
import com.treasuremount.petshop.DTO.ProductResourceDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class

FoodResourceDTO extends ProductResourceDTO {


    // Foods fields
    private String brand;
    private String weightUnit;
    private BigDecimal weight;
    private Date expiryDate;


    //Food Details
    private String ingredients;
    private String nutritionalInfo;
    private String storageInstructions;
    private String feedingGuidelines;

    public FoodResourceDTO(Long productId, Long categoryId, Long userId, String productName, String productImageUrl, Double productPrice, Long stockQuantity, Boolean activeStatus, Integer returnWithin, Date productCreatedDate, Date productUpdatedDate, Long subCategoryId, Long productStatusId, Double discount, Integer minStockLevel, String videoUrl, String barCode, String brand, String weightUnit, BigDecimal weight, Date expiryDate, String ingredients, String nutritionalInfo, String storageInstructions, String feedingGuidelines) {
        super(productId, categoryId, userId, productName, productImageUrl, productPrice, stockQuantity, activeStatus, returnWithin, productCreatedDate, productUpdatedDate, subCategoryId, productStatusId, discount, minStockLevel, videoUrl, barCode);
        this.brand = brand;
        this.weightUnit = weightUnit;
        this.weight = weight;
        this.expiryDate = expiryDate;
        this.ingredients = ingredients;
        this.nutritionalInfo = nutritionalInfo;
        this.storageInstructions = storageInstructions;
        this.feedingGuidelines = feedingGuidelines;
    }

    public FoodResourceDTO(String brand, String weightUnit, BigDecimal weight, Date expiryDate, String ingredients, String nutritionalInfo, String storageInstructions, String feedingGuidelines) {
        this.brand = brand;
        this.weightUnit = weightUnit;
        this.weight = weight;
        this.expiryDate = expiryDate;
        this.ingredients = ingredients;
        this.nutritionalInfo = nutritionalInfo;
        this.storageInstructions = storageInstructions;
        this.feedingGuidelines = feedingGuidelines;
    }
}
