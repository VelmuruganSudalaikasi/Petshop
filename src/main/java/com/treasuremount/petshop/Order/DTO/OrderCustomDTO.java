package com.treasuremount.petshop.Order.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NegativeOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;  // You could also use LocalDateTime if time is necessary
import java.util.Date;

@Data
@NoArgsConstructor
public class OrderCustomDTO {

    private String productName;
    private BigDecimal productPrice;  // Changed to BigDecimal for better precision with monetary values
    private String contactNumber;
    private String vendorName;
    private Long subCategoryId;
    private String subCategoryName;
    private Long categoryId;
    private String categoryName;
    private Long id;
    private String uniqueId;
    private Long userId;
    private Long productId;  // Corrected to camelCase
    private Long quantity;
    private BigDecimal tax;
    private BigDecimal shippingCharge;
    private BigDecimal subtotal;
    private Long orderStatusId;
    private BigDecimal totalAmount;
    private LocalDate orderDate;  // Changed to LocalDate for better handling of dates
    private LocalDate deliveryDate;  // Changed to LocalDate
    private Boolean isCancelled;
    private Boolean isReturned;

    // Validation on fields like tax and shippingCharge to ensure they are not negative
    @NegativeOrZero(message = "Tax must be zero or positive")
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    @NegativeOrZero(message = "Shipping charge must be zero or positive")
    public BigDecimal getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(BigDecimal shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    @NegativeOrZero(message = "Subtotal must be zero or positive")
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @NegativeOrZero(message = "Total amount must be zero or positive")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderCustomDTO(String productName, BigDecimal productPrice, String contactNumber, String vendorName, Long subCategoryId, String subCategoryName, Long categoryId, String categoryName, Long id, Long userId, Long productId, Long quantity, BigDecimal tax, BigDecimal shippingCharge, BigDecimal subtotal, Long orderStatusId, BigDecimal totalAmount, LocalDate orderDate, LocalDate deliveryDate, Boolean isCancelled, Boolean isReturned) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.contactNumber = contactNumber;
        this.vendorName = vendorName;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.tax = tax;
        this.shippingCharge = shippingCharge;
        this.subtotal = subtotal;
        this.orderStatusId = orderStatusId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.isCancelled = isCancelled;
        this.isReturned = isReturned;
    }

    @Override
    public String toString() {
        return "OrderCustomDTO{" +
                "productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", contactNumber='" + contactNumber + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", subCategoryId=" + subCategoryId +
                ", subCategoryName='" + subCategoryName + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", tax=" + tax +
                ", shippingCharge=" + shippingCharge +
                ", subtotal=" + subtotal +
                ", orderStatusId=" + orderStatusId +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", isCancelled=" + isCancelled +
                ", isReturned=" + isReturned +
                '}';
    }
}