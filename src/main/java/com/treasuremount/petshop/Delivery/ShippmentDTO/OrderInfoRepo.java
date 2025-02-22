package com.treasuremount.petshop.Delivery.ShippmentDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInfoRepo extends JpaRepository<OrderInformation,Long> {
}
