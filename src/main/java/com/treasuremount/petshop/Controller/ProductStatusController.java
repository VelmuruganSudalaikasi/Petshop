package com.treasuremount.petshop.Controller;


import com.treasuremount.petshop.Entity.ProductStatus;
import com.treasuremount.petshop.Service.ProductStatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/public/productStatus")
public class ProductStatusController {

    @Autowired
    private ProductStatusService service;


    @PostMapping("/add")
    public ResponseEntity<ProductStatus> createUser(@RequestBody @Valid ProductStatus user) {
        ProductStatus createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<ProductStatus>> getAllUsers() {
        List<ProductStatus> users = service.getAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ProductStatus> getOneUser(@PathVariable("id") Long id) {
        ProductStatus user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductStatus> updateUser(@RequestBody @Valid ProductStatus user, @PathVariable("id") Long id) {
        ProductStatus updatedUser = service.update(user, id);
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

