package com.treasuremount.petshop.Repository;


import com.treasuremount.petshop.Entity.Foods;
import com.treasuremount.petshop.Entity.Pets;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.PetResource.PetResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PetRepo extends JpaRepository<Pets,Long> {
    @Query(value = "SELECT * FROM pets p WHERE p.product_id = :productId", nativeQuery = true)
    Pets findByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT * FROM pets p WHERE p.product_id = :productId", nativeQuery = true)
    Optional<Pets> findByProductIdOptional(@Param("productId") Long productId);

    @Query(value =
            "SELECT " +
                    "CASE " +
                    "   WHEN f.available_quantity IS NULL THEN 0 " +
                    "   ELSE f.available_quantity " +
                    "END as stock " +
                    "FROM pets f " +
                    "WHERE f.product_id = :productId", nativeQuery = true)
    Integer stockAvailability(@Param("productId") Long productId);



    @Modifying
    @Transactional
    @Query(value = "UPDATE pets  f SET f.available_quantity = :stockQuantity WHERE f.product_id = :productId", nativeQuery = true)
    void updateStockQuantity(@Param("productId") Long productId, @Param("stockQuantity") Integer stockQuantity);




        @Query("""
        SELECT new com.treasuremount.petshop.PetResource.PetResponseDTO(
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
            pet.breed, 
            pet.month,
            pet.year, 
            pet.gender, 
            pet.color, 
            pet.weight, 
            pet.isTransportAvailable,
            pet.isVeterinaryVerified,
            pet.veterinaryCertificationUrl,
            pet.isInsured,
            pet.insuredCertificationUrl,
            d.about, 
            d.healthInfo, 
            d.careInstructions, 
            d.specialRequirements
            
        )
        FROM Product p
        JOIN Pets pet ON p.id = pet.ProductId
        JOIN PetDetails d ON pet.id=d.PetId   
    """)
        List<PetResponseDTO> getAllWithJpa();









}
