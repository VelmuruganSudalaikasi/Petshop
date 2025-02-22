package com.treasuremount.petshop.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Messages", schema = "public")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "productId", nullable = false)
    private Long productId;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "status")
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "createdDate")
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    @Override
    public String toString() {
        return "Messages [id=" + id + ", userId=" + userId + ", productId=" + productId + ", content=" + content
                + ", status=" + status + ", createdDate=" + createdDate + "]";
    }


    public Messages(Long id, Long userId, Long productId, String content, String status, Date createdDate, User user,
                    Product product) {
        super();
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.content = content;
        this.status = status;
        this.createdDate = createdDate;
        this.user = user;
        this.product = product;
    }


    public Messages() {
        super();
        // TODO Auto-generated constructor stub
    }

}
