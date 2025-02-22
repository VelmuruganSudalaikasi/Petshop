package com.treasuremount.petshop.Delivery.ClientWarehouseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientWarehouseResponseDTO {
    private DataDTO data;
    private String error;
    private Boolean success;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataDTO {
        private boolean active;
        private String address;
        private List<String> businessDays;
        private Map<String, BusinessHoursDTO> businessHours;
        private String client;
        private String largestVehicleConstraint;
        private String message;
        private String name;
        private String phone;
        private Integer pincode;
        private String typeOfClientwarehouse;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusinessHoursDTO {
        private String closeTime;
        private String startTime;
    }
}

