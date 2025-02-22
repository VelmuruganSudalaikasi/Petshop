package com.treasuremount.petshop.Delivery;

import lombok.Data;

@Data
public class ShipmentRequest {
    private String name; // Customer name
    private String address; // Delivery address
    private String phone; // Customer phone number
    private String pinCode; // Delivery PIN code
    private String orderId; // Your shop's order ID
    private double weight; // Package weight
    private String paymentMode; // Payment mode (e.g., Prepaid, COD)
}