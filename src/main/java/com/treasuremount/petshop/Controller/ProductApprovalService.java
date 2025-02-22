package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.DTO.ApprovalDTO;
import com.treasuremount.petshop.DTO.ApprovalUpdateDTO;
import com.treasuremount.petshop.Service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProductApprovalService {


    @Autowired
    ProductServiceImpl productService;


    public List<ApprovalDTO> getAll(){
        return productService.getAllApprovalData();
    }

    public void updateStatus(List<ApprovalDTO> lst){
        productService.updateApprovalStatus(lst);

    }

    public ApprovalDTO getOne(Long productId){
        return productService.getApprovalDataById(productId);

    }


}
