package com.treasuremount.petshop.Repository;

import com.treasuremount.petshop.Entity.AccessoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoriesDetailsRepo extends JpaRepository<AccessoryDetails,Long> {
    @Query(value = "SELECT * from accessory_details AS a where a.accessories_id = :accId ",nativeQuery = true)
    AccessoryDetails findByAccessoryId(@Param("accId") Long id);
}
