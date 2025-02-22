package com.treasuremount.petshop.ShopImage.Position;

import java.util.List;



public interface ShopImagePositionService {

    ShopImagePosition create(ShopImagePosition d);

    List<ShopImagePosition> getAll();

    ShopImagePosition getOne(Long id);

    ShopImagePosition update(ShopImagePosition d, Long id);

    void delete(Long id);
}


