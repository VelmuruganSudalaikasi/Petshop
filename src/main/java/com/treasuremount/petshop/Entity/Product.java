package com.treasuremount.petshop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Entity
@Table(name = "Product")
@Data
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CategoryId",referencedColumnName = "id",insertable = false,updatable = false)
    @JsonIgnore
    private Category category;

    @Column(name="CategoryId")
    private Long CategoryId;

    @ManyToOne
    @JoinColumn(name = "UserId",referencedColumnName = "id",insertable = false,updatable = false)
    @JsonIgnore
    private User user;

    @Column(name="UserId")
    private Long UserId;

    @Column(name="stock_quantity")
    private Long stockQuantity;

    @Column(name="discount")
    private Double discount;

    @Column(name = "min_stock_level", nullable = false)
    private Integer minStockLevel;

    @ManyToOne
    @JoinColumn(name = "ProductStatusId",referencedColumnName = "id",insertable = false,updatable = false)
    @JsonIgnore
    private ProductStatus productStatus;

    @Column(name="ProductStatusId")
    private Long ProductStatusId;

    @ManyToOne
    @JoinColumn(name = "SubCategoryId",referencedColumnName = "id",insertable = false,updatable = false)
    @JsonIgnore
    private SubCategory subCategory;

    @Column(name = "SubCategoryId")
    private Long SubCategoryId;

    private String name;
    @Column(name = "imageUrl", length = 1024)
    private String imageUrl;
    private String videoUrl;
    private String barCode;
    private Double price;
    /*@Column(name = "tax", nullable = false, precision = 10, scale = 2)
    private BigDecimal tax;
    @Column(name = "shipping_charge", nullable = false, precision = 10, scale = 2)
    private BigDecimal shippingCharge;*/
    private Boolean activeStatus;
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    @Column(name="returnWithin")
    private Integer returnWithin;


    public Product() {

    }

}
/* tax,
   discount,
   shipping charge

* */