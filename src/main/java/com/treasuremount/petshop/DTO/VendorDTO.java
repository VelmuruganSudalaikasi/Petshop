package com.treasuremount.petshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO  {
    private Long id;
    private String shopName;
    private String imageUrl;
    private String contactDetails;
    private String taxId;
    private String registrationNumber;
    private Boolean activeStatus;
    private Long userId;
    private String address;
    private Long countryId;
    private String city;
    @NonNull
    private String gstNumber;
    private Long stateId;
    private String postalCode;
    private Date createdDate;
    private Date modifiedDate;


}
