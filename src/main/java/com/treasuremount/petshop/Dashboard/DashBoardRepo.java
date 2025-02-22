package com.treasuremount.petshop.Dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface DashBoardRepo extends JpaRepository<DashboardEntity,Long> {


    @Query(value = """
            SELECT 
                DATE(ord.order_date) AS order_date,
                st.name AS status_name,
                COALESCE(SUM(ord.total_amount), 0) AS total_amount,
                COALESCE(COUNT(ord.id),0) AS product_count
            FROM 
                orders ord
            LEFT JOIN 
                order_status st
            ON 
                ord.order_status_id = st.id
            LEFT JOIN 
                product pd
            ON 
                pd.id = ord.product_id
            LEFT JOIN 
                vendor v
            ON 
                v.user_id = pd.user_id
            WHERE 
                (ord.order_date BETWEEN :startDate AND :endDate OR :startDate IS NULL OR :endDate IS NULL)
                AND (:vendorUserId IS NULL OR :vendorUserId = 0 OR v.user_id = :vendorUserId)
            GROUP BY 
                DATE(ord.order_date), st.name
            ORDER BY 
                order_date ASC, st.name ASC
            """, nativeQuery = true)
    List<Object[]> findOrderStatisticsByDateRange(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("vendorUserId") Long vendorUserId
    );

    @Query(value = """
        SELECT 
            DATE(ord.order_date) AS order_date,
            st.name AS status_name,
            COALESCE(SUM(ord.total_amount), 0) AS total_amount,
            COALESCE(COUNT(ord.id), 0) AS product_count,
            COALESCE(SUM(CASE WHEN st.name = 'Pending' THEN ord.total_amount ELSE 0 END), 0) AS pending_total,
            COALESCE(SUM(CASE WHEN st.name = 'Confirmed' THEN ord.total_amount ELSE 0 END), 0) AS confirmed_total,
            COALESCE(SUM(CASE WHEN st.name = 'Return' THEN ord.total_amount ELSE 0 END), 0) AS return_total,
            COALESCE(SUM(CASE WHEN st.name = 'Refund' THEN ord.total_amount ELSE 0 END), 0) AS refund_total,
            COALESCE(COUNT(CASE WHEN st.name = 'Pending' THEN 1 END), 0) AS pending_count,
            COALESCE(COUNT(CASE WHEN st.name = 'Confirmed' THEN 1 END), 0) AS confirmed_count,
            COALESCE(COUNT(CASE WHEN st.name = 'Return' THEN 1 END), 0) AS return_count,
            COALESCE(COUNT(CASE WHEN st.name = 'Refund' THEN 1 END), 0) AS refund_count
        FROM 
            orders ord
        LEFT JOIN 
            order_status st ON ord.order_status_id = st.id
        LEFT JOIN 
            product pd ON pd.id = ord.product_id
        LEFT JOIN 
            vendor v ON v.user_id = pd.user_id
        WHERE 
            (ord.order_date BETWEEN :startDate AND :endDate OR :startDate IS NULL OR :endDate IS NULL)
            AND (:vendorUserId IS NULL OR :vendorUserId = 0 OR v.user_id = :vendorUserId)
        GROUP BY 
            DATE(ord.order_date), st.name
        ORDER BY 
            order_date ASC, st.name ASC
    """, nativeQuery = true)
    List<Object[]> findOrderStatusByDateRange(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("vendorUserId") Long vendorUserId
    );


    @Query(value = """
                SELECT 
                    0 AS totalUsers,
                    COUNT(DISTINCT o.id) AS totalOrders,
                    CASE WHEN :vendorUserid = 0 THEN COUNT(DISTINCT v.user_id) ELSE -1 END AS totalVendors,
                    COUNT(DISTINCT r.id) AS totalReviews,
                    COUNT(DISTINCT p.id) productCount
                FROM 
                    product p
               LEFT JOIN 
                    orders o ON o.product_id = p.id
               LEFT JOIN 
                    vendor v ON p.user_id = v.user_id
                LEFT JOIN 
                    reviews r ON r.product_id = p.id
                WHERE 
                    :vendorUserid = 0 OR v.user_id = :vendorUserid
            """, nativeQuery = true)
    Map<String, Object> getProductVendorReviewData(@Param("vendorUserid") Long vendorUserid);

    @Query("SELECT COUNT(u) FROM User u")
    Long getTotalUsers(@Param("vendorUserid") Long vendorUserid);
    @Query("SELECT COUNT(v) FROM Vendor v ")
    Long getTotalVendor(@Param("vendorUserid") Long vendorUserid);

    @Query(
            """
             SELECT new com.treasuremount.petshop.Dashboard.VendorSalesDTO(
             u.firstName,
             v.shopName,
             pf.imageUrl,
             u.emailId,
             COUNT(r.id)
             )
             FROM   
              Product p
              JOIN Vendor v ON p.UserId=v.userId 
              JOIN Orders r ON r.ProductId = p.id
              JOIN User u   ON  u.id=v.userId
              LEFT JOIN ProfileImage pf ON  u.id=pf.userId   
              WHERE r.OrderStatusId = :OrderStatusId
              GROUP BY 
               v.id,pf.imageUrl
              Order BY 
               COUNT(r.id) DESC 
              LIMIT :limit 
            """
    )


    List<VendorSalesDTO> getTopVendorBySales(@Param("limit")Long Limit,
                                             @Param("OrderStatusId") Long OrderStatusId
    );

    @Query(value = """
             SELECT
                 COUNT(ap.id) AS AppointmentCount,
                 ap.status,
                 ap.appointment_requested_date
             FROM appointment ap
             JOIN veterinarian v ON ap.veterinarian_id = v.id
             WHERE
                 (:startDate IS NULL OR ap.appointment_requested_date >= :startDate)
                 AND (:endDate IS NULL OR ap.appointment_requested_date <= :endDate)
                 AND (:doctorId = 0 OR v.id = :doctorId)
             GROUP BY ap.status, ap.appointment_requested_date
             ORDER BY DATE(ap.appointment_requested_date) ASC, ap.status ASC
            """, nativeQuery = true)
    List<Object[]> findAppointmentStatisticsByDateRange(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("doctorId") Long doctorId
    );


}


