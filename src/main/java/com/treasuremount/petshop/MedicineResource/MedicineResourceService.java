package com.treasuremount.petshop.MedicineResource;

import com.treasuremount.petshop.AccessoriesResource.AccessoriesResourceDTO;
import com.treasuremount.petshop.AccessoriesResource.AccessoriesResponseDTO;
import com.treasuremount.petshop.Entity.Accessories;
import com.treasuremount.petshop.Entity.AccessoryDetails;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Service.AccessoriesDetailService;
import com.treasuremount.petshop.Service.AccessoriesImpl;
import com.treasuremount.petshop.Service.ProductServiceImpl;
import com.treasuremount.petshop.utils.Mapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
@Slf4j
public class MedicineResourceService {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private MedicineDetailsService medicineDetailsService;

    @Autowired
    private Mapper mapper;

    public MedicineResourceDTO createAccessory(MedicineResourceDTO resource) {
        try {
            // Step 1: Save Product Entity
            Product product = mapper.ProducttoEntity(resource);
            product = productService.create(product);

            log.error("ProductId saved {}",product);

            // Step 2: Save Medicine Entity with the saved product ID
            Medicine medicine = mapper.MedicineToEntity(resource, product.getId());
            medicine.setCreateDate(product.getCreatedDate());
            medicine = medicineService.create(medicine);

            log.error("Medicine is saved {}",medicine);

            // Step 3: Save MedicineDetails Entity with the saved medicine ID
            MedicineDetails details = mapper.MedicineDetailsToEntity(resource, medicine.getId());
            // Instead of setting the Medicine entity directly, we only set the ID
            details.setMedicineId(medicine.getId());
            details = medicineDetailsService.create(details);
            log.error("MedicineDetails is saved {}",details);

            // Step 4: Map everything to DTO and return
            return mapper.toMedicineResourceDTO(product, medicine, details);
        } catch (TransientPropertyValueException e) {
            log.error("Error occurred while creating medicine: {}", e.getMessage(), e);
        }
        catch (Exception e) {
            log.error("Error occurred while creating medicine: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create medicine resource", e);
        }
        return null;
    }



    public MedicineResourceDTO getPetById(Long productId) {
        Product product = productService.getOneById(productId);
        System.out.println("product found ->" +product);
        if (product == null) return null;


        Medicine pets =medicineService.get1ProductId(productId);
        System.out.println("medicine found ->" +pets);
        if (pets == null) return null;


        MedicineDetails details = medicineDetailsService.get1ByMedicineId(pets.getId());
        System.out.println("details found ->" +details);
        if (details == null) return null;


        return mapper.toMedicineResourceDTO(product, pets, details);
    }

    public List<MedicineResourceDTO> getAllPets() {
        List<Product> products = productService.getAll();
        List<MedicineResourceDTO> responses = new ArrayList<>();

        for (Product product : products) {
            Medicine pets = medicineService.get1ProductId(product.getId());
            if (pets != null) {
                MedicineDetails details = medicineDetailsService.get1ByMedicineId(pets.getId());
                if (details != null) {
                    responses.add(mapper.toMedicineResourceDTO(product, pets, details));
                }
            }
        }

        return responses;
    }

    public List<MedicineResourceDTO> getAllByQuery() {
        List<MedicineResourceDTO> responseDTOS= medicineService.getAllWithConstructor();

      /*  for(AccessoriesResponseDTO acc:responseDTOS){
          *//*  String imageUrl=productService.getAllUrl(acc.getProductId());
            acc.setProductImageUrl(imageUrl);*//*
        }*/
        return responseDTOS;

    }

    public MedicineResourceDTO updatePet(Long productId, MedicineResourceDTO resource) {
        // Check if product exists\
        System.out.println("Hello from the controller");
        Product existingProduct = productService.getOneById(productId);
        if (existingProduct == null) return null;
        Product updatedProduct=null;
        // Update product
        try{
            updatedProduct = mapper.ProducttoEntity(resource);
            updatedProduct.setId(productId);
            updatedProduct = productService.update(updatedProduct,productId);
            System.out.println("ProductUpdate is done");

        }catch (Exception e){
            e.printStackTrace();
        }

        // Update medicine
        Medicine existingPets = medicineService.get1ProductId(productId);
        if (existingPets != null) {
            Medicine updatedPets = mapper.MedicineToEntity(resource, productId);

//            updatedFood.setId(existingFood.getId());
            updatedPets = medicineService.update(updatedPets,existingPets.getId());

            System.out.println("FoodUpdate is done");

            // Update food details
            MedicineDetails existingDetails = medicineDetailsService.get1ByMedicineId(existingPets.getId());
            if (existingDetails != null) {
                MedicineDetails updatedDetails = mapper.MedicineDetailsToEntity(resource, existingPets.getId());
//                updatedDetails.setId(existingDetails.getId());
                updatedDetails = medicineDetailsService.update(updatedDetails,existingDetails.getId());
                System.out.println("FoodDetails is done");

                return mapper.toMedicineResourceDTO(updatedProduct, updatedPets, updatedDetails);
            }
        }
        return null;
    }

    @Transactional
    public Boolean deleteByProductId(Long id) {
        try {
            Product product = productService.getOneById(id);
            if (product == null) return false;

            Medicine food = medicineService.get1ProductId(id);
            if (food != null) {
                MedicineDetails details = medicineDetailsService.get1ByMedicineId(food.getId());
                if (details != null) {
                    medicineDetailsService.delete(details.getId());
                }
                medicineService.delete(food.getId());
            }
            productService.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


