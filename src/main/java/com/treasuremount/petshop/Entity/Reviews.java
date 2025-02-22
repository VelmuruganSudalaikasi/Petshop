package com.treasuremount.petshop.Entity;


import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "Reviews", schema = "public")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "productId", nullable = false)
    private Long productId;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comments", length = 1000)
    private String comments;

    @Column(name = "isApproved")
    private Boolean isApproved;

    @Column(name = "createdDate")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;


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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    @Override
    public String toString() {
        return "Reviews [id=" + id + ", userId=" + userId + ", productId=" + productId + ", rating=" + rating
                + ", comments=" + comments + ", isApproved=" + isApproved + ", createdDate=" + createdDate + "]";
    }


    public Reviews(Long id, Long userId, Long productId, Integer rating, String comments, Boolean isApproved,
                   Date createdDate, User user, Product product) {
        super();
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comments = comments;
        this.isApproved = isApproved;
        this.createdDate = createdDate;
        this.user = user;
        this.product = product;
    }


    public Reviews() {
        super();
        // TODO Auto-generated constructor stub
    }

}