package com.treasuremount.petshop.DTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorWithUserName {

    private Long id;
    private String imageUrl;
    private String shopName;
    private String contactDetails;
    private String taxId;
    private String registrationNumber;
    private Boolean activeStatus;
    private String address;
    private String city;
    private String gstNumber;
    private String userName;
    private Long userId;
    private Long CountryId;
    private Long StateId;
    private String postalCode;
    private Date createdDate;

    private Date modifiedDate;
}
