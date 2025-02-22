package com.treasuremount.petshop.Delivery.ShippmentResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanDetail {
    @JsonProperty("Instructions")
    private String instructions;
    @JsonProperty("Scan")
    private String scan;
    @JsonProperty("ScanDateTime")
    private String scanDateTime;
    @JsonProperty("ScanType")
    private String scanType;
    @JsonProperty("ScannedLocation")
    private String scannedLocation;
    @JsonProperty("StatusCode")
    private String statusCode;
    @JsonProperty("StatusDateTime")
    private String statusDateTime;

    // Getters and Setters
    // ... Getters and Setters for all fields
}
