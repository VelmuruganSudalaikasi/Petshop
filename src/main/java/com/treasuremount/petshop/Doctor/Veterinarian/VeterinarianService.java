package com.treasuremount.petshop.Doctor.Veterinarian;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface VeterinarianService  {


    Veterinarian create(Veterinarian d);

    Veterinarian getOneById(Long id);

    List<Veterinarian> getAll();

    Veterinarian update(Veterinarian d, Long id);

    void delete(Long id);
    Veterinarian getOneByUserId(Long userId);



}
