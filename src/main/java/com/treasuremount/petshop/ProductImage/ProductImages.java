package com.treasuremount.petshop.ProductImage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.ProductImage.Position.Position;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ProductImages")
@Data
public class ProductImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Product product;

    @Column(name = "productId")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="positionId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Position position;

    @Column(name = "positionId")
    private Long positionId;

    @Column(name = "imageUrl" , length = 1000)
    private String imageUrl;


    @Column(name = "activeStatus")
    private Boolean activeStatus;


    public ProductImages() {
    }


}
