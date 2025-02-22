package com.treasuremount.petshop.DTO;


import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
public class ProductResourceDTO {
    private Long productId;
    private Long CategoryId;
    private Long UserId;
    private String productName;
    private String productImageUrl;
    private Double productPrice;
    private Long stockQuantity;
    private Boolean activeStatus;
    private Integer returnWithin;
    @Temporal(TemporalType.DATE)
    private Date productCreatedDate;
    @Temporal(TemporalType.DATE)
    private Date productUpdatedDate;
    private Long SubCategoryId;
    private Long ProductStatusId;
    private Double discount;
    private Integer minStockLevel;
    private String videoUrl;
    private String barCode;

    public ProductResourceDTO(Long productId, Long categoryId, Long userId, String productName, String productImageUrl, Double productPrice, Long stockQuantity, Boolean activeStatus, Integer returnWithin, Date productCreatedDate, Date productUpdatedDate, Long subCategoryId, Long productStatusId, Double discount, Integer minStockLevel, String videoUrl, String barCode) {
        this.productId = productId;
        CategoryId = categoryId;
        UserId = userId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.stockQuantity = stockQuantity;
        this.activeStatus = activeStatus;
        this.returnWithin = returnWithin;
        this.productCreatedDate = productCreatedDate;
        this.productUpdatedDate = productUpdatedDate;
        SubCategoryId = subCategoryId;
        ProductStatusId = productStatusId;
        this.discount = discount;
        this.minStockLevel = minStockLevel;
        this.videoUrl = videoUrl;
        this.barCode = barCode;
    }

    public ProductResourceDTO(){}
}
