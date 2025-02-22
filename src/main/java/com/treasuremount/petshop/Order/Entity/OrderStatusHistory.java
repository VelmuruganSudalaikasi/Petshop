package com.treasuremount.petshop.Order.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Order.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "order_status_history") // Maps the entity to the "order_status_history" table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // Many status changes can belong to one Order
    @JoinColumn(name = "orderId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private Orders order;

    @Column(name="orderId")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY) // Many status changes can be made by one User
    @JoinColumn(name = "changed_by", referencedColumnName = "id", insertable=false, updatable=false) // Foreign key column
    @JsonIgnore
    private User changedBy;

    @Column(name="UserId")
    private Long UserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderStatusId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private OrderStatusS orderStatusS;

    @Column(name = "OrderStatusId")
    private Long OrderStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previousStatusId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private OrderStatusS previousOrderStatus;

    @Column(name = "previousStatusId")
    private Long previousStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newStatusId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private OrderStatusS newOrderStatus;

/*
    @Column(name = "newStatusId")
    private Long newStatus;
*/

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false)
    private Date createdDate;

}
