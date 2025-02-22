package com.treasuremount.petshop.Service;


import com.treasuremount.petshop.Entity.InventoryLocation;
import com.treasuremount.petshop.Repository.InventoryLocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class InventoryLocationServiceImpl implements InventoryLocationService {
    @Autowired
    private InventoryLocationRepo repository;



    @Override
    public InventoryLocation create(InventoryLocation d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<InventoryLocation> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }



    @Override
    public InventoryLocation getOneUserById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }


    @Override
    public InventoryLocation update(InventoryLocation d, Long id) {
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
