package com.treasuremount.petshop.Return.Controller;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Return.DTO.ReturnDTO;
import com.treasuremount.petshop.Return.Entity.Cancel;
import com.treasuremount.petshop.Return.Entity.Return;
import com.treasuremount.petshop.Return.Service.ReturnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/return")
public class ReturnController {

    @Autowired
    private ReturnService service;


 /*   @GetMapping("/eligible-products")
    public ResponseEntity<List<Product>> getEligibleProductsForReturn(@RequestParam Long userId) {
        List<Product> eligibleProducts = service.getEligibleProductsForReturn(userId);
        return ResponseEntity.ok(eligibleProducts);
    }*/

    @GetMapping("/not-approved-requests")
    public ResponseEntity<List<ReturnDTO>> getNotApprovedRequests(@RequestParam Long vendorUserId) {
        List<ReturnDTO> notApprovedRequests = service.getNotApprovedRequests(vendorUserId);
        return ResponseEntity.ok(notApprovedRequests);
    }

    @PostMapping("/approve-return/")
    public ResponseEntity<ReturnDTO> approveReturn(@RequestBody ReturnDTO request) {
        ReturnDTO updatedReturn = service.approveReturnRequest(request);
        if (updatedReturn == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedReturn);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<Return>> getAllByUserId(@PathVariable("userId") Long userId, @RequestParam("Approved") Boolean isApproved){
        List<Return> listOfCancel=service.getCancelListByUserId(userId,isApproved);
        return ResponseEntity.ok(listOfCancel);
    }



  @PostMapping("/add")
    public ResponseEntity<Return> createUser(@RequestBody @Valid Return user) {
        Return createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Return>> getAllUsers() {
        List<Return> users = service.getAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Return> getOneUser(@PathVariable("id") Long id) {
        Return user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Return> updateUser(@RequestBody @Valid Return user, @PathVariable("id") Long id) {
        Return updatedUser = service.update(user, id);
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
    }


}


