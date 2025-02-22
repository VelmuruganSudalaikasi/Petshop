package com.treasuremount.petshop.Repository;


import com.treasuremount.petshop.Entity.Foods;
import com.treasuremount.petshop.FoodResource.FoodResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FoodRepo extends JpaRepository<Foods, Long> {


    @Query(value = "SELECT * FROM foods f WHERE f.product_id = :productId", nativeQuery = true)
    Foods findByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT f.stock_quantity FROM foods f WHERE f.product_id = :productId", nativeQuery = true)
    Integer stockAvailability(@Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE foods f SET f.stock_quantity = :stockQuantity WHERE f.product_id = :productId", nativeQuery = true)
    void updateStockQuantity(@Param("productId") Long productId, @Param("stockQuantity") Integer stockQuantity);


    @Query("""
        SELECT new com.treasuremount.petshop.FoodResource.FoodResponseDTO (
            p.id, 
            p.CategoryId, 
            p.UserId, 
            p.name, 
            p.imageUrl, 
            p.price, 
            p.stockQuantity, 
            p.activeStatus, 
            p.returnWithin, 
            p.createdDate, 
            p.updatedDate, 
            p.SubCategoryId, 
            p.ProductStatusId, 
            p.discount,
            p.minStockLevel,
            p.videoUrl,
            p.barCode,
            f.brand,
            f.weightUnit,
            f.weight,
            f.expiryDate,
            fd.ingredients,
            fd.nutritionalInfo,
            fd.storageInstructions,
            fd.feedingGuidelines )
            FROM Foods f
            JOIN FoodDetails fd ON f.id = fd.FoodsId
            LEFT JOIN  Product p ON p.id=f.ProductId
            """)
    List<FoodResponseDTO> getAllWithJpa();


}