package com.treasuremount.petshop.Order.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Entity.ShippingAddress;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Order.Entity.OrderStatusS;
import com.treasuremount.petshop.Order.Entity.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor

public class OrderGetOneDTO {
    private Long id;
    private String uniqueId;
    private Long userId;
    private String imageUrl;
    private Long productId;
    private BigDecimal unitPrice;
    private Long quantity;
    private BigDecimal tax;
    private BigDecimal shippingCharge;
    private BigDecimal subtotal;
    private Long orderStatusId;
    private String categoryName;
    private String subCategoryName;
    private BigDecimal totalAmount;
    private Date orderDate;
    private Date deliveryDate;
    private Boolean isCancelled;
    private Boolean isReturned;

    // Extra fields
    private String userName;
    private String userEmail;
    private String orderStatusName;
    private String productName;
    private ShippingAddressDTO shippingAddress;


     // from vendor table
    private String vendorName;
    private String shopName;
    private String address;

    // Default constructor
    public OrderGetOneDTO() {}

    // Constructor mapping from Orders entity
    public OrderGetOneDTO(Orders order) {
        this.id = order.getId();
        this.uniqueId=order.getUniqueOrderId();
        this.userId = order.getUser() != null ? order.getUser().getId() : null;
        this.productId = order.getProduct() != null ? order.getProduct().getId() : null;
        this.unitPrice = order.getUnitPrice();
        this.quantity = order.getQuantity();
        this.tax = order.getTax();
        this.shippingCharge = order.getShippingCharge();
        this.subtotal = order.getSubtotal();
        this.orderStatusId = order.getOrderStatusS() != null ? order.getOrderStatusS().getId() : null;
        this.totalAmount = order.getTotalAmount();
        this.orderDate = order.getOrderDate();
        this.deliveryDate = order.getDeliveryDate();
        this.isCancelled = order.getCancelled();
        this.isReturned = order.getReturned();
    }

    // Getters and Setters
    // ...
}

