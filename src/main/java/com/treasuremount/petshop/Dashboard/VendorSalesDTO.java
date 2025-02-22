package com.treasuremount.petshop.Dashboard;


/*
     VendorName
     productCount
     shopName
     ImageUrl
     email

* */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorSalesDTO {
    private String vendorName;
    private String shopName;
    private String imageUrl;
    private String email;
    private Long SalesCount;

}
