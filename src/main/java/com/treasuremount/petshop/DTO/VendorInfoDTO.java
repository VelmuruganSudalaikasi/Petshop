package com.treasuremount.petshop.DTO;



public class VendorInfoDTO {
    private Long vendorId;
    private String firstName;
    private String shopName;
    private String contactDetails;

    // Constructors
    public VendorInfoDTO(Long vendorId, String firstName, String shopName, String contactDetails) {
        this.vendorId = vendorId;
        this.firstName = firstName;
        this.shopName = shopName;
        this.contactDetails = contactDetails;
    }

    public VendorInfoDTO(){}

    // Getters and Setters
    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }
}
