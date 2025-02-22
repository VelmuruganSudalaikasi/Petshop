package com.treasuremount.petshop.Service;

import java.util.Collections;
import java.util.List;

import com.treasuremount.petshop.Entity.Role;
import com.treasuremount.petshop.Repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo repository;

    @Override
    public Role create(Role d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Role> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public Role getOne(Integer id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Role update(Role d, Integer id) {
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
    public void delete(Integer id) {
        repository.deleteById(id);
    }

}

