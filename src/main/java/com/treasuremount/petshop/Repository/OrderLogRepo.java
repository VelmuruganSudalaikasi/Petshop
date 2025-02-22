package com.treasuremount.petshop.Repository;

import com.treasuremount.petshop.Order.Entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLogRepo extends JpaRepository<OrderLog,Long> {

}
