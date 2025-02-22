package com.treasuremount.petshop.Return.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Return.Entity.Return;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnDTO {
    private Long id;
    private Long orderId;
    private Long ProductId;
    private String returnReason;
    private String returnStatus; // Status of the return
    private Date requestedDate; // Date the return was requested
    private Date approvedDate; // Date the return was approved (if applicable)
    private Boolean isApproved;
    private Long userId;
    private Date createdDate;


    private Long returnQuantity;
    private Long stockQuantity;
    private String uniqueOrderId;

    public ReturnDTO(Return obj) {
        this.id = obj.getId();
        this.orderId = obj.getOrderId();
        this.ProductId = obj.getProductId();
        this.returnReason = obj.getReturnReason();
        this.returnStatus = obj.getReturnStatus();
        this.requestedDate = obj.getRequestedDate();
        this.approvedDate = obj.getApprovedDate();
        this.isApproved = obj.getIsApproved();
        this.userId = obj.getUserId();
        this.createdDate = obj.getCreatedDate();
    }
    public Return DtoToReturn(ReturnDTO obj){
            Return response=new Return();
        response.setId(obj.getId());
        response.setOrderId(obj.getOrderId());
        response.setProductId(obj.getProductId());
        response.setReturnReason(obj.getReturnReason());
        response.setReturnStatus(obj.getReturnStatus());
        response.setRequestedDate(obj.getRequestedDate());
        response.setApprovedDate(obj.getApprovedDate());
        response.setIsApproved(obj.getIsApproved());
        response.setUserId(obj.getUserId());
        response.setCreatedDate(obj.getCreatedDate());
            return response;
    }

}
