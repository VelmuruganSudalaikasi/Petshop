package com.treasuremount.petshop.ProductImage.Position;
import com.treasuremount.petshop.Entity.Reviews;
import java.util.List;



public interface PositionService {

    Position create(Position d);

    List<Position> getAll();

    Position getOne(Long id);

    Position update(Position d, Long id);

    void delete(Long id);
}


