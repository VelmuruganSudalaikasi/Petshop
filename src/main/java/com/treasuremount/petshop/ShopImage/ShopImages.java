package com.treasuremount.petshop.ShopImage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.ShopImage.Position.ShopImagePosition;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ShopImages")
@Data
public class ShopImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vendorId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Vendor vendor;

    @Column(name = "vendorId")
    private Long vendorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="positionId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private ShopImagePosition position;

    @Column(name = "positionId")
    private Long positionId;

    @Column(name = "imageUrl" , length = 1000)
    private String imageUrl;


    @Column(name = "activeStatus")
    private Boolean activeStatus;


    public ShopImages() {
    }
}
