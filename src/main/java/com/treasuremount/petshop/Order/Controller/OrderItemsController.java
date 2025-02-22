/*
package com.treasuremount.petshop.Order.Controller;
import com.treasuremount.petshop.Order.Entity.OrderItems;
import com.treasuremount.petshop.Order.Service.OrderItemsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/orderItems")
public class OrderItemsController {

    @Autowired
    private OrderItemsService service;


    @PostMapping("/add")
    public ResponseEntity<OrderItems> createUser(@RequestBody @Valid OrderItems user) {
        OrderItems createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<OrderItems>> getAllUsers() {
        List<OrderItems> users = service.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<OrderItems> getOneUser(@PathVariable("id") Long id) {
        OrderItems user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderItems> updateUser(@RequestBody @Valid OrderItems user, @PathVariable("id") Long id) {
        OrderItems updatedUser = service.update(user, id);
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
