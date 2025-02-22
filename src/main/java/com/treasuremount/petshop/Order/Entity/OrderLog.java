package com.treasuremount.petshop.Order.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.Category;
import com.treasuremount.petshop.Entity.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "OrderLog")
public class OrderLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    //product Id

    @ManyToOne
    @JoinColumn(name = "ProductId", insertable = false,updatable = false)
    @JsonIgnore
    private Product product;

    @Column(name="ProductId")
    private Long ProductId;


    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false)
    private Date createdDate;


//categoryID

    @ManyToOne
    @JoinColumn(name = "CategoryId",referencedColumnName = "id",insertable = false,updatable = false)
    @JsonIgnore
    private Category category;

    @Column(name="CategoryId")
    private Long CategoryId;



    // previous quantity

    private int oldQuantity;

    //new quantity

    private int newQuantity;

    public OrderLog(Long id, Product product, Long productId, Category category, Long categoryId, int oldQuantity, int newQuantity) {
        this.id = id;
        this.product = product;
        ProductId = productId;
        this.category = category;
        CategoryId = categoryId;
        this.oldQuantity = oldQuantity;
        this.newQuantity = newQuantity;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public OrderLog(){}

    public Long getProductId() {
        return ProductId;
    }

    public void setProductId(Long productId) {
        ProductId = productId;
    }

    public Long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Long categoryId) {
        CategoryId = categoryId;
    }

    public int getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(int oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
