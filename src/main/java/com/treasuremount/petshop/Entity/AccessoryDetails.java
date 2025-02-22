package com.treasuremount.petshop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "AccessoryDetails")
public class AccessoryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "AccessoriesId", insertable = false,updatable = false)
    @JsonIgnore
    private  Accessories accessories;

    @Column(name= "AccessoriesId" )
    private Long AccessoriesId;

    @Column(columnDefinition = "TEXT")
    private String specifications;

    @Column(name = "usage_instructions", columnDefinition = "TEXT")
    private String usageInstructions;

    @Column(name = "care_instructions", columnDefinition = "TEXT")
    private String careInstructions;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public AccessoryDetails(Long id, Accessories accessories, Long accessoriesId, String specifications, String usageInstructions, String careInstructions, Date createdAt) {
        this.id = id;
        this.accessories = accessories;
        AccessoriesId = accessoriesId;
        this.specifications = specifications;
        this.usageInstructions = usageInstructions;
        this.careInstructions = careInstructions;
        this.createdAt = createdAt;
    }

    public AccessoryDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccessoriesId() {
        return AccessoriesId;
    }

    public void setAccessoriesId(Long accessoriesId) {
        AccessoriesId = accessoriesId;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getUsageInstructions() {
        return usageInstructions;
    }

    public void setUsageInstructions(String usageInstructions) {
        this.usageInstructions = usageInstructions;
    }

    public String getCareInstructions() {
        return careInstructions;
    }

    public void setCareInstructions(String careInstructions) {
        this.careInstructions = careInstructions;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AccessoryDetails{" +
                "id=" + id +
                ", accessories=" + accessories +
                ", AccessoriesId=" + AccessoriesId +
                ", specifications='" + specifications + '\'' +
                ", usageInstructions='" + usageInstructions + '\'' +
                ", careInstructions='" + careInstructions + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
