package com.treasuremount.petshop.DTO;


import com.treasuremount.petshop.Entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDemoDTO {

    private Long id;
    private Long CategoryId;
    private Long UserId;
    private Long ProductStatusId;
    private Long SubCategoryId;
    private String productName;
    private String imageUrl;
    private double price;
    private Boolean activeStatus;
    private Date createdDate;
    private Date updatedDate;
    private Integer returnWithin;

    /*
    *
    * {
  "id": 0,
  "name": "string",
  "imageUrl": "string",
  "price": 0,
  "activeStatus": true,
  "createdDate": "2024-12-06T06:40:57.082Z",
  "updatedDate": "2024-12-06T06:40:57.082Z",
  "returnWithin": 0,
  "subCategoryId": 0,
  "productStatusId": 0,
  "userId": 0,
  "categoryId": 0
}*/
}
