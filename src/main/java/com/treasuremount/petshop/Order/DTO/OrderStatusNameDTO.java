package com.treasuremount.petshop.Order.DTO;



import java.math.BigDecimal;
import java.util.Date;

public class OrderStatusNameDTO {

    private Long id;
    private Long userId;
    private Long productId;
    private BigDecimal unitPrice;
    private Long quantity;
    private BigDecimal tax;
    private BigDecimal shippingCharge;
    private BigDecimal subtotal;
    private Long orderStatusId;
    private String orderStatusName;
    private BigDecimal totalAmount;
    private Long ShippingAddressId;
    private Date orderDate;
    private Date deliveryDate;
    private Boolean isCancelled;
    private Boolean isReturned;

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(BigDecimal shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getShippingAddressId() {
        return ShippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        ShippingAddressId = shippingAddressId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public Boolean getReturned() {
        return isReturned;
    }

    public void setReturned(Boolean returned) {
        isReturned = returned;
    }

    public OrderStatusNameDTO(Long shippingAddressId, Long id, Long userId, Long productId, BigDecimal unitPrice, Long quantity, BigDecimal tax, BigDecimal shippingCharge, BigDecimal subtotal, Long orderStatusId, String orderStatusName, BigDecimal totalAmount, Date orderDate, Date deliveryDate, Boolean isCancelled, Boolean isReturned) {
        ShippingAddressId = shippingAddressId;
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.tax = tax;
        this.shippingCharge = shippingCharge;
        this.subtotal = subtotal;
        this.orderStatusId = orderStatusId;
        this.orderStatusName = orderStatusName;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.isCancelled = isCancelled;
        this.isReturned = isReturned;
    }

    @Override
    public String toString() {
        return "OrderStatusNameDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", tax=" + tax +
                ", shippingCharge=" + shippingCharge +
                ", subtotal=" + subtotal +
                ", orderStatusId=" + orderStatusId +
                ", orderStatusName='" + orderStatusName + '\'' +
                ", totalAmount=" + totalAmount +
                ", ShippingAddressId=" + ShippingAddressId +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", isCancelled=" + isCancelled +
                ", isReturned=" + isReturned +
                '}';
    }

    public OrderStatusNameDTO() {
    }
}