package com.treasuremount.petshop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "InventoryLocation")
public class InventoryLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VendorId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private Vendor vendor;

    @Column(name="VendorId")
    private Long VendorId;

    private String locationName;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CountryId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private Country country;

    @Column(name = "CountryId")
    private Long CountryId;

    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StateId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private State State;
    @Column(name = "StateId")
    private Long StateId;

    private String postalCode;
    private String phoneNumber;
    private Boolean activeStatus;

    public InventoryLocation(Long id, Vendor vendor, Long vendorId, String locationName, String address, Country country, Long countryId, String city, com.treasuremount.petshop.Entity.State state, Long stateId, String postalCode, String phoneNumber, Boolean activeStatus) {
        this.id = id;
        this.vendor = vendor;
        VendorId = vendorId;
        this.locationName = locationName;
        this.address = address;
        this.country = country;
        CountryId = countryId;
        this.city = city;
        State = state;
        StateId = stateId;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.activeStatus = activeStatus;
    }

    public InventoryLocation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return VendorId;
    }

    public void setVendorId(Long vendorId) {
        VendorId = vendorId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCountryId() {
        return CountryId;
    }

    public void setCountryId(Long countryId) {
        CountryId = countryId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getStateId() {
        return StateId;
    }

    public void setStateId(Long stateId) {
        StateId = stateId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
}