package com.treasuremount.petshop.Service;

import com.treasuremount.petshop.Entity.ProductStatus;
import com.treasuremount.petshop.Repository.ProductStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
@org.springframework.stereotype.Service
public class ProductStatusService implements Service<ProductStatus,Long>{
    @Autowired
    private ProductStatusRepo repository;

    @Override
    public ProductStatus create(ProductStatus d) {
        try {

            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<ProductStatus> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }



    @Override
    public ProductStatus getOneById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ProductStatus update(ProductStatus d, Long id) {
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







}


