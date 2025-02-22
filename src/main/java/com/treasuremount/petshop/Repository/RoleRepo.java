package com.treasuremount.petshop.Repository;

import com.treasuremount.petshop.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
}
