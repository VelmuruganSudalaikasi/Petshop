package com.treasuremount.petshop.Payment.CustomerRefund;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount;
    private Date paymentDate;
    private String paymentReference;
    private String paymentMode;
    private String remarks;

    @PrePersist
    protected void onCreate() {
        paymentDate = new Date();
    }
}
