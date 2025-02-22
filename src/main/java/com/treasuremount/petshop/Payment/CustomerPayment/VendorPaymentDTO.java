package com.treasuremount.petshop.Payment.CustomerPayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorPaymentDTO {

    //after the payment
    private Long vendorId;
    private String name;
    private String shopName;
    private Long orderId;
    private Date orderDate;
    private BigDecimal orderAmount;
    private String orderStatus;
    private String paymentReference;
    private Date paymentDate;
    private Boolean isPaid;
}