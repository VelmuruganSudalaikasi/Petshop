package com.treasuremount.petshop.DTO;


import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class CartItemDto {
    //cart
    private Long id;
    private Long productId;
    private Long userId;


    //product and category mapping details
    private String categoryName;
    private String shopName;
    private String productName;
    private Double price;
    private String imageUrl;
    private Long actualQuantity;
    private Long quantity;


    public CartItemDto(Long id, Long productId, Long userId, String categoryName, String shopName, String productName, Double price, String imageUrl, Long actualQuantity, Long quantity,Double weight) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.categoryName = categoryName;
        this.shopName = shopName;
        this.productName = productName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.actualQuantity = actualQuantity;
        this.quantity = quantity;
        this.weight=weight;
    }
    // added further requirement
    private BigDecimal tax;
    private BigDecimal shippingCharge;
    private BigDecimal subtotal;
    private BigDecimal totalAmount;
    private Double weight;




}
