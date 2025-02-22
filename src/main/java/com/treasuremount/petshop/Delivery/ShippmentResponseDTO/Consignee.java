package com.treasuremount.petshop.Delivery.ShippmentResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consignee {
    @JsonProperty("Address1")
    private List<Object> address1;  // Empty list, so Object is used
    @JsonProperty("Address2")
    private List<Object> address2;  // Empty list, so Object is used
    @JsonProperty("Address3")
    private String address3;
    @JsonProperty("City")
    private String city;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("PinCode")
    private int pinCode;
    @JsonProperty("State")
    private String state;
    @JsonProperty("Telephone1")
    private String telephone1;
    @JsonProperty("Telephone2")
    private String telephone2;

    // Getters and Setters
    // ... Getters and Setters for all fields
}
