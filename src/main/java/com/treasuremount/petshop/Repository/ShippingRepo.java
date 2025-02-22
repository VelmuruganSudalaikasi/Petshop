package com.treasuremount.petshop.Repository;
import com.treasuremount.petshop.Entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRepo extends JpaRepository<ShippingAddress,Long> {

    List<ShippingAddress> findAllByUserId(Long userId);
}




