package com.treasuremount.petshop.Repository;


import com.treasuremount.petshop.Entity.FoodDetails;
import com.treasuremount.petshop.Entity.Foods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodDetailsRepo extends JpaRepository<FoodDetails,Long> {


    @Query(value = "SELECT * FROM food_details WHERE foods_id = :foodId", nativeQuery = true)
    FoodDetails findByFoodId(@Param("foodId") Long foodId);

}
