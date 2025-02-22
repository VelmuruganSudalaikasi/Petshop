package com.treasuremount.petshop.ShopImage.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopImagePositionRepo extends JpaRepository<ShopImagePosition,Long> {

}