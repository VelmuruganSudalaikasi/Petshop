package com.treasuremount.petshop.DTO;

import com.treasuremount.petshop.Entity.Category;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Entity.SubCategory;
import com.treasuremount.petshop.Entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/*
*

    private Long id;



    private Category category;


    private Long CategoryId;


    private User user;


    private Long UserId;

    private SubCategory subCategory;

    private Long SubCategoryId;

    private String name;
    private String imageUrl;

    private double price;

    private Boolean activeStatus;

    private Date createdDate;

    private Date updatedDate;

    private Integer returnWithin;*/
@Data
@NoArgsConstructor
public class ProductDTO  {


    private Long id;

    private String userName;

    private Long CategoryId;


    private Long UserId;


    private Long SubCategoryId;


    private String imageUrl;

    private Double price;

    private Boolean activeStatus;

    private Date createdDate;

    private Date updatedDate;

    private Integer returnWithin;
    //new
    private Long productStatusId;
    private String productStatusName;

    private Double discount;
    private Integer minStockLevel;

    private Long stockQuantity;


    private  String categoryName;
    private  String subCategoryName;
    private String shopName;
    private String ProductName;

    private String videoUrl;


    public ProductDTO(Product product){
        this.setId(product.getId());
//        this.setCategory(product.getCategory());
        this.setCategoryId(product.getSubCategoryId());
//        this.setUser(product.getUser());
        this.setUserId(product.getUserId());
//        this.setSubCategory(product.getSubCategory());
        this.setSubCategoryId(product.getSubCategoryId());
        this.setImageUrl(product.getImageUrl());
        this.setPrice(product.getPrice());
        this.setActiveStatus(product.getActiveStatus());
        this.setCreatedDate(product.getCreatedDate());
        this.setUpdatedDate(product.getUpdatedDate());
        this.setReturnWithin(product.getReturnWithin());


    }




}
