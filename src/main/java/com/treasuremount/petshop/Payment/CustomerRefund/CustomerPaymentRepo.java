package com.treasuremount.petshop.Payment.CustomerRefund;
import com.treasuremount.petshop.Payment.VendorPayment.OrderDetailDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CustomerPaymentRepo extends JpaRepository<CustomerPayment,Long> {

    @Query("SELECT new com.treasuremount.petshop.Payment.CustomerRefund.CustomerPaymentDTO(" +
            "v.id, u.firstName, o.id, o.orderDate, o.totalAmount, os.name, " +
            "CASE WHEN o.customerPayment IS NULL THEN false ELSE true END, " +
            "cp.paymentReference) " +  // Include payment reference
            "FROM Vendor v " +
            "JOIN Product p ON p.UserId = v.userId " +  // Ensure correct column name for userId
            "JOIN Orders o ON o.ProductId = p.id " +  // Correct column name for productId
            "JOIN OrderStatusS os ON o.OrderStatusId = os.id " +  // Correct column for orderStatusId
            "JOIN User u ON u.id = v.userId " +  // Ensure correct reference for User
            "LEFT JOIN CustomerPayment cp ON cp.id = o.customerPayment.id " +  // Left join for customerPayment
            "WHERE o.vendorPayment IS NULL " +  // Check for unpaid orders
            "AND o.OrderStatusId = :orderStatusId " +  // Filter by orderStatusId
            "AND o.isReturned = true " +  // Check for returned orders
            "ORDER BY v.id, o.orderDate")  // Order by vendor id and order date
    List<CustomerPaymentDTO> findUnpaidVendorOrders(@Param("orderStatusId") Long orderStatusId);




    @Query("SELECT new com.treasuremount.petshop.Payment.CustomerRefund.CustomerPaymentDTO(" +
            "v.id, u.firstName, o.id, o.orderDate, o.totalAmount, os.name, " +
            "CASE WHEN o.customerPayment IS NULL THEN false ELSE true END, " +
            "cp.paymentReference) " +  // Include payment reference
            "FROM Vendor v " +
            "JOIN Product p ON p.UserId = v.userId " +
            "JOIN Orders o ON o.ProductId = p.id " +
            "JOIN OrderStatusS os ON o.OrderStatusId = os.id " +
            "JOIN User u ON u.id = v.userId " +
            "LEFT JOIN CustomerPayment cp ON cp.id = o.customerPayment.id " +  // Left join for payment reference
            "WHERE "+
            "o.isReturned = true " +
            "AND (:isPaid IS NULL OR (o.customerPayment IS NULL AND :isPaid = false) " +
            "OR (o.customerPayment IS NOT NULL AND :isPaid = true)) " +
            "ORDER BY v.id, o.orderDate")
    List<CustomerPaymentDTO> allVendorStatus(@Param("isPaid") Boolean isPaid);

    @Query("SELECT new com.treasuremount.petshop.Payment.VendorPayment.OrderDetailDTO(" +
            "o.id, p.id, p.name, o.quantity, o.totalAmount) " +
            "FROM Orders o " +
            "JOIN Product p ON p.id = o.ProductId " +  // Join Orders with Product using productId
            "JOIN User u ON u.id = o.userId " +  // Direct join between Orders and User using userId
            "WHERE u.id = :userId " +  // Filter by userId
            "AND o.isReturned = true " +  // Ensure orders are returned
            "AND o.customerPayment IS NULL " +  // Ensure no customer payment
            "AND o.OrderStatusId = :orderStatusId")  // Filter by orderStatusId
    List<OrderDetailDTO> findOrderDetailsByUserIdAndReturned(@Param("userId") Long userId, @Param("orderStatusId") Long orderStatusId);




    // Update query to mark orders as paid
    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.customerPayment = :payment WHERE o.id IN :orderIds " +
            "AND o.customerPayment IS NULL AND o.isReturned=true  " )
    int updateOrdersWithPayment(@Param("orderIds") List<Long> orderIds,
                                @Param("payment") CustomerPayment payment);





}
