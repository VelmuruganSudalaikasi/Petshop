package com.treasuremount.petshop.MedicineResource;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineDetailsRepo extends JpaRepository<MedicineDetails,Long> {

    @Query("SELECT md FROM MedicineDetails md WHERE md.medicine.id = :medicineId")
    MedicineDetails findByMedicineId(@Param("medicineId") Long medicineId);

}