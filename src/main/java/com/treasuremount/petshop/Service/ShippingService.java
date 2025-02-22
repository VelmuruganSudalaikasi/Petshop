package com.treasuremount.petshop.Service;

import com.treasuremount.petshop.Entity.Blog;
import com.treasuremount.petshop.Entity.ShippingAddress;
import com.treasuremount.petshop.Repository.BlogRepo;
import com.treasuremount.petshop.Repository.ShippingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collections;
import java.util.List;

@org.springframework.stereotype.Service
public class ShippingService implements Service<ShippingAddress,Long>{
    @Autowired
    private ShippingRepo repository;


    @Override
    public ShippingAddress create(ShippingAddress d) {
        try {
            System.out.println(d);
            return repository.save(d);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ShippingAddress> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }




    @Override
    public ShippingAddress getOneById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<ShippingAddress> getAddressesByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }




    @Override
    public ShippingAddress update(ShippingAddress d, Long id) {
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

