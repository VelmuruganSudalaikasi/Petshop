/*
package com.treasuremount.petshop.Controller;


import com.treasuremount.petshop.Entity.Accessories;
import com.treasuremount.petshop.Order.Entity.OrderLog;
import com.treasuremount.petshop.Service.AccessoriesImpl;
import com.treasuremount.petshop.Service.OrderLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/log")
public class OrderLogController {

    @Autowired
    private OrderLogService service;


    @PostMapping("/add")
    public ResponseEntity<OrderLog> createUser(@RequestBody @Valid OrderLog user) {
        OrderLog createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<OrderLog>> getAllUsers() {
        List<OrderLog> users = service.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<OrderLog> getOneUser(@PathVariable("id") Long id) {
        OrderLog user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderLog> updateUser(@RequestBody @Valid OrderLog user, @PathVariable("id") Long id) {
        OrderLog updatedUser = service.update(user, id);
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
