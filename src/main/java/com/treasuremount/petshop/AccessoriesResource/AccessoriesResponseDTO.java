package com.treasuremount.petshop.AccessoriesResource;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;



public class AccessoriesResponseDTO extends AccessoriesResourceDTO {
    public AccessoriesResponseDTO() {
    }

    public AccessoriesResponseDTO(Long productId, Long categoryId, Long userId, String productName, String productImageUrl, Double productPrice, Long stockQuantity, Boolean activeStatus, Integer returnWithin, Date productCreatedDate, Date productUpdatedDate, Long subCategoryId, Long productStatusId, Double discount, Integer minStockLevel, String videoUrl, String barCode, String brand, String size, String color, String specifications, String usageInstructions, String careInstructions) {
        super(productId, categoryId, userId, productName, productImageUrl, productPrice, stockQuantity, activeStatus, returnWithin, productCreatedDate, productUpdatedDate, subCategoryId, productStatusId, discount, minStockLevel, videoUrl, barCode, brand, size, color, specifications, usageInstructions, careInstructions);
    }

    public AccessoriesResponseDTO(String brand, String size, String color, String specifications, String usageInstructions, String careInstructions) {
        super(brand, size, color, specifications, usageInstructions, careInstructions);
    }

}
