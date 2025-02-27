package com.treasuremount.petshop.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.treasuremount.petshop.DTO.UserDTO;
import com.treasuremount.petshop.DTO.UserDetailsDTO;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.ExceptionHandler.DuplicateUserException;
import com.treasuremount.petshop.Login.LoginResponse;
import com.treasuremount.petshop.Service.UserService;
import com.treasuremount.petshop.Service.UserServiceImpl;
import com.treasuremount.petshop.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/public/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private Mapper entityDtoMapper;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDto) {


        try{
            User user = entityDtoMapper.toEntity(userDto);
            User createdUser = service.create(user);
            System.out.println(createdUser);
            LoginResponse response=entityDtoMapper.toLoginDTO(createdUser) ;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateUserException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }


    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = service.getAll();
        List<UserDTO> responseDTOs = users.stream()
                .map(entityDtoMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }


  /*  @PatchMapping(value = "/edit")
    public ResponseEntity<?>  editUser(@RequestBody User user){
        boolean flag=service.editUser(user);
        return (flag)  ?  ResponseEntity.ok().build() : ResponseEntity.notFound().build();

    }*/

    @GetMapping("/getOne/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = service.getOneUserById(id);
        if (user != null) {
            UserDTO responseDTO = entityDtoMapper.toDTO(user);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDto, @PathVariable Long id) {
        User user = entityDtoMapper.toEntity(userDto);
        User updatedUser = service.update(user, id);
        if (updatedUser != null) {
            UserDTO responseDTO = entityDtoMapper.toDTO(updatedUser);
            return ResponseEntity.ok().body(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/getOne/userDetails/{id}")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable Long id) {
        User user = service.getOneUserById(id);
        if (user != null) {
            UserDetailsDTO responseDTO = entityDtoMapper.userDetailsToDTO(user);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}