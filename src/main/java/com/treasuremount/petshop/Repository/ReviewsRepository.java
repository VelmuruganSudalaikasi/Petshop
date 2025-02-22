package com.treasuremount.petshop.Repository;

import com.treasuremount.petshop.DTO.ReviewsDTO;
import com.treasuremount.petshop.Entity.Reviews;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

    @Query("SELECT r FROM Reviews r WHERE (:productId IS NULL OR :productId = 0 OR r.productId = :productId)")
    List<Reviews> findByProductId(@Param("productId") Long productId);



    @Query(value = """
            SELECT new  com.treasuremount.petshop.DTO.ReviewsDTO(
            r.id,
            u.firstName,
            uu.firstName,
            p.name,
            pf.imageUrl,
            r.rating,
            r.comments,
            r.isApproved,
            r.createdDate
            )
            FROM Reviews r
            JOIN User u ON u.id=r.userId
            LEFT JOIN ProfileImage pf ON  pf.userId=u.id
            JOIN Product p ON p.id = r.productId
            LEFT JOIN Vendor v ON v.userId = p.UserId
            LEFT JOIN User uu ON uu.id=v.userId
            WHERE
                (
                :isApproved IS NULL OR
                 (
                 (:isApproved = false AND r.isApproved = false)
                  OR
                 (:isApproved = true AND r.isApproved = true)
                 )
           
                 )
            AND 
                (:userId = 0 OR (
                 (:isUser = true AND r.userId = :userId) 
                 OR
                 (:isUser = false AND v.userId = :userId)
                 
                 )
                 
                 )
            AND 
                (:productId = 0 OR r.productId = :productId)
            """)
    List<ReviewsDTO> findByAll(
            @Param("userId") Long userId,
            @Param("isUser") Boolean isUser,
            @Param("productId") Long productId,
            @Param("isApproved") Boolean isApproved
    );


    @Modifying
    @Transactional
    @Query("UPDATE Reviews r SET r.isApproved = :isApproved WHERE r.id = :reviewId")
    int updateReviewApprovalStatus(Long reviewId, Boolean isApproved);

}
