package com.treasuremount.petshop.Controller;



import com.treasuremount.petshop.DTO.ApprovalDTO;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Service.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/approval")
public class ProductApproval {

   /* @Autowired
    private ProductServiceImpl service;*/

    /*userId shop name  productDetails approval*/

    @Autowired
    ProductApprovalService approvalService;


    @GetMapping("/getAll")
    public ResponseEntity<List<ApprovalDTO>> getAllApprovals() {
        List<ApprovalDTO> approvalDTOs = approvalService.getAll();
        return ResponseEntity.ok(approvalDTOs);
    }

    @PutMapping("/updateList")
    public ResponseEntity<String> updateApprovalStatuses(@RequestBody List<ApprovalDTO> approvalDTOList) {
        approvalService.updateStatus(approvalDTOList);
        return ResponseEntity.ok("Approval statuses updated successfully.");
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApprovalDTO> getIdByApprovals(@PathVariable("id")Long id) {
        ApprovalDTO approvalDTOs = approvalService.getOne(id);
        if(approvalDTOs!=null){
            return ResponseEntity.ok(approvalDTOs);

        }

        return ResponseEntity.notFound().build();
    }






/*
    @PostMapping("/add")
    public ResponseEntity<Product> createUser(@RequestBody @Valid Product user) {
        Product createdUser = service.create(user);
        if(createdUser!=null) {
            return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(createdUser,HttpStatus.NOT_FOUND);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllUsers() {
        List<Product> users = service.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Product> getOneUser(@PathVariable("id") Long id) {
        Product user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateUser(@RequestBody @Valid Product user, @PathVariable("id") Long id) {
        Product updatedUser = service.update(user, id);
        if (updatedUser != null) {
            return ResponseEntity.ok().body(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }*/


}
