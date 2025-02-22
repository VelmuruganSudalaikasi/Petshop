package com.treasuremount.petshop.Payment.VendorPayment;
import com.treasuremount.petshop.Payment.CustomerPayment.VendorPaymentDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VendorPaymentRepo extends JpaRepository<VendorPayment,Long> {
/*
    @Query("SELECT new com.treasuremount.petshop.Payment.CustomerPayment.VendorPaymentDTO(" +
            "v.id, u.firstName, v.shopName, o.id, o.orderDate, o.totalAmount, os.name, " +
            "vp.paymentReference, " +
            "CASE WHEN o.vendorPayment IS NULL THEN false ELSE true END) " +
            "FROM Vendor v " +
            "JOIN Product p ON p.UserId = v.userId " +
            "JOIN Orders o ON o.ProductId = p.id " +
            "JOIN OrderStatusS os ON o.OrderStatusId = os.id " +
            "JOIN User u ON u.id = v.userId " +
            "LEFT JOIN VendorPayment vp ON vp.id = o.vendorPayment.id " + // Include payment reference
            "WHERE o.vendorPayment IS NULL " +  // Only unpaid orders
            "AND o.OrderStatusId >= :orderStatusId " +
            "ORDER BY v.id, o.orderDate")
    List<VendorPaymentDTO> findUnpaidVendorOrders(@Param("orderStatusId") Long orderStatusId);
*/


    //order id 7
    @Query("SELECT new com.treasuremount.petshop.Payment.VendorPayment.OrderDetailDTO(" +
            " o.id,p.id, p.name, o.quantity, o.totalAmount) " +
            "FROM Orders o " +
            "JOIN Product p ON o.ProductId = p.id " +
            "JOIN Vendor v ON p.UserId = v.userId " +
            "WHERE v.id = :vendorId AND (o.isCancelled = true OR o.isReturned = true) AND" +
            " ( isVendorDeductionIsDone= false OR isVendorDeductionIsDone IS NULL)  ")
    List<OrderDetailDTO> findCancelOrReturnOrdersByVendorUserId(@Param("vendorId") Long vendorId);




    //not paid items for vendor

   //order Id is 2 means we will give
    @Query("SELECT new com.treasuremount.petshop.Payment.VendorPayment.OrderDetailDTO(" +
            "o.id,p.id, p.name, o.quantity, o.totalAmount) " +  // Map fields to DTO constructor
            "FROM Orders o " +
            "JOIN Product p ON p.id = o.ProductId " +  // Join Product with Orders on ProductId
            "JOIN Vendor v ON v.userId = p.UserId " +  // Join Vendor with Product on UserId
            "WHERE v.id = :vendorId " +  // Filter by vendorId
            "AND o.vendorPayment IS  NULL " +
            "AND o.OrderStatusId = :orderStatusId ")  // Ensure VendorPayment is NULL
    List<OrderDetailDTO> findAllByVendorIdWithNoVendorPayment(@Param("vendorId") Long vendorId,@Param("orderStatusId") Long orderStatusId);



    // while payment




    @Query("SELECT new com.treasuremount.petshop.Payment.CustomerPayment.VendorPaymentDTO(" +
            "v.id, u.firstName, v.shopName, o.id, o.orderDate, o.totalAmount, os.name, " +
            "vp.paymentReference, vp.paymentDate, " +  // Include payment reference
            "(CASE WHEN o.refundPayment IS NULL THEN false ELSE true END)) "+
            "FROM Vendor v " +
            "JOIN Product p ON p.UserId = v.userId " +
            "JOIN Orders o ON o.ProductId = p.id " +
            "JOIN OrderStatusS os ON o.OrderStatusId = os.id " +
            "JOIN User u ON u.id = v.userId " +
            "LEFT JOIN VendorPayment vp ON vp.id = o.vendorPayment.id " +  // Join VendorPayment to fetch payment reference
            "WHERE "+
            "(:isPaid IS NULL OR (o.vendorPayment IS NULL AND :isPaid = false) " +
            "OR (o.vendorPayment IS NOT NULL AND :isPaid = true)) " +
            "ORDER BY v.id, o.orderDate")
    List<VendorPaymentDTO> allVendorStatus(@Param("isPaid") Boolean isPaid);

    //after payment





    // Update query to mark orders as paid
    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.vendorPayment = :payment WHERE o.id IN :orderIds " +
            "AND o.vendorPayment IS NULL " )
    int updateOrdersWithPayment(@Param("orderIds") List<Long> orderIds,
                                @Param("payment") VendorPayment payment);



    @Query("SELECT " +
            "vp.id, " +
            "GROUP_CONCAT(o.uniqueOrderId), " + // Concatenate the uniqueOrderId values
            "vp.totalAmount, " +
            "vp.paymentDate, " +
            "vp.paymentReference, " +
            "vp.paymentMode, " +
            "vp.remarks, " +
            "vp.vendorConfirmation " +
            "FROM VendorPayment vp " +
            "JOIN Orders o ON o.vendorPayment.id = vp.id " +
            "WHERE o.product.UserId = :vendorId " +
            "AND vp.vendorConfirmation = :confirmed " +
            "GROUP BY vp.id " +
            "ORDER BY vp.id")
    List<Object[]> getVendorPaymentsWithUniqueOrderIds(
            @Param("vendorId") Long vendorId,
            @Param("confirmed") Boolean confirmed);




    @Modifying
    @Transactional
    @Query("UPDATE VendorPayment vp " +
            "SET vp.vendorConfirmation = true " +
            "WHERE vp.id IN :vendorPaymentIds")
    void confirmVendorPaymentsByIds(@Param("vendorPaymentIds") List<Long> vendorPaymentIds);



    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.isVendorDeductionIsDone = true WHERE o.id IN :orderIds")
    void updateVendorDeductionStatus(@Param("orderIds") List<Long> orderIds);


}
