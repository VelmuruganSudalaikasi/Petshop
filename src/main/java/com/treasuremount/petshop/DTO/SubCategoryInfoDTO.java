package com.treasuremount.petshop.DTO;


public class SubCategoryInfoDTO {


    private Long id;


    private String name;


    private Long categoryId;


    private Boolean activeStatus;


    private String categoryName;

    public SubCategoryInfoDTO(Long id, String name, Long categoryId, String categoryName, Boolean activeStatus) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.activeStatus = activeStatus;
        this.categoryName = categoryName;
    }

    public SubCategoryInfoDTO(){}

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "SubCategoryInfoDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", activeStatus=" + activeStatus +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
