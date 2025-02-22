package com.treasuremount.petshop.DTO;

public class SubCategoryInfoImageDTO extends SubCategoryInfoDTO{

    private String imageUrl;

    public SubCategoryInfoImageDTO(Long id, String name, Long categoryId, String categoryName, Boolean activeStatus, String imageUrl) {
        super(id, name, categoryId, categoryName, activeStatus);
        this.imageUrl = imageUrl;
    }

    public SubCategoryInfoImageDTO(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
