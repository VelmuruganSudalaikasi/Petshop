package com.treasuremount.petshop.Delivery.ShippmentResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    @JsonProperty("Instructions")
    private String instructions;
    @JsonProperty("RecievedBy")
    private String recievedBy;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("StatusCode")
    private String statusCode;
    @JsonProperty("StatusDateTime")
    private String statusDateTime;
    @JsonProperty("StatusLocation")
    private String statusLocation;
    @JsonProperty("StatusType")
    private String statusType;

    // Getters and Setters
    // ... Getters and Setters for all fields
}
