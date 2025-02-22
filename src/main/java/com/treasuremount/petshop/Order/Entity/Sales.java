package com.treasuremount.petshop.Order.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private User user;

    @Column(name = "userId")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "ProductId", insertable = false,updatable = false)
    @JsonIgnore
    private Product product;

    @Column(name="ProductId")
    private Long ProductId;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "tax", nullable = false, precision = 10, scale = 2)
    private BigDecimal tax;


    @Column(name = "discount")
    private double  discount;

    @Temporal(TemporalType.DATE)
    @Column(name = "sales_date", nullable = false)
    private Date salesDate;


    @Column(name = "unit_price",nullable = false)
    private BigDecimal unitPrice;



}
