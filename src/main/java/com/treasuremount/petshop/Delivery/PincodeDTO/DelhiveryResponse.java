package com.treasuremount.petshop.Delivery.PincodeDTO;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DelhiveryResponse {
    @JsonProperty("delivery_codes")
    private List<DeliveryCode> deliveryCodes;

    public List<DeliveryCode> getDeliveryCodes() {
        return deliveryCodes;
    }

    public void setDeliveryCodes(List<DeliveryCode> deliveryCodes) {
        this.deliveryCodes = deliveryCodes;
    }
}
