package com.treasuremount.petshop.Delivery.PincodeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Center {

    @JsonProperty("code")
    private String code;

    @JsonProperty("cn")
    private String cn;

    @JsonProperty("s")
    private String s;

    @JsonProperty("u")
    private String u;

    @JsonProperty("ud")
    private String ud;

    @JsonProperty("sort_code")
    private String sortCode;
}
