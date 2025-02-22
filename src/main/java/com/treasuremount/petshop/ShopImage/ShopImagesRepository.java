package com.treasuremount.petshop.ShopImage;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopImagesRepository extends JpaRepository<ShopImages, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ShopImages p WHERE p.vendorId = :vendorId")
    void deleteByProductId(@Param("vendorId") Long vendorId);

    @Query("SELECT p FROM ShopImages p WHERE p.vendorId = :vendorId")
    List<ShopImages> findByProductId(@Param("vendorId") Long vendorId);

/*    @Query("SELECT p FROM ShopImages p WHERE p.vendorId = :vendorId AND p.position = :position")
    List<ShopImages> findByProductIdAndPosition(@Param("vendorId") Long vendorId, @Param("position") String position);*/

    @Query("SELECT pi FROM ShopImages pi WHERE pi.vendorId = :vendorId AND pi.positionId = :positionId")
    List<ShopImages> findByProductIdAndPositionId(@Param("vendorId") Long vendorId, @Param("positionId") Long positionId);

    @Query("SELECT pi FROM ShopImages pi WHERE pi.vendorId IN (:Ids) AND ( 0 =: positionId ) OR pi.positionId = :positionId")
    List<ShopImages> findByProductIdLst(@Param("Ids") List<Long> productIds, @Param("positionId") Long positionId);

}

//Best selling, Top rating,