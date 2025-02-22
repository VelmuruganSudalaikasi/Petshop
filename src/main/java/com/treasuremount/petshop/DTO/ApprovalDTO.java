package com.treasuremount.petshop.DTO;

import lombok.Data;

@Data
public class ApprovalDTO {

    /*userId shop name  productDetails approval*/

    private Long productId;  // Product ID
    private Long userId;     // User ID
    private String userName;
    private String shopName; // Vendor's Shop Name
    private String productName; // Product Name
    private Boolean activeStatus;

    public ApprovalDTO() {
    }




}
