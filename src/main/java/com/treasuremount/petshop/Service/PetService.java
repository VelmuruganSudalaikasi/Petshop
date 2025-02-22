package com.treasuremount.petshop.Service;



import com.treasuremount.petshop.Entity.Foods;
import com.treasuremount.petshop.Entity.Pets;

import com.treasuremount.petshop.PetResource.PetResponseDTO;
import com.treasuremount.petshop.Repository.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class PetService implements Service<Pets,Long>{
    @Autowired
    private PetRepo repository;


    @Override
    public Pets create(Pets d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    public List<PetResponseDTO> getAllByQuery(){
        return repository.getAllWithJpa();
    }

/*
    @Override
    public Pets getOneById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }*/


    @Override
    public List<Pets> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

//    @Override
//    public List<UserDTOs> getAllUser() {
//        try {
//            List<User> all = repository.findAll();
//            List<UserDTOs> response = new ArrayList<>();
//
//            for (User assignedUser : all) {
//                Role role = assignedUser.getRole();
//
//                UserDTOs assignedUserDTO = new UserDTOs(
//                        assignedUser.getId(),
//                        assignedUser.getFirstName(),
//                        assignedUser.getLastName(),
//                        assignedUser.getEmailId(),
//                        assignedUser.getMobileNumber(),
//                        assignedUser.getPassword(),
//                        assignedUser.getConfirmPassword(),
//                        assignedUser.getActiveStatus(),
//                        assignedUser.getRoleId(),
//                        role != null ? role.getName() : null,
//                        assignedUser.getUserProfile(),
//                        assignedUser.getCreatedDate(),
//                        assignedUser.getModifiedDate()
//                );
//
//                response.add(assignedUserDTO);
//            }
//
//            return response;
//        } catch (Exception ex) {
//            return Collections.emptyList();
//        }
//    }



    @Override
    public Pets getOneById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }


//    @Override
//    public UserDTOs getOneById(Long id) {
//        try {
//            User assignedUser = repository.findById(id).orElse(null);
//
//            if (assignedUser == null) {
//                return null; // or throw an exception if preferred
//            }
//
//            Role role = assignedUser.getRole();
//            return new UserDTOs(
//                    assignedUser.getId(),
//                    assignedUser.getFirstName(),
//                    assignedUser.getLastName(),
//                    assignedUser.getEmailId(),
//                    assignedUser.getMobileNumber(),
//                    assignedUser.getPassword(),
//                    assignedUser.getConfirmPassword(),
//                    assignedUser.getActiveStatus(),
//                    assignedUser.getRoleId(),
//                    role != null ? role.getName() : null,
//                    assignedUser.getUserProfile(),
//                    assignedUser.getCreatedDate(),
//                    assignedUser.getModifiedDate()
//            );
//        } catch (Exception ex) {
//            return null;
//        }
//    }



    @Override
    public Pets update(Pets d, Long id) {
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
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Pets get1ByProductId(Long id) {
        return repository.findByProductId(id);
    }

    public Integer getStock(Long productId) {

        Integer stock=repository.stockAvailability(productId);
        System.out.println(stock);
        if(stock!=null)
            return stock;

        return -1;

    }

    public Boolean updateStockQuantity(Long productId,Integer updatedStock){
        repository.updateStockQuantity(productId,updatedStock);
        return true;
    }




}

