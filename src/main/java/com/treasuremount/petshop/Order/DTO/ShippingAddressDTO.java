package com.treasuremount.petshop.Order.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.Country;
import com.treasuremount.petshop.Entity.State;
import com.treasuremount.petshop.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShippingAddressDTO {

    private Long id;
    private Long userId;
    private String fullName;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private Long CountryId;
    private Long StateId;
    private String countryName;
    private String stateName;
    private String postalCode;
    private Boolean defaultAddress;
    private Date createDate;
    private Date updateDate;

}
