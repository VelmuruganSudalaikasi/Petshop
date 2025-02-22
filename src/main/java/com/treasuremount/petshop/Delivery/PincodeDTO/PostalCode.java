package com.treasuremount.petshop.Delivery.PincodeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostalCode {

    @JsonProperty("city")
    private String city;

    @JsonProperty("cod")
    private String cod;

    @JsonProperty("inc")
    private String inc;

    @JsonProperty("district")
    private String district;

    @JsonProperty("pin")
    private Integer pin; // Changed from int to Integer

    @JsonProperty("max_amount")
    private Double maxAmount; // Changed from double to Double

    @JsonProperty("pre_paid")
    private String prePaid;

    @JsonProperty("cash")
    private String cash;

    @JsonProperty("state_code")
    private String stateCode;

    @JsonProperty("max_weight")
    private Double maxWeight; // Changed from double to Double

    @JsonProperty("pickup")
    private String pickup;

    @JsonProperty("repl")
    private String repl;

    @JsonProperty("covid_zone")
    private String covidZone;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("is_oda")
    private String isOda;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("sort_code")
    private String sortCode;

    @JsonProperty("sun_tat")
    private Boolean sunTat; // Changed from boolean to Boolean

    @JsonProperty("center")
    private List<Center> centers;


}

