package com.treasuremount.petshop.MedicineResource;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    @Getter(AccessLevel.NONE) // Prevents getter generation
    @Setter(AccessLevel.NONE) // Prevents setter generation
    private Product product;

    @Column(name = "product_id")
    private Long productId;

   private String petType;

    private String brand;

    @Column(name = "dosage_unit")
    private String dosageUnit;

    @Column(name = "dosage")
    private BigDecimal dosage;


    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @Temporal(TemporalType.DATE)
     private Date createDate;


}
