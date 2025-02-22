package com.treasuremount.petshop.Documents.Certificate;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CertificationRepo extends JpaRepository<Certificates, Long> {
    // Derived query method based on method name convention
    Certificates findByProductIdAndDocumentId(Long productId, Long documentId);

    // Optional additional query methods if needed
    List<Certificates> findByProductId(Long productId);
    List<Certificates> findByDocumentId(Long documentId);

    void deleteByProductId(Long productId);

}
