package com.treasuremount.petshop.Payment.VendorPayment;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class VendorPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount;
    private Date paymentDate;
    private String paymentReference;
    private String paymentMode;
    private String remarks;

    @JsonIgnore
    private Boolean vendorConfirmation;



    @PrePersist
    protected void onCreate() {
        paymentDate = new Date();
        this.vendorConfirmation =false;
    }
}
