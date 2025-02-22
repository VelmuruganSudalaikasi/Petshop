package com.treasuremount.petshop.Order.Controller;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.Service.OrdersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/public/orders")
public class OrdersController {

    @Autowired
    private OrdersService service;


    @PostMapping("/add")
    public ResponseEntity<Orders> createUser(@RequestBody @Valid Orders user) {
        Orders createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Orders>> getAllUsers() {
        List<Orders> users = service.getAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Orders> getOneUser(@PathVariable("id") Long id) {
        Orders user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Orders> updateUser(@RequestBody @Valid Orders user, @PathVariable("id") Long id) {
        Orders updatedUser = service.update(user, id);
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
