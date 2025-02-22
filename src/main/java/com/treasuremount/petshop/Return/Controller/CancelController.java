package com.treasuremount.petshop.Return.Controller;
import com.treasuremount.petshop.Return.DTO.CancelDTO;
import com.treasuremount.petshop.Return.Entity.Cancel;
import com.treasuremount.petshop.Return.Entity.Return;
import com.treasuremount.petshop.Return.Service.CancelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/cancel")
public class CancelController {

    @Autowired
    private CancelService service;


 /*   @GetMapping("/eligible-products")
    public ResponseEntity<List<Product>> getEligibleProductsForReturn(@RequestParam Long userId) {
        List<Product> eligibleProducts = service.getEligibleProductsForReturn(userId);
        return ResponseEntity.ok(eligibleProducts);
    }*/

    @GetMapping("/not-approved-requests")
    public ResponseEntity<List<CancelDTO>> getNotApprovedRequests(@RequestParam Long vendorUserId) {
        List<CancelDTO> notApprovedRequests = service.getNotApprovedRequests(vendorUserId);
        return ResponseEntity.ok(notApprovedRequests);
    }

    @PostMapping("/approve-cancel/")
    public ResponseEntity<CancelDTO> approveReturn(@RequestBody CancelDTO request) {
        CancelDTO updatedReturn = service.approveReturnRequest(request);
        if (updatedReturn == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedReturn);
    }

    @PostMapping("/add")
    public ResponseEntity<Cancel> createUser(@RequestBody @Valid Cancel user) {
        Cancel createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Cancel>> getAllUsers() {
        List<Cancel> users = service.getAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Cancel> getOneUser(@PathVariable("id") Long id) {
        Cancel user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<Cancel>> getAllByUserId(@PathVariable("userId") Long userId,@RequestParam("Approved") Boolean isApproved){
        List<Cancel> listOfCancel=service.getCancelListByUserId(userId,isApproved);
        return ResponseEntity.ok(listOfCancel);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cancel> updateUser(@RequestBody @Valid Cancel user, @PathVariable("id") Long id) {
        Cancel updatedUser = service.update(user, id);
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


