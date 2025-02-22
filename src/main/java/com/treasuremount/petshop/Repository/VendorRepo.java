package com.treasuremount.petshop.Repository;

import com.treasuremount.petshop.DTO.VendorWithUserName;
import com.treasuremount.petshop.Entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepo extends JpaRepository<Vendor,Long> {
        @Query(value = "SELECT * FROM vendor where user_id= :userId",nativeQuery = true)
        Vendor findByUserId(@Param("userId") Long Id);


        @Query(value = "SELECT * FROM vendor where user_id= :userId",nativeQuery = true)
        Optional<Vendor> findByVendorUserId(@Param("userId") Long Id);


        @Query(value = "SELECT v.id AS vendorId, u.first_name AS firstName, v.shop_name AS shopName, v.contact_details AS contactDetails " +
                "FROM vendor v " +
                "JOIN user u ON v.user_id = u.id",
                nativeQuery = true)
        List<Object[]> findVendorInfoRaw();


        @Query(value = """
                select  v.* from product p
                 join vendor v on  v.user_id=p.user_id
                 where p.id=:productId
                """,nativeQuery = true)
        Vendor getVendorByProductId(Long productId);


        @Query("SELECT new com.treasuremount.petshop.DTO.VendorWithUserName(" +
                "v.id, v.imageUrl, v.shopName, v.contactDetails, v.taxId, v.registrationNumber, v.activeStatus, " +
                "v.address, v.city, v.gstNumber, CONCAT(u.firstName, ' ', u.lastName), v.userId, v.CountryId, v.StateId, v.postalCode,"+
                "v.createdDate, v.modifiedDate) " +
                "FROM Vendor v " +
                "JOIN User u ON u.id = v.userId") // Join the User table to get the userName
        List<VendorWithUserName> findAllVendorsWithUserName();

}
