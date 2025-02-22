package com.treasuremount.petshop.Order.Repository;

import com.treasuremount.petshop.Order.Entity.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusHistoryRepo extends JpaRepository<OrderStatusHistory,Long> {
    @Query(value = "SELECT DISTINCT h.orderId, i.productId, h.newStatus, h.createdAt " +
            "FROM OrderStatusHistory h " +
            "JOIN OrderItems i ON h.orderId = i.orderId " +
            "WHERE i.productId = 54 " +
            "AND h.newStatus = :newStatus",nativeQuery = true)
    List<Object[]> findDistinctOrderStatusHistoryWithProductIdAndStatus(@Param("newStatus") String newStatus);

    @Query(value = "SELECT * FROM order_status_history os WHERE os.order_id = :orderId",nativeQuery = true)
    OrderStatusHistory findByOrderId(@Param("orderId") Long orderId);
}

