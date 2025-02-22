package com.treasuremount.petshop.DTO;

public class ApprovalUpdateDTO {

    private Long productId;
    private Boolean isApproved;

    public ApprovalUpdateDTO(Long productId, Boolean isApproved) {
        this.productId = productId;
        this.isApproved = isApproved;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
}
