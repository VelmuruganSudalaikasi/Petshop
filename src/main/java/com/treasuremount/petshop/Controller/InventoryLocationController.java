package com.treasuremount.petshop.Controller;


import com.treasuremount.petshop.Entity.InventoryLocation;
import com.treasuremount.petshop.Service.InventoryLocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/inventorylocation")
public class InventoryLocationController {

    @Autowired
    private InventoryLocationService service;

    @PostMapping("/add")
    public ResponseEntity<InventoryLocation> createUser(@RequestBody @Valid InventoryLocation user) {
        InventoryLocation createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }



    @GetMapping("/getAll")
    public ResponseEntity<List<InventoryLocation>> getAllUsers() {
        List<InventoryLocation> users = service.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<InventoryLocation> getOneUser(@PathVariable("id") Long id) {
        InventoryLocation user = service.getOneUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<InventoryLocation> updateUser(@RequestBody @Valid InventoryLocation user, @PathVariable("id") Long id) {
        InventoryLocation updatedUser = service.update(user, id);
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

