package com.treasuremount.petshop.Service;

import com.treasuremount.petshop.Entity.InventoryLocation;


import java.util.List;

public interface InventoryLocationService {


    InventoryLocation create(InventoryLocation d);

    List<InventoryLocation> getAll();

    InventoryLocation getOneUserById(Long id);

    InventoryLocation update(InventoryLocation d, Long id);

    void delete(Long id);


}
