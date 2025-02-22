package com.treasuremount.petshop.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
        private String imageUrl;
    private String shopName;
    private String contactDetails;
    private String taxId;
    private String registrationNumber;
    private Boolean activeStatus;
    private String address;
    private String city;
    @Column(length = 15,nullable = false)
    private String gstNumber;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private User user;
    @Column(name = "userId")
    private Long userId;


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

    @Column(name = "postal_code", nullable = true, length = 20)
    private String postalCode;

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    private Date modifiedDate;

    public Vendor() {}

}