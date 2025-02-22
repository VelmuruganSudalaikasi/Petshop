package com.treasuremount.petshop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;


@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

   @Column(name = "VendorId")
   private Long VendorId;

  @ManyToOne
  @JoinColumn(name = "VendorId",referencedColumnName = "id", insertable=false, updatable=false)
  @JsonIgnore
  private Vendor vendor;


 @Column(name = "InventoryLocationId")
 private Long InventoryLocationId;

  @JoinColumn(name = "InventoryLocationId",referencedColumnName = "id",insertable=false, updatable=false)
  @OneToOne
  @JsonIgnore
  private InventoryLocation inventoryLocation;



  @OneToOne
  @JoinColumn(name = "CategoryId",referencedColumnName = "id",insertable=false, updatable=false)
  @JsonIgnore
  private Category category;

  @Column(name = "CategoryId")
  private Long CategoryId;

  private Long totalItems;
  @Temporal(TemporalType.DATE)
  private Date createdAt;
  @Temporal(TemporalType.DATE)
  private Date modifiedAt;

    public Inventory() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return VendorId;
    }

    public void setVendorId(Long vendorId) {
        VendorId = vendorId;
    }

    public Long getInventoryLocationId() {
        return InventoryLocationId;
    }

    public void setInventoryLocationId(Long inventoryLocationId) {
        InventoryLocationId = inventoryLocationId;
    }

    public Long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Long categoryId) {
        CategoryId = categoryId;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
