package com.treasuremount.petshop.Service;


import com.treasuremount.petshop.Entity.BankDetails;
import com.treasuremount.petshop.Entity.InventoryLocation;
import com.treasuremount.petshop.Repository.BankDetailsRepo;
import com.treasuremount.petshop.Repository.BankDetailsService;
import com.treasuremount.petshop.Repository.InventoryLocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BankDetailsServiceImpl implements BankDetailsService {
    @Autowired
    private BankDetailsRepo repository;



    @Override
    public BankDetails create(BankDetails d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<BankDetails> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }



    @Override
    public BankDetails getOneById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }


    @Override
    public BankDetails update(BankDetails d, Long id) {
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
