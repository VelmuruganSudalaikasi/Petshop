/*
package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.Entity.PetDetails;

import com.treasuremount.petshop.Service.PetDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/petDetail")
public class PetDetailController {

    @Autowired
    private PetDetailsService service;


    @PostMapping("/add")
    public ResponseEntity<PetDetails> createUser(@RequestBody @Valid PetDetails user) {
        PetDetails createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<PetDetails>> getAllUsers() {
        List<PetDetails> users = service.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<PetDetails> getOneUser(@PathVariable("id") Long id) {
        PetDetails user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetDetails> updateUser(@RequestBody @Valid PetDetails user, @PathVariable("id") Long id) {
        PetDetails updatedUser = service.update(user, id);
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
