package com.treasuremount.petshop.Delivery.ShippmentDTO;

// ShipmentResponseDTO.java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentResponseDTO {
    private Boolean success;
    private Integer cash_pickups;
    private Integer cash_pickups_count;
    private Integer cod_amount;
    private Integer cod_count;
    private Integer package_count;
    private List<Package> packages;
    private Integer pickups_count;
    private Integer prepaid_count;
    private Integer replacement_count;
    private String upload_wbn;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Package {
        private String client;
        private Integer cod_amount;
        private String payment;
        private String refnum;
        private List<String> remarks;
        private Boolean serviceable;
        private String sort_code;
        private String status;
        private String waybill;


    }
}
