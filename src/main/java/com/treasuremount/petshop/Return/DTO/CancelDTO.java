package com.treasuremount.petshop.Return.DTO;


import com.treasuremount.petshop.Return.Entity.Cancel;
import com.treasuremount.petshop.Return.Entity.Return;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelDTO {
    private Long id;
    private Long orderId;
    private Long ProductId;
    private String cancelReason;
    private String cancelStatus; // Status of the return
    private Date requestedDate; // Date the return was requested
    private Date approvedDate; // Date the return was approved (if applicable)
    private Boolean isApproved;
    private Long userId;
    private Date createdDate;


    private Long returnQuantity;
    private Long stockQuantity;
    private String uniqueOrderId;

    public CancelDTO(Cancel obj) {
        this.id = obj.getId();
        this.orderId = obj.getOrderId();
        this.ProductId = obj.getProductId() ;
        this.cancelReason = obj.getCancelReason();
        this.cancelStatus = obj.getCancelStatus();
        this.requestedDate = obj.getRequestedDate();
        this.approvedDate = obj.getApprovedDate();
        this.isApproved = obj.getIsApproved();
        this.userId = obj.getUserId();
        this.createdDate = obj.getCreatedDate();
    }
    public Cancel DtoToCancel(CancelDTO obj){
        Cancel response=new Cancel();
        response.setId(obj.getId());
        response.setOrderId(obj.getOrderId());
        response.setProductId(obj.getProductId());
        response.setCancelReason(obj.getCancelReason());
        response.setCancelStatus(obj.getCancelStatus());
        response.setRequestedDate(obj.getRequestedDate());
        response.setApprovedDate(obj.getApprovedDate());
        response.setIsApproved(obj.getIsApproved());
        response.setUserId(obj.getUserId());
        response.setCreatedDate(obj.getCreatedDate());
        return response;
    }

}
