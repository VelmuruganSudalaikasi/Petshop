package com.treasuremount.petshop.MedicineResource;


import java.util.List;

public interface MedicineService {


    Medicine create(Medicine d);

    List<Medicine> getAll();

    Medicine getOne(Long id);

    Medicine update(Medicine d, Long id);

    void delete(Long id);

    Medicine get1ProductId(Long id);

    List<MedicineResourceDTO> getAllWithConstructor();
}
