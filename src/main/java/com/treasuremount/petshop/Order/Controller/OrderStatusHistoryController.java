package com.treasuremount.petshop.Order.Controller;
import com.treasuremount.petshop.Order.Entity.OrderStatusHistory;
import com.treasuremount.petshop.Order.Service.OrderStatusHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/orderStatusHistory")
public class OrderStatusHistoryController {

    @Autowired
    private OrderStatusHistoryService service;


    @PostMapping("/add")
    public ResponseEntity<OrderStatusHistory> createUser(@RequestBody @Valid OrderStatusHistory user) {
        OrderStatusHistory createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<OrderStatusHistory>> getAllUsers() {
        List<OrderStatusHistory> users = service.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<OrderStatusHistory> getOneUser(@PathVariable("id") Long id) {
        OrderStatusHistory user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderStatusHistory> updateUser(@RequestBody @Valid OrderStatusHistory user, @PathVariable("id") Long id) {
        OrderStatusHistory updatedUser = service.update(user, id);
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
