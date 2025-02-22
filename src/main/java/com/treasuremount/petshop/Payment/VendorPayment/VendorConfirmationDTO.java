package com.treasuremount.petshop.Payment.VendorPayment;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
public class VendorConfirmationDTO {

    private Long id;
    private List<String> uniqueOrderIds; // List of unique_order_id instead of orderId
    private BigDecimal totalAmount;
    private Date paymentDate;
    private String paymentReference;
    private String paymentMode;
    private String remarks;
    private Boolean vendorConfirmation;

    public VendorConfirmationDTO() {}

    public VendorConfirmationDTO(Long id, String uniqueOrderIds, BigDecimal totalAmount, Date paymentDate,
                                 String paymentReference, String paymentMode, String remarks,
                                 Boolean vendorConfirmation) {
        this.id = id;
        this.uniqueOrderIds = parseUniqueOrderIds(uniqueOrderIds); // Changed to parse uniqueOrderIds
        this.totalAmount = totalAmount;
        this.paymentDate = paymentDate;
        this.paymentReference = paymentReference;
        this.paymentMode = paymentMode;
        this.remarks = remarks;
        this.vendorConfirmation = vendorConfirmation;
    }

    private List<String> parseUniqueOrderIds(String uniqueOrderIds) {
        return List.of(uniqueOrderIds.split(","));
    }

    // Getters and Setters
}
