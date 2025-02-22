package com.treasuremount.petshop.ProductImage;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductImages p WHERE p.productId = :productId")
    void deleteByProductId(@Param("productId") Long productId);

    @Query("SELECT p FROM ProductImages p WHERE p.productId = :productId")
    List<ProductImages> findByProductId(@Param("productId") Long productId);

/*    @Query("SELECT p FROM ProductImages p WHERE p.productId = :productId AND p.position = :position")
    List<ProductImages> findByProductIdAndPosition(@Param("productId") Long productId, @Param("position") String position);*/

    @Query("SELECT pi FROM ProductImages pi WHERE pi.productId = :productId AND pi.positionId = :positionId")
    List<ProductImages> findByProductIdAndPositionId(@Param("productId") Long productId, @Param("positionId") Long positionId);

    @Query("SELECT pi FROM ProductImages pi WHERE pi.productId IN (:Ids) AND ( 0 =: positionId ) OR pi.positionId = :positionId")
    List<ProductImages> findByProductIdLst(@Param("Ids") List<Long> productIds,@Param("positionId") Long positionId);

}