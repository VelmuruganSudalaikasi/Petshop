package com.treasuremount.petshop.Repository;


import com.treasuremount.petshop.Entity.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDetailsRepo extends JpaRepository<BankDetails, Long> {
    // Additional query methods can be defined here if needed
    // Example: Optional<BankDetails> findByUpiId(String upiId);
}
