package com.treasuremount.petshop.MedicineResource;

import java.util.List;

public interface MedicineDetailsService {

    MedicineDetails create(MedicineDetails d);

    List<MedicineDetails> getAll();

    MedicineDetails getOne(Long id);

    MedicineDetails update(MedicineDetails d, Long id);

    void delete(Long id);

    MedicineDetails  get1ByMedicineId(Long medicineId);
}
