package com.treasuremount.petshop.FoodResource;

import java.math.BigDecimal;
import java.util.Date;

public class FoodResponseDTO extends FoodResourceDTO {

    public FoodResponseDTO() {
    }

    public FoodResponseDTO(Long productId, Long categoryId, Long userId, String productName, String productImageUrl, Double productPrice, Long stockQuantity, Boolean activeStatus, Integer returnWithin, Date productCreatedDate, Date productUpdatedDate, Long subCategoryId, Long productStatusId, Double discount, Integer minStockLevel, String videoUrl, String barCode, String brand, String weightUnit, BigDecimal weight, Date expiryDate, String ingredients, String nutritionalInfo, String storageInstructions, String feedingGuidelines) {
        super(productId, categoryId, userId, productName, productImageUrl, productPrice, stockQuantity, activeStatus, returnWithin, productCreatedDate, productUpdatedDate, subCategoryId, productStatusId, discount, minStockLevel, videoUrl, barCode, brand, weightUnit, weight, expiryDate, ingredients, nutritionalInfo, storageInstructions, feedingGuidelines);
    }

    public FoodResponseDTO(String brand, String weightUnit, BigDecimal weight, Date expiryDate, String ingredients, String nutritionalInfo, String storageInstructions, String feedingGuidelines) {
        super(brand, weightUnit, weight, expiryDate, ingredients, nutritionalInfo, storageInstructions, feedingGuidelines);
    }
}
