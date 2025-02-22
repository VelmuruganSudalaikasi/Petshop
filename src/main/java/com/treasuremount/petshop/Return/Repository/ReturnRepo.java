package com.treasuremount.petshop.Return.Repository;





import com.treasuremount.petshop.Return.Entity.Cancel;
import com.treasuremount.petshop.Return.Entity.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnRepo extends JpaRepository<Return,Long> {

    @Query("""
    SELECT re FROM Return re 
    LEFT JOIN Orders o ON o.id = re.orderId
    LEFT JOIN Product p ON p.id = o.ProductId
    LEFT JOIN Vendor v ON v.userId = p.UserId
    WHERE v.userId = :vendorId
""")
    List<Return> returnListByVendorId(@Param("vendorId") Long userId);

    @Query("SELECT r FROM Return r " +
            "JOIN r.orders o " +
            "JOIN o.product p " +
            "WHERE p.UserId = :vendorUserId AND r.isApproved = false")
    List<Return> findNotApprovedRequestsByVendorUserId(@Param("vendorUserId") Long vendorUserId);

    @Query("""
            SELECT r FROM Return r WHERE r.userId = :userId AND r.isApproved = :isApproved
            """)

    List<Return> findAllByUserId(@Param("userId") Long userId, Boolean isApproved);
}
