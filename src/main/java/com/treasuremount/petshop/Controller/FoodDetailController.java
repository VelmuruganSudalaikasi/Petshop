/*
package com.treasuremount.petshop.Controller;
import com.treasuremount.petshop.Entity.FoodDetails;
import com.treasuremount.petshop.Service.FoodDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/foodDetail")
public class FoodDetailController {

    @Autowired
    private FoodDetailsService service;


    @PostMapping("/add")
    public ResponseEntity<FoodDetails> createUser(@RequestBody @Valid FoodDetails user) {
        FoodDetails createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<FoodDetails>> getAllUsers() {
        List<FoodDetails> users = service.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<FoodDetails> getOneUser(@PathVariable("id") Long id) {
        FoodDetails user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FoodDetails> updateUser(@RequestBody @Valid FoodDetails user, @PathVariable("id") Long id) {
        FoodDetails updatedUser = service.update(user, id);
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
