package com.treasuremount.petshop.MedicineResource;

import com.treasuremount.petshop.Entity.SubCategory;
import com.treasuremount.petshop.Repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MedicineDetailsServiceImpl implements MedicineDetailsService {

    @Autowired
    private MedicineDetailsRepo repository;

    @Override
    public MedicineDetails create(MedicineDetails d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<MedicineDetails> getAll() {

        return repository.findAll();
    }

    @Override
    public MedicineDetails getOne(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public MedicineDetails update(MedicineDetails d, Long id) {
        try {

            if (repository.existsById(id)) {
                d.setId(id);
                return repository.saveAndFlush(d);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

   public   MedicineDetails get1ByMedicineId(Long medicineId){
        return repository.findByMedicineId(medicineId);
   }
}
