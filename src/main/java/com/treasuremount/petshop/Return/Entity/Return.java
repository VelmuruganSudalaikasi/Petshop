package com.treasuremount.petshop.Return.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Order.Entity.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "returns")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // Primary Key

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private Orders orders;

    @Column(name = "orderId")
    private Long orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductId", insertable = false,updatable = false)
    @JsonIgnore
    private Product product;

    @Column(name="ProductId")
    private Long ProductId;


    @Column(name = "return_reason", nullable = false)
    private String returnReason; // Reason for return

    @Column(name = "return_status", nullable = false, length = 50)
    private String returnStatus; // Status of the return

    @Column(name = "requested_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date requestedDate; // Date the return was requested

    @Column(name = "approved_date")
    @Temporal(TemporalType.DATE)
    private Date approvedDate; // Date the return was approved (if applicable)

    private Boolean isApproved;


    @OneToOne()
    @JoinColumn(name = "approved_by", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private User user;

    @Column(name = "approved_by")
    private Long userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date createdDate; // Entity creation timestamp

}
