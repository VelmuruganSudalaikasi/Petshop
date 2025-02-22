package com.treasuremount.petshop.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "shipping_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private User user;

    @Column(name = "userId",nullable = false)
    private Long userId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "address_line1", nullable = false, length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CountryId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private Country country;

    @Column(name = "CountryId")
    private Long CountryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StateId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private State State;
    @Column(name = "StateId")
    private Long StateId;

    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Column(name = "default_address", nullable = false)
    private Boolean defaultAddress;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createDate;

    @Column(name = "updated_date")
    private Date updateDate;


}