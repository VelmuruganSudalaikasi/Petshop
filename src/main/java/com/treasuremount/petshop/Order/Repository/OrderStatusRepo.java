package com.treasuremount.petshop.Order.Repository;
import com.treasuremount.petshop.Order.Entity.OrderStatusS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepo extends JpaRepository<OrderStatusS,Long> {
}
