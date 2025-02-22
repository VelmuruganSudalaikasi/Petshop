package com.treasuremount.petshop.Payment.CustomerRefund;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPaymentDTO {
    private Long userId;
    private String name;
    private Long orderId;
    private Date orderDate;
    private BigDecimal orderAmount;
    private String orderStatus;
    private Boolean isPaid;
    private String paymentReference;
}