package com.treasuremount.petshop.Repository;


import com.treasuremount.petshop.AccessoriesResource.AccessoriesResponseDTO;
import com.treasuremount.petshop.Entity.Accessories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccessoriesRepo extends JpaRepository<Accessories,Long> {

    @Query(value = "SELECT * FROM accessories a WHERE a.product_id = :productId", nativeQuery = true)
    Accessories findByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT f.stock_quantity FROM accessories f WHERE f.product_id = :productId", nativeQuery = true)
    Integer stockAvailability(@Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE accessories f SET f.stock_quantity = :stockQuantity WHERE f.product_id = :productId", nativeQuery = true)
    void updateStockQuantity(@Param("productId") Long productId, @Param("stockQuantity") Integer stockQuantity);


    @Query("""
        SELECT new com.treasuremount.petshop.AccessoriesResource.AccessoriesResponseDTO(
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
            a.brand,
            a.size, 
            a.color,
            ad.specifications, 
            ad.usageInstructions, 
            ad.careInstructions
        )
        FROM Accessories a
        LEFT JOIN AccessoryDetails ad ON a.id = ad.AccessoriesId
        LEFT JOIN  Product p ON p.id=a.ProductId
        """)
    List<AccessoriesResponseDTO> getAllWithJpa();


}

