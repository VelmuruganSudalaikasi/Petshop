package com.treasuremount.petshop.AccessoriesResource;

import com.treasuremount.petshop.DTO.ProductResourceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.util.Date;
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccessoriesResourceDTO extends ProductResourceDTO {


     //Accessories
    private String brand;
    private String size;
    private String color;


   //Details
    private String specifications;
    private String usageInstructions;
    private String careInstructions;

    public AccessoriesResourceDTO(Long productId, Long categoryId, Long userId, String productName, String productImageUrl, Double productPrice, Long stockQuantity, Boolean activeStatus, Integer returnWithin, Date productCreatedDate, Date productUpdatedDate, Long subCategoryId, Long productStatusId, Double discount, Integer minStockLevel, String videoUrl, String barCode, String brand, String size, String color, String specifications, String usageInstructions, String careInstructions) {
        super(productId, categoryId, userId, productName, productImageUrl, productPrice, stockQuantity, activeStatus, returnWithin, productCreatedDate, productUpdatedDate, subCategoryId, productStatusId, discount, minStockLevel, videoUrl, barCode);
        this.brand = brand;
        this.size = size;
        this.color = color;
        this.specifications = specifications;
        this.usageInstructions = usageInstructions;
        this.careInstructions = careInstructions;
    }

    public AccessoriesResourceDTO(String brand, String size, String color, String specifications, String usageInstructions, String careInstructions) {
        this.brand = brand;
        this.size = size;
        this.color = color;
        this.specifications = specifications;
        this.usageInstructions = usageInstructions;
        this.careInstructions = careInstructions;
    }
}
