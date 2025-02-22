package com.treasuremount.petshop.Payment.VendorPayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
//while payment
    private Long orderId;
    private Long productId;
    private String ProductName;
    private Long quantity;
    private BigDecimal totalAmount;

}


