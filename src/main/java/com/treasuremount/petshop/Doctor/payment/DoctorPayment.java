package com.treasuremount.petshop.Doctor.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Doctor.Veterinarian.Veterinarian;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class DoctorPayment {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "veterinarian_id", referencedColumnName = "id", insertable = false, updatable = false)
        @JsonIgnore
        private Veterinarian veterinarian;

        @Column(name = "veterinarian_id", nullable = false)
        private Long veterinarian_id;

        @Column(name = "payment_method", nullable = false, length = 50)
        private String paymentMethod; // e.g., "Credit Card", "Cash", etc.

        @Column(name = "amount_paid", nullable = false, precision = 10, scale = 2)
        private BigDecimal amountPaid;

        @Column(name = "transaction_id", unique = true, length = 100)
        private String transactionId;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "payment_date", nullable = false)
        private Date paymentDate;

        @Column(name = "payment_status", length = 50, nullable = false)
        private String paymentStatus;


}
