package com.treasuremount.petshop.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "ProductStatus", schema = "public")
public class ProductStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "activeStatus")
    private Boolean activeStatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }


    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", activeStatus=" + activeStatus + "]";
    }


    public ProductStatus(Long id, String name, Boolean activeStatus) {
        super();
        this.id = id;
        this.name = name;
        this.activeStatus = activeStatus;
    }


    public ProductStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

}