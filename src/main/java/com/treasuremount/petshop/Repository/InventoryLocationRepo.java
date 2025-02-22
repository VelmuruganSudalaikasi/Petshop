package com.treasuremount.petshop.Repository;

import com.treasuremount.petshop.Entity.InventoryLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryLocationRepo extends JpaRepository<InventoryLocation, Long> {
}
