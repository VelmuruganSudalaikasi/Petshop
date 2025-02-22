package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.Entity.Inventory;
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
@RequestMapping("/api/public/inventory")
public class InventoryController {

    @Autowired
    private InventoryImpl service;


    @PostMapping("/add")
    public ResponseEntity<Inventory> createUser(@RequestBody @Valid Inventory user) {
        Inventory createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Inventory>> getAllUsers() {
        List<Inventory> users = service.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Inventory> getOneUser(@PathVariable("id") Long id) {
        Inventory user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Inventory> updateUser(@RequestBody @Valid Inventory user, @PathVariable("id") Long id) {
        Inventory updatedUser = service.update(user, id);
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
