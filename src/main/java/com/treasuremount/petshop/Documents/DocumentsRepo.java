package com.treasuremount.petshop.Documents;


import com.treasuremount.petshop.ProductImage.Position.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepo extends JpaRepository<Documents,Long> {

}