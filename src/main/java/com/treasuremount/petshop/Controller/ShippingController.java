package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.Entity.Blog;
import com.treasuremount.petshop.Entity.ShippingAddress;
import com.treasuremount.petshop.Service.ShippingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/public/shippingAddress")
public class ShippingController {

    @Autowired
    private ShippingService service;


    @PostMapping("/add")
    public ResponseEntity<ShippingAddress> createUser(@RequestBody @Valid ShippingAddress user) {
        ShippingAddress createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<ShippingAddress>> getAllUsers() {
        List<ShippingAddress> users = service.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<ShippingAddress>> getAddByUserId(@PathVariable("userId") Long userId){
        List<ShippingAddress> lst=service.getAddressesByUserId(userId);
        return ResponseEntity.ok(lst);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ShippingAddress> getOneUser(@PathVariable("id") Long id) {
        ShippingAddress user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShippingAddress> updateUser(@RequestBody @Valid ShippingAddress user, @PathVariable("id") Long id) {
        ShippingAddress updatedUser = service.update(user, id);
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
