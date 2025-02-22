package com.treasuremount.petshop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pets")
@Data
@AllArgsConstructor
public class Pets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ProductId", insertable = false,updatable = false)
    @JsonIgnore
    private Product product;

    @Column(name= "ProductId" )
    private Long ProductId;

    @Column(nullable = false)
    private String breed;

    @Column(name = "month", nullable = false)
    private Short month;

    @Column(name = "year", nullable = false)
    private Short year;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;

    //last added fields

    private Boolean isTransportAvailable ;


    private Boolean isVeterinaryVerified;
    private String veterinaryCertificationUrl;


    private Boolean isInsured;
    private String insuredCertificationUrl;

//    private String barCode;


    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;


    public Pets() {}


}

