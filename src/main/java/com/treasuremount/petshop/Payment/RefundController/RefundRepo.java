package com.treasuremount.petshop.Payment.RefundController;
import com.treasuremount.petshop.Payment.CustomerPayment.VendorPaymentDTO;
import com.treasuremount.petshop.Payment.VendorPayment.OrderDetailDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepo extends JpaRepository<RefundPayment,Long> {


    @Query("SELECT new com.treasuremount.petshop.Payment.VendorPayment.OrderDetailDTO(" +
            " o.id,p.id, p.name, o.quantity, o.totalAmount) " +
            "FROM Orders o " +
            "JOIN Product p ON o.ProductId = p.id " +
            "JOIN Vendor v ON p.UserId = v.userId " +
            "WHERE v.id = :vendorId  AND (o.OrderStatusId = 7 OR o.OrderStatusId=8) AND o.refundPayment IS NULL ")
    List<OrderDetailDTO> payToCustomer(@Param("vendorId") Long vendorId);


    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.refundPayment = :payment WHERE o.id IN :orderIds " +
            "AND o.refundPayment IS NULL " )
    int updateOrderWithRefund(@Param("orderIds") List<Long> orderIds,
                                @Param("payment") RefundPayment payment);


    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.OrderStatusId = :OrderStatusId WHERE o.id IN :orderIds ")
    int updateOrderStatusIdToRefund(@Param("orderIds") List<Long> orderIds,
                              @Param("OrderStatusId") Long  OrderStatusId);


    @Query("SELECT new com.treasuremount.petshop.Payment.CustomerPayment.VendorPaymentDTO(" +
            "v.id, u.firstName, v.shopName, o.id, o.orderDate, o.totalAmount, os.name, " +
            "vp.paymentReference, vp.paymentDate, " +  // Include payment reference
            "(CASE WHEN o.refundPayment IS NULL THEN false ELSE true END)) " +
            "FROM Vendor v " +
            "JOIN Product p ON p.UserId = v.userId " +
            "JOIN Orders o ON o.ProductId = p.id " +
            "JOIN OrderStatusS os ON o.OrderStatusId = os.id " +
            "JOIN User u ON u.id = v.userId " +
            "LEFT JOIN RefundPayment vp ON vp.id = o.refundPayment.id " +  // Join VendorPayment to fetch payment reference
            "WHERE "+
            "(:isPaid IS NULL OR (o.refundPayment IS NULL AND :isPaid = false) " +
            "OR (o.refundPayment IS NOT NULL AND :isPaid = true)) " +
            "ORDER BY v.id, o.orderDate")
    List<VendorPaymentDTO> allVendorStatus(@Param("isPaid") Boolean isPaid);

}
