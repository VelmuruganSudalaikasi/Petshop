
package com.treasuremount.petshop.Controller;
import java.util.List;

import com.treasuremount.petshop.Entity.Role;
import com.treasuremount.petshop.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/public/role")
public class RoleController {

    @Autowired
    private RoleService service;

    @PostMapping("/add")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = service.create(role);
        if (createdRole != null) {
            return ResponseEntity.ok(createdRole);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Role>> getAllRole() {
        List<Role> role = service.getAll();
            return ResponseEntity.ok(role);

    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Integer id) {
        Role role = service.getOne(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Role> updateRole(@RequestBody Role role, @PathVariable Integer id) {
        Role updatedRole = service.update(role, id);
        if (updatedRole != null) {
            return ResponseEntity.ok(updatedRole);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

}
