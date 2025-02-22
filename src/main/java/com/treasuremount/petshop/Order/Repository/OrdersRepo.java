package com.treasuremount.petshop.Order.Repository;


import com.treasuremount.petshop.Order.DTO.OrderCustomDTO;
import com.treasuremount.petshop.Order.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Orders,Long> {


    //admin getAll
    @Query(value = """
    SELECT
        p.name AS productName,
        p.price AS productPrice,
        v.contact_details AS contactNumber,
        u.first_name AS vendorName,
        sc.id AS subCategoryId,
        sc.name AS subCategoryName,
        c.id AS categoryId,
        c.name AS categoryName,
        o.id AS orderId,
        o.user_id AS userId,
        o.product_id AS productId,
        o.quantity AS quantity,
        o.tax AS tax,
        o.shipping_charge AS shippingCharge,
        o.subtotal AS subtotal,
        o.order_status_id AS orderStatusId,
        o.total_amount AS totalAmount,
        o.order_date AS orderDate,
        o.delivery_date AS deliveryDate,
        o.is_cancelled AS isCancelled,
        o.is_returned AS isReturned
    FROM orders o
    JOIN product p ON o.product_id = p.id
    JOIN sub_category sc ON p.sub_category_id = sc.id
    JOIN category c ON sc.category_id = c.id
    JOIN vendor v ON p.user_id = v.user_id
    JOIN user u ON v.user_id = u.id
    """, nativeQuery = true)
    List<Object[]> getAllByAdminNative();


    @Query(value = """
    SELECT
        p.name AS productName,
        p.price AS productPrice,
        v.contact_details AS contactNumber,
        u.first_name AS vendorName,
        sc.id AS subCategoryId,
        sc.name AS subCategoryName,
        c.id AS categoryId,
        c.name AS categoryName,
        o.id AS orderId,
        o.user_id AS userId,
        o.product_id AS productId,
        o.quantity AS quantity,
        o.tax AS tax,
        o.shipping_charge AS shippingCharge,
        o.subtotal AS subtotal,
        o.order_status_id AS orderStatusId,
        o.total_amount AS totalAmount,
        o.order_date AS orderDate,
        o.delivery_date AS deliveryDate,
        o.is_cancelled AS isCancelled,
        o.is_returned AS isReturned
    FROM orders o
    LEFT JOIN product p ON o.product_id = p.id
    LEFT JOIN sub_category sc ON p.sub_category_id = sc.id
     LEFT JOIN category c ON sc.category_id = c.id
     LEFT JOIN vendor v ON p.user_id = v.user_id
    LEFT JOIN user u ON v.user_id = u.id
    WHERE 
      o.user_id = :userId
      
    """, nativeQuery = true)
    List<Object[]> findByUserIdCustom(@Param("userId") Long userId);

    List<Orders> findByUserId(Long userId);




    //don't pass the userId to get the details use the above findByUserIdCustom

    // This only applicable for get the orderDetail for the specific vendor by vendorUser Id
    @Query(value = """
    SELECT
        p.name AS productName,
        p.price AS productPrice,
        uu.mobile_number AS contactNumber,
        u.first_name AS vendorName,
        sc.id AS subCategoryId,
        sc.name AS subCategoryName,
        c.id AS categoryId,
        c.name AS categoryName,
        o.id AS orderId,
        o.user_id AS userId,
        o.product_id AS productId,
        o.quantity AS quantity,
        o.tax AS tax,
        o.shipping_charge AS shippingCharge,
        o.subtotal AS subtotal,
        o.order_status_id AS orderStatusId,
        o.total_amount AS totalAmount,
        o.order_date AS orderDate,
        o.delivery_date AS deliveryDate,
        o.is_cancelled AS isCancelled,
        o.is_returned AS isReturned,
        o.unique_order_id as uniqueId
    FROM orders o
    JOIN product p ON o.product_id = p.id
    JOIN sub_category sc ON p.sub_category_id = sc.id
    JOIN category c ON sc.category_id = c.id
    LEFT JOIN vendor v ON p.user_id = v.user_id
    JOIN user u ON v.user_id = u.id
    LEFT JOIN user uu ON uu.id = o.user_id
    WHERE 
        (
            (:userId = 0 OR 
                (
                    (:isUser = true AND o.user_id = :userId)
                    OR (:isUser = false AND p.user_id = :userId)
                )
            )
        )
        AND (
            (:OrderStatus = 0 OR o.order_status_id = :OrderStatus)
        )
        AND (
            (:StartDate IS NULL OR o.order_date >= :StartDate)
            AND (:EndDate IS NULL OR o.order_date <= :EndDate)
        )
    """, nativeQuery = true)
    List<Object[]> getAllByUserVendorv1(
            @Param("userId") Long userId,
            @Param("isUser") Boolean isUser,
            @Param("OrderStatus") Long orderStatus,
            @Param("StartDate") LocalDate startDate,
            @Param("EndDate") LocalDate endDate
    );



//000 false

    /* user create mail
     *  vendor ,verterinain
     *  order is placed
    * */

    /*super admin
    from order date to Date
    * */

    @Query(value = """
    SELECT
        p.name AS productName,
        p.price AS productPrice,
        uu.mobile_number AS contactNumber,
        u.first_name AS vendorName,
        sc.id AS subCategoryId,
        sc.name AS subCategoryName,
        c.id AS categoryId,
        c.name AS categoryName,
        o.id AS orderId,
        o.user_id AS userId,
        o.product_id AS productId,
        o.quantity AS quantity,
        o.tax AS tax,
        o.shipping_charge AS shippingCharge,
        o.subtotal AS subtotal,
        o.order_status_id AS orderStatusId,
        o.total_amount AS totalAmount,
        o.order_date AS orderDate,
        o.delivery_date AS deliveryDate,
        o.is_cancelled AS isCancelled,
        o.is_returned AS isReturned
    FROM orders o
     JOIN product p ON o.product_id = p.id
     JOIN sub_category sc ON p.sub_category_id = sc.id
     JOIN category c ON sc.category_id = c.id
     JOIN vendor v ON p.user_id = v.user_id
     JOIN user u ON v.user_id = u.id
     JOIN user uu ON uu.id=o.user_id
    WHERE 
       (   (:isUser = 1 AND o.user_id = :userId)
        OR  (:isUser = 0 AND p.user_id = :userId)
        )
 
    """, nativeQuery = true)
    List<Object[]> getAllByUserVendor(@Param("userId") Long userId, @Param("isUser") Boolean isUser);

    @Query("SELECT o FROM Orders o JOIN FETCH o.product p WHERE o.userId = :userId AND o.isReturned = false AND o.isCancelled = false")
    List<Orders> findEligibleOrdersByUserId(Long userId);

    boolean existsByUniqueOrderId(String uniqueOrderId);

/*

    @Query("SELECT o FROM Orders o " +
            "JOIN Product p ON o.ProductId = p.id " +
            "JOIN Vendor v ON p.UserId = v.userId " +
            "WHERE v.userId = :vendorUserId AND (o.isCancelled = true OR o.isReturned = true) AND" +
            " ( isVendorDeductionIsDone= false OR isVendorDeductionIsDone IS NULL)  ")
    List<Orders> findOrdersByVendorUserId(@Param("vendorUserId") Long vendorUserId);
*/



/*
    @Query(value = """
             SELECT r.*,u.first_name,v. FROM orders r
             LEFT JOIN user u on u.id=r.user_id;
             LEFT JOIN product p on p.id=r.product_id
             LEFT JOIN vendor v on p.user_id=v.user_id
              
            
            """)



    List<Object[]> getOneInOrder(@Param("orderId") Long orderId);*/


/*
*
*
*  @Query("""
        SELECT com.treasuremount.petshop.FoodResource.FoodResponseDTO (
            p.id,
            p.CategoryId,
            p.UserId,
            p.name,
            p.imageUrl,
            p.price,
            p.activeStatus,
            p.returnWithin,
            p.createdDate,
            p.updatedDate,
            p.SubCategoryId,
            p.ProductStatusId,
            f.brand,
            f.stockQuantity,
            f.weightUnit,
            f.weight,
            f.expiryDate,
            f.minStockLevel,
            fd.ingredients,
            fd.nutritionalInfo,
            fd.storageInstructions,
            fd.feedingGuidelines )
            FROM Foods f
            JOIN FoodDetails fd ON f.id = fd.FoodsId
            LEFT JOIN  Product p ON p.id=f.ProductId*/
}
