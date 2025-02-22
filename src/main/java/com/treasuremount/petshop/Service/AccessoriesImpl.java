package com.treasuremount.petshop.Service;



import com.treasuremount.petshop.AccessoriesResource.AccessoriesResponseDTO;
import com.treasuremount.petshop.Entity.Accessories;
import com.treasuremount.petshop.Repository.AccessoriesRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
@org.springframework.stereotype.Service
public class AccessoriesImpl implements Service<Accessories,Long>{
    @Autowired
    private AccessoriesRepo repository;


    @Override
    public Accessories create(Accessories d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            System.out.println("From pet respository");
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Accessories> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }



    @Override
    public Accessories getOneById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }



    @Override
    public Accessories update(Accessories d, Long id) {
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

   public Accessories get1ByProductId(Long productId){
        return repository.findByProductId(productId);
    }

    public Integer getStock(Long productId) {
        Integer stock=repository.stockAvailability(productId);
        System.out.println(stock);
        if(stock!=null)
            return stock;

        return -1;
    }

    public Boolean updateStockQuantity(Long productId,Integer updatedStock){
        repository.updateStockQuantity(productId,updatedStock);
        return true;
    }

    public List<AccessoriesResponseDTO> getAllWithConstructor(){
        return repository.getAllWithJpa();
    }
}

