package com.treasuremount.petshop.Payment.VendorPayment;

import com.treasuremount.petshop.Entity.ShippingAddress;
import com.treasuremount.petshop.Order.Entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrdersDTO {

    private Long id;
    private Long userId;
    private Long ProductId;
    private BigDecimal unitPrice;
    private Long quantity;
    private BigDecimal tax;
    private BigDecimal shippingCharge;
    private BigDecimal subtotal;
    private Long OrderStatusId;
    private BigDecimal totalAmount;
    private Long ShippingAddressId;
    private Date orderDate;
    private Date deliveryDate;
    private Long vendorPaymentId;
    private Boolean isCancelled;
    private Boolean isReturned;
    private Boolean returnAmountIsPaid;

    // Constructor to map Orders entity to OrdersDTO
    public OrdersDTO(Orders order) {
        this.id = order.getId();
        this.userId = order.getUserId();
        this.ProductId = order.getProductId();
        this.unitPrice = order.getUnitPrice();
        this.quantity = order.getQuantity();
        this.tax = order.getTax();
        this.shippingCharge = order.getShippingCharge();
        this.subtotal = order.getSubtotal();
        this.OrderStatusId = order.getOrderStatusId();
        this.totalAmount = order.getTotalAmount();
        this.ShippingAddressId = order.getShippingAddressId();
        this.orderDate = order.getOrderDate();
        this.deliveryDate = order.getDeliveryDate();
        this.vendorPaymentId = order.getVendorPayment() != null ? order.getVendorPayment().getId() : null;
        this.isCancelled = order.getCancelled();
        this.isReturned = order.getReturned();
        this.returnAmountIsPaid = order.getIsVendorDeductionIsDone();
    }

    // Getters and setters
    // (Optional if you're using Lombok annotations like @Data or @Getter/@Setter)
}
