package com.treasuremount.petshop.Repository;

import com.treasuremount.petshop.Entity.Blog;
import com.treasuremount.petshop.Entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface StateRepo extends JpaRepository<State,Long> {
}
