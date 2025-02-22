package com.treasuremount.petshop.Order.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Entity.ShippingAddress;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Payment.CustomerRefund.CustomerPayment;
import com.treasuremount.petshop.Payment.RefundController.RefundPayment;
import com.treasuremount.petshop.Payment.VendorPayment.VendorPayment;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Many Orders can belong to one User
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private User user;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "unique_order_id",nullable =false,  unique = true)
    @JsonIgnore
    private String uniqueOrderId;

    @ManyToOne
    @JoinColumn(name = "ProductId", insertable = false,updatable = false)
    @JsonIgnore
    private Product product;

    @Column(name="ProductId")
    private Long ProductId;

    @Column(name = "unit_price",nullable = false)
    private BigDecimal unitPrice;

  //from orderItem
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "tax", nullable = false, precision = 10, scale = 2)
    private BigDecimal tax;

    @Column(name = "shipping_charge", nullable = false, precision = 10, scale = 2)
    private BigDecimal shippingCharge;


    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // order Item end

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderStatusId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private OrderStatusS orderStatusS;

    @Column(name = "OrderStatusId")
    private Long OrderStatusId;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ShippingAddressId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private ShippingAddress shippingAddress;

    @Column(name = "ShippingAddressId")
    private Long ShippingAddressId;

    @Temporal(TemporalType.DATE)
    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "delivery_date")
    private Date deliveryDate;

    //admin to vendor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendorPaymentId",referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private VendorPayment vendorPayment;

    @Column(name = "is_cancelled", nullable = false)
    private Boolean isCancelled;

    @Column(name = "is_returned", nullable = false)
    private Boolean isReturned;

    @JsonIgnore
    private Boolean isVendorDeductionIsDone;


    @PrePersist
    private void setIsVendorDeductionIsDone(){
        this.isVendorDeductionIsDone=false;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerPaymentId",referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private CustomerPayment customerPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refundPaymentId",referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private RefundPayment refundPayment;


    public Orders(){}

    public Orders(Long id, User user, Long userId, Product product, Long productId, BigDecimal unitPrice, Long quantity, BigDecimal tax, BigDecimal shippingCharge, BigDecimal subtotal, OrderStatusS orderStatusS, Long orderStatusId, BigDecimal totalAmount, ShippingAddress shippingAddress, Long shippingAddressId, Date orderDate, Date deliveryDate, Boolean isCancelled, Boolean isReturned) {
        this.id = id;
        this.user = user;
        this.userId = userId;
        this.product = product;
        ProductId = productId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.tax = tax;
        this.shippingCharge = shippingCharge;
        this.subtotal = subtotal;
        this.orderStatusS = orderStatusS;
        OrderStatusId = orderStatusId;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        ShippingAddressId = shippingAddressId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.isCancelled = isCancelled;
        this.isReturned = isReturned;
    }


    public Boolean getIsVendorDeductionIsDone() {
        return isVendorDeductionIsDone;
    }

    public void setIsVendorDeductionIsDone(Boolean vendorDeductionIsDone) {
        isVendorDeductionIsDone = vendorDeductionIsDone;
    }


    public String getUniqueOrderId() {
        return uniqueOrderId;
    }

    public void setUniqueOrderId(String uniqueOrderId) {
        this.uniqueOrderId = uniqueOrderId;
    }



    public CustomerPayment getCustomerPayment() {
        return customerPayment;
    }

    public void setCustomerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
    }

    public VendorPayment getVendorPayment() {
        return vendorPayment;
    }

    public void setVendorPayment(VendorPayment vendorPayment) {
        this.vendorPayment = vendorPayment;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getProductId() {
        return ProductId;
    }

    public void setProductId(Long productId) {
        ProductId = productId;
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

    public OrderStatusS getOrderStatusS() {
        return orderStatusS;
    }

    public void setOrderStatusS(OrderStatusS orderStatusS) {
        this.orderStatusS = orderStatusS;
    }

    public Long getOrderStatusId() {
        return OrderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        OrderStatusId = orderStatusId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
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

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
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
}

