package com.treasuremount.petshop.Documents;


import java.util.List;

public interface DocumentsService {

    Documents create(Documents d);

    List<Documents> getAll();

    Documents getOne(Long id);

    Documents update(Documents d, Long id);

    void delete(Long id);
}


