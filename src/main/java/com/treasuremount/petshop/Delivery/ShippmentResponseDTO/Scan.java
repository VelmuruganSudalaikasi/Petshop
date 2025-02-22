package com.treasuremount.petshop.Delivery.ShippmentResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scan {
    @JsonProperty("ScanDetail")
    private ScanDetail scanDetail;

    // Getters and Setters
    public ScanDetail getScanDetail() {
        return scanDetail;
    }

    public void setScanDetail(ScanDetail scanDetail) {
        this.scanDetail = scanDetail;
    }
}
