/*
package com.treasuremount.petshop.Order.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.Product;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItems {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", referencedColumnName = "id", insertable=false, updatable=false)
    private Orders orders;

    @Column(name = "orderId")
    private Long orderId;


    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "tax", nullable = false, precision = 10, scale = 2)
    private BigDecimal tax;

    @Column(name = "shipping_charge", nullable = false, precision = 10, scale = 2)
    private BigDecimal shippingCharge;

   */
/* @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;*//*


    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;


    public OrderItems(){}

    public OrderItems(Long id, Orders orders, Long orderId, int quantity, BigDecimal tax, BigDecimal shippingCharge, BigDecimal subtotal) {
        this.id = id;
        this.orders = orders;
        this.orderId = orderId;
        this.quantity = quantity;
        this.tax = tax;
        this.shippingCharge = shippingCharge;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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

    @Override
    public String toString() {
        return "OrderItems{" +
                "id=" + id +
                ", orders=" + orders +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                ", tax=" + tax +
                ", shippingCharge=" + shippingCharge +
                ", subtotal=" + subtotal +
                '}';
    }
}
*/
