package com.treasuremount.petshop.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileImageService  {

    @Autowired
    ProfileImageRepo repository;

    public ProfileImage create(ProfileImage image){
        try{
            repository.save(image);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public List<ProfileImage> getAll(){
        try{

            return repository.findAll();

        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

    public ProfileImage getOneByUserId(Long userId){
        try{

            Optional<ProfileImage> image=repository.findByUserId(userId);
            return image.orElse(null);

        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
