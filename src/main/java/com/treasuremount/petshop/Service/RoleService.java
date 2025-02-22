package com.treasuremount.petshop.Service;



import com.treasuremount.petshop.Entity.Role;

import java.util.List;


public interface RoleService {

    Role create(Role d);

    List<Role> getAll();

    Role getOne(Integer id);

    Role update(Role d, Integer id);

    void delete(Integer id);

}
