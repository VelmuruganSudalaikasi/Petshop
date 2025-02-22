package com.treasuremount.petshop.Pages.HomPage1;

import java.util.List;

public interface HomePage1Service {


    HomePage1 create(HomePage1 d);

    List<HomePage1> getAll();

    HomePage1 getOne(Long id);

    HomePage1 update(HomePage1 d, Long id);

    void delete(Long id);


}