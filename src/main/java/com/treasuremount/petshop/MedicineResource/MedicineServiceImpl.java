package com.treasuremount.petshop.MedicineResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService{

    @Autowired
    private MedicineRepo repository;

    @Override
    public Medicine create(Medicine d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Medicine> getAll() {

        return repository.findAll();
    }

    @Override
    public Medicine getOne(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Medicine update(Medicine d, Long id) {
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

    @Override
    public Medicine get1ProductId(Long productId){
        return repository.findByProductId(productId);
    }

    public List<MedicineResourceDTO> getAllWithConstructor(){
        return repository.getAllWithJpa();
    }
}
