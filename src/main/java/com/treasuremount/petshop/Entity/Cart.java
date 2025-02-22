package com.treasuremount.petshop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cart",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userId", "productId"})
        }
)
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id",insertable = false,updatable = false)
    @JsonIgnore
    private Product product;

    @Column(name = "productId", nullable = false)
    private Long productId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id",insertable = false,updatable = false)
    @JsonIgnore
    private User user;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "quantity")
    private Long quantity;

}
