package com.treasuremount.petshop.Delivery.PincodeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeliveryCode {

    @JsonProperty("postal_code")
    private PostalCode postalCode;

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }


}

