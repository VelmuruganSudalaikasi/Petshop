package com.treasuremount.petshop.Repository;


import com.treasuremount.petshop.Entity.PetDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetDetailsRepo extends JpaRepository<PetDetails,Long> {
    @Query(value = "SELECT * FROM pet_details pt WHERE pt.pet_id = :petsId", nativeQuery = true)
    PetDetails findByFoodId(@Param("petsId") Long petsId);
}
