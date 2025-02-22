package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.Entity.BankDetails;
import com.treasuremount.petshop.Entity.Inventory;
import com.treasuremount.petshop.Repository.BankDetailsService;
import com.treasuremount.petshop.Service.InventoryImpl;
import com.treasuremount.petshop.Service.Service;
import com.treasuremount.petshop.Service.VendorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/bankDetails")
public class BankDetailsController {

    @Autowired
    private BankDetailsService service;


    @PostMapping("/add")
    public ResponseEntity<BankDetails> createUser(@RequestBody @Valid BankDetails user) {
        BankDetails createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<BankDetails>> getAllUsers() {
        List<BankDetails> users = service.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<BankDetails> getOneUser(@PathVariable("id") Long id) {
        BankDetails user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BankDetails> updateUser(@RequestBody @Valid BankDetails user, @PathVariable("id") Long id) {
        BankDetails updatedUser = service.update(user, id);
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
