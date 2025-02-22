package com.treasuremount.petshop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "FoodDetails")
public class FoodDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "FoodsId", insertable = false,updatable = false)
    @JsonIgnore
    private  Foods foods;

    @Column(name= "FoodsId" )
    private Long FoodsId;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(name = "nutritional_info", columnDefinition = "TEXT")
    private String nutritionalInfo;

    @Column(name = "storage_instructions", columnDefinition = "TEXT")
    private String storageInstructions;

    @Column(name = "feeding_guidelines", columnDefinition = "TEXT")
    private String feedingGuidelines;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public FoodDetails() {
    }

    @Override
    public String toString() {
        return "FoodDetails{" +
                "id=" + id +
                ", foods=" + foods +
                ", FoodsId=" + FoodsId +
                ", ingredients='" + ingredients + '\'' +
                ", nutritionalInfo='" + nutritionalInfo + '\'' +
                ", storageInstructions='" + storageInstructions + '\'' +
                ", feedingGuidelines='" + feedingGuidelines + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodsId() {
        return FoodsId;
    }

    public void setFoodsId(Long foodsId) {
        FoodsId = foodsId;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getNutritionalInfo() {
        return nutritionalInfo;
    }

    public void setNutritionalInfo(String nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }

    public String getStorageInstructions() {
        return storageInstructions;
    }

    public void setStorageInstructions(String storageInstructions) {
        this.storageInstructions = storageInstructions;
    }

    public String getFeedingGuidelines() {
        return feedingGuidelines;
    }

    public void setFeedingGuidelines(String feedingGuidelines) {
        this.feedingGuidelines = feedingGuidelines;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public FoodDetails(Long id, Foods foods, Long foodsId, String ingredients, String nutritionalInfo, String storageInstructions, String feedingGuidelines, Date createdAt) {
        this.id = id;
        this.foods = foods;
        FoodsId = foodsId;
        this.ingredients = ingredients;
        this.nutritionalInfo = nutritionalInfo;
        this.storageInstructions = storageInstructions;
        this.feedingGuidelines = feedingGuidelines;
        this.createdAt = createdAt;
    }
}
