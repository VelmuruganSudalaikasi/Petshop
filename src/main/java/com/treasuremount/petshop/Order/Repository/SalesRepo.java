package com.treasuremount.petshop.Order.Repository;

import com.treasuremount.petshop.Order.Entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepo extends JpaRepository<Sales,Long> {
}
