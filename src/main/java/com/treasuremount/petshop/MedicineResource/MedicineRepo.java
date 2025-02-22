package com.treasuremount.petshop.MedicineResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepo extends JpaRepository<Medicine,Long> {
    @Query("SELECT m FROM Medicine m WHERE m.product.id = :productId")
    Medicine findByProductId(@Param("productId") Long productId);


    @Query("""
        SELECT new com.treasuremount.petshop.MedicineResource.MedicineResourceDTO(
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
            m.petType, 
            m.brand, 
            m.dosageUnit, 
            m.dosage,
            m.expiryDate,
            md.activeIngredients, 
            md.usageInstructions, 
            md.storageInstructions, 
            md.warnings
        )
        FROM Medicine m
        LEFT JOIN MedicineDetails md ON m.id = md.medicineId
        LEFT JOIN Product p ON p.id = m.productId
        """)
    List<MedicineResourceDTO> getAllWithJpa();


}