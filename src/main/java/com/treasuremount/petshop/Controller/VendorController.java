package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.DTO.VendorWithUserName;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.Service.VendorService;
import com.treasuremount.petshop.Service.VendorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/public/vendor")
public class VendorController {

    @Autowired
    private VendorServiceImpl service;


    @PostMapping(value="/add")
    public ResponseEntity<Vendor> createUser(@RequestBody @Valid Vendor user) {

        Vendor createdUser = service.create(user);
        if(createdUser !=null)
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        else{
            return ResponseEntity.notFound().build();
        }
    }
/*

    @PatchMapping(value = "/edit")
    public ResponseEntity<?>  editVendor(@RequestBody Vendor vendor){
           boolean flag=service.editVendor(vendor);
           return (flag)  ?  ResponseEntity.ok().build() : ResponseEntity.notFound().build();

    }
*/


    @GetMapping("/getAll")
    public ResponseEntity<List<VendorWithUserName>> getAllUsers() {
        List<VendorWithUserName> users = service.getAllWithUserName();
        if(users != null)
         return ResponseEntity.ok(users);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Vendor> getOneUser(@PathVariable("id") Long id) {
        Vendor user = service.getOneUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vendor> updateUser(@RequestBody @Valid Vendor user, @PathVariable("id") Long id) {
        Vendor updatedUser = service.update(user, id);
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
