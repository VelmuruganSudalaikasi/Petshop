/*
package com.treasuremount.petshop.Controller;


import com.treasuremount.petshop.Entity.Pets;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.Service.PetService;
import com.treasuremount.petshop.Service.ProductServiceImpl;
import com.treasuremount.petshop.Service.VendorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/pets")
public class PetController {

    @Autowired

    private PetService service;

    @PostMapping("/add")
    public ResponseEntity<Pets> createUser(@RequestBody @Valid Pets user) {
        Pets createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Pets>> getAllUsers() {
        List<Pets> users = service.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Pets> getOneUser(@PathVariable("id") Long id) {
        Pets user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Pets> updateUser(@RequestBody @Valid Pets user, @PathVariable("id") Long id) {
        Pets updatedUser = service.update(user, id);
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
*/
