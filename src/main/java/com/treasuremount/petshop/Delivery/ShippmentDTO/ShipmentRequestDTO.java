package com.treasuremount.petshop.Delivery.ShippmentDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class




ShipmentRequestDTO {
    private List<Shipment> shipments;
    private PickupLocation pickup_location;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Shipment {
        @NotNull
        private String name;
        @NotNull
        private String add;
        @NotNull
        private String pin;
        private String city;
        private String state;
        private String country;
        @NotNull
        private String phone;
        @NotNull
        private String order;
        @NotNull
        private String payment_mode;
        private String return_pin;
        private String return_city;
        private String return_phone;
        private String return_add;
        private String return_state;
        private String return_country;
        private String products_desc;
        private String hsn_code;
        private String cod_amount;
        private String order_date;
        private String total_amount;
        private String seller_add;
        private String seller_name;
        private String seller_inv;
        private String quantity;
        @NotNull
        private String waybill;
        private String shipment_width;
        private String shipment_height;
        private String weight;
        private String seller_gst_tin;
        @NotNull
        private String shipping_mode;
        private String address_type;

        // Getters and Setters
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PickupLocation {
        @NotNull
        private String name;
        @NotNull
        private String add;
        @NotNull
        private String city;
        @NotNull
        private String pin_code;
        @NotNull
        private String country;
        @NotNull
        private String phone;

    }
}
