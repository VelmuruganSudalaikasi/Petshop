package com.treasuremount.petshop.Repository;





import com.treasuremount.petshop.Entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ProductStatusRepo extends JpaRepository<ProductStatus,Long> {

}
