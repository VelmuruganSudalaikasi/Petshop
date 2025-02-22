package com.treasuremount.petshop.Delivery.ShippmentDTO;


import com.treasuremount.petshop.DTO.BlogDTO;
import com.treasuremount.petshop.Entity.Blog;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OrderInfoService {


    @Autowired
    private OrderInfoRepo repository;

    public OrderInformation create(OrderInformation d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }



    public List<OrderInformation> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }




    public OrderInformation getOneById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }



    public OrderInformation update(OrderInformation d, Long id) {
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

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
