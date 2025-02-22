package com.treasuremount.petshop.Service;



import com.treasuremount.petshop.DTO.VendorInfoDTO;
import com.treasuremount.petshop.DTO.VendorWithUserName;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.ProductImage.ProductImagesService;
import com.treasuremount.petshop.Repository.VendorRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class VendorServiceImpl implements VendorService{

    private static Integer userRoleId=3;
    private static Integer vendorRoleId=2;
    @Autowired
    private  VendorRepo repository;

    @Autowired
    private ProductImagesService imagesService;

    @Autowired
    private UserServiceImpl userService;



    @Override
    public Vendor create(Vendor d) {
        try {


            System.out.println(d);
            System.out.println(d.getId());
            System.out.println(d.getUserId());

            /*updateRole(d.getUserId(),d.getActiveStatus());*/
           System.out.println("Vendor before Saving" + d);
            return repository.save(d);

        } catch (Exception ex) {
    ex.printStackTrace();
    throw ex;
//                throw  ex;

        }

    }

/*

    @Override
    public boolean editVendor(Vendor v){
        Optional<Vendor> oldVendor=repository.findById(v.getId());
   if(oldVendor.isEmpty()){
       return false;
   }

   patchVendor(oldVendor.get(),v);
    return true;

    }

    public Vendor patchVendor(Vendor existingVendor, Vendor updatedVendor) {
        */
/*//*
/ Fetch the existing vendor from the database
        *//*
*/
/*Vendor existingVendor = repository.findById(vendorId)
                .orElseThrow(() -> new EntityNotFoundExc*//*
*/
/*eption("Vendor not found with id: " + vendorId));
*//*

        // Apply updates only to non-null fields
        if (updatedVendor.getImageUrl() != null) {
            existingVendor.setImageUrl(updatedVendor.getImageUrl());
        }

        if (updatedVendor.getShopName() != null) {
            existingVendor.setShopName(updatedVendor.getShopName());
        }

        if (updatedVendor.getContactDetails() != null) {
            existingVendor.setContactDetails(updatedVendor.getContactDetails());
        }

        if (updatedVendor.getTaxId() != null) {
            existingVendor.setTaxId(updatedVendor.getTaxId());
        }

        if (updatedVendor.getRegistrationNumber() != null) {
            existingVendor.setRegistrationNumber(updatedVendor.getRegistrationNumber());
        }

        if (updatedVendor.getActiveStatus() != null) {

            updateRole(updatedVendor.getUserId(),updatedVendor.getActiveStatus());

            existingVendor.setActiveStatus(updatedVendor.getActiveStatus());
        }

        if (updatedVendor.getAddress() != null) {
            existingVendor.setAddress(updatedVendor.getAddress());
        }

        if (updatedVendor.getCity() != null) {
            existingVendor.setCity(updatedVendor.getCity());
        }

        if (updatedVendor.getGstNumber() != null) {
            existingVendor.setGstNumber(updatedVendor.getGstNumber());
        }

        if (updatedVendor.getUserId() != null) {
            existingVendor.setUserId(updatedVendor.getUserId());
        }

        if (updatedVendor.getCountryId() != null) {
            existingVendor.setCountryId(updatedVendor.getCountryId());
        }

        if (updatedVendor.getStateId() != null) {
            existingVendor.setStateId(updatedVendor.getStateId());
        }

        if (updatedVendor.getCreatedDate() != null) {
            existingVendor.setCreatedDate(updatedVendor.getCreatedDate());
        }

        if (updatedVendor.getModifiedDate() != null) {
            existingVendor.setModifiedDate(updatedVendor.getModifiedDate());
        }

        // Save the updated entity back to the database
        return create(existingVendor);
    }
*/

    @Override
    public List<Vendor> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    public List<VendorWithUserName> getAllWithUserName(){
        try {
            return repository.findAllVendorsWithUserName();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }




    @Override
    public Vendor getOneUserById(Long id) {
        try {
            return repository.findByUserId(id);

        } catch (Exception ex) {
            return null;
        }
    }



    @Override
    public Vendor update(Vendor d, Long id) {
        try {
            if (repository.existsById(id)) {
                d.setId(id);

                return create(d);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
/*
    private Boolean updateRole(Long userId,Boolean activeStatus){
        Integer roleId=null;
        if(activeStatus){
            roleId=vendorRoleId;
        }
        else {
            roleId=userRoleId;
        }
        User user=userService.findById(userId);
        if(user==null){
            System.out.println("User not role is updated");
            return false;
        }
        user.setRoleId(roleId);
        userService.create(user);
        System.out.println("User role  is updated");

        return true ;
    }*/

    @Override
    public boolean delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public Vendor findById(Long id) {
        return repository.findById(id).orElse(null)
    ;}



       @Override
        public List<VendorInfoDTO> getAllCustom1() {
            List<Object[]> results = repository.findVendorInfoRaw();

            return results.stream()
                    .map(row -> new VendorInfoDTO(
                            ((Number) row[0]).longValue(),  // vendorId
                            (String) row[1],               // firstName
                            (String) row[2],               // shopName
                            (String) row[3]))             // contactDetails
                    .toList();


        }
   @Override
    public Vendor findVendorByProductId(Long productId){
        return repository.getVendorByProductId(productId);
    }


}




