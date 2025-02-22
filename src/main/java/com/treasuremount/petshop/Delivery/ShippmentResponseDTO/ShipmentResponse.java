package com.treasuremount.petshop.Delivery.ShippmentResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentResponse {
    @JsonProperty("ShipmentData")
    private List<ShipmentData> shipmentData;

    // Getters and Setters
    public List<ShipmentData> getShipmentData() {
        return shipmentData;
    }

    public void setShipmentData(List<ShipmentData> shipmentData) {
        this.shipmentData = shipmentData;
    }
}
