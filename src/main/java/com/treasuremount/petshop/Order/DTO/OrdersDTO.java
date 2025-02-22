package com.treasuremount.petshop.Order.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {
    private Long id;
    private Long userId;
    private Long ProductId;
    private Long OrderStatusId;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private Date orderDate;
    private Date deliveryDate;
    private Boolean isCancelled;
    private Boolean isReturned;
}
