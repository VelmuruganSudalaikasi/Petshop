package com.treasuremount.petshop.Repository;



import com.treasuremount.petshop.DTO.SubCategoryInfoDTO;
import com.treasuremount.petshop.DTO.SubCategoryInfoImageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.treasuremount.petshop.Entity.SubCategory;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query("SELECT new com.treasuremount.petshop.DTO.SubCategoryInfoImageDTO(s.id, s.name, s.categoryId, c.name, s.activeStatus,s.imageUrl) " +
            "FROM SubCategory s " +
            "JOIN Category c ON s.categoryId = c.id " +
            "WHERE (:categoryId IS NULL OR 0=:categoryId)" +
            " OR (s.categoryId=:categoryId)")
    List<SubCategoryInfoImageDTO> getAllSubCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT new com.treasuremount.petshop.DTO.SubCategoryInfoImageDTO(s.id, s.name, s.categoryId, c.name, s.activeStatus,s.imageUrl) " +
            "FROM SubCategory s " +
            "JOIN Category c ON s.categoryId = c.id")
    List<SubCategoryInfoImageDTO> findAllWithCategoryName();


}