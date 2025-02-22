package com.treasuremount.petshop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "PetDetails")
public class PetDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PetsId", insertable = false,updatable = false)
    @JsonIgnore
    private  Pets pets;

    @Column(name= "PetId" )
    private Long PetId;

    @Column(columnDefinition = "TEXT")
    private String about;

    @Column(name = "health_info", columnDefinition = "TEXT")
    private String healthInfo;

    @Column(name = "care_instructions", columnDefinition = "TEXT")
    private String careInstructions;

    @Column(name = "special_requirements", columnDefinition = "TEXT")
    private String specialRequirements;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    // Constructors


    @Override
    public String toString() {
        return "PetDetails{" +
                "id=" + id +
                ", pets=" + pets +
                ", PetId=" + PetId +
                ", about='" + about + '\'' +
                ", healthInfo='" + healthInfo + '\'' +
                ", careInstructions='" + careInstructions + '\'' +
                ", specialRequirements='" + specialRequirements + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public PetDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetId() {
        return PetId;
    }

    public void setPetId(Long petId) {
        PetId = petId;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getHealthInfo() {
        return healthInfo;
    }

    public void setHealthInfo(String healthInfo) {
        this.healthInfo = healthInfo;
    }

    public String getCareInstructions() {
        return careInstructions;
    }

    public void setCareInstructions(String careInstructions) {
        this.careInstructions = careInstructions;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }

    public void setSpecialRequirements(String specialRequirements) {
        this.specialRequirements = specialRequirements;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public PetDetails(Long id, Pets pets, Long petId, String about, String healthInfo, String careInstructions, String specialRequirements, Date createdAt) {
        this.id = id;
        this.pets = pets;
        PetId = petId;
        this.about = about;
        this.healthInfo = healthInfo;
        this.careInstructions = careInstructions;
        this.specialRequirements = specialRequirements;
        this.createdAt = createdAt;
    }
}
