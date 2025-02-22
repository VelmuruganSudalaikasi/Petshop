package com.treasuremount.petshop.DTO;


public class InventoryLocationDTO {

    private Long id;
    private Long vendorId;
    private String locationName;
    private String address;
    private Long countryId;
    private String city;
    private Long stateId;
    private String postalCode;
    private String phoneNumber;
    private Boolean activeStatus;



    public InventoryLocationDTO() {
    }

    public InventoryLocationDTO(Long id, Long vendorId, String locationName, String address, Long countryId, String city, Long stateId, String postalCode, String phoneNumber, Boolean activeStatus) {
        this.id = id;
        this.vendorId = vendorId;
        this.locationName = locationName;
        this.address = address;
        this.countryId = countryId;
        this.city = city;
        this.stateId = stateId;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.activeStatus = activeStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
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
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
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

    @Override
    public String toString() {
        return "InventoryLocationDTO{" +
                "id=" + id +
                ", vendorId=" + vendorId +
                ", locationName='" + locationName + '\'' +
                ", address='" + address + '\'' +
                ", countryId=" + countryId +
                ", city='" + city + '\'' +
                ", stateId=" + stateId +
                ", postalCode='" + postalCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", activeStatus=" + activeStatus +
                '}';
    }
}


