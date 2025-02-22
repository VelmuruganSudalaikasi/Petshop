package com.treasuremount.petshop.Service;


import com.treasuremount.petshop.DTO.ProductDTO;
import com.treasuremount.petshop.Entity.Pets;

import java.util.List;

public interface Service<T, ID> {

    T create(T entity);


    T getOneById(ID id);

    T update(T entity, ID id);

    void delete(ID id);
    List<T> getAll();


}
