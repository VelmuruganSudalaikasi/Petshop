package com.treasuremount.petshop.AccessoriesResource;


import com.treasuremount.petshop.Entity.*;
import com.treasuremount.petshop.Service.*;
import com.treasuremount.petshop.utils.Mapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@Slf4j
public class AccResourceService {

    @Autowired
    private AccessoriesImpl accessoriesService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private AccessoriesDetailService accessoriesDetailService;

    @Autowired
    private Mapper mapper;

    @Transactional

    public AccessoriesResponseDTO createAccessory(AccessoriesResourceDTO resource) {
        try {
            // Step 1: Map and Save Product Entity
            Product product = mapper.ProducttoEntity(resource);
            product = productService.create(product);

            // Step 2: Map and Save Accessories Entity
            Accessories accessories = mapper.AcctoEntity(resource, product.getId());
            accessories.setCreatedAt(product.getCreatedDate());
            accessories.setUpdatedAt(product.getUpdatedDate());
            accessories = accessoriesService.create(accessories);

            // Step 3: Map and Save AccessoryDetails Entity
            AccessoryDetails details = mapper.AccDetailstoEntity(resource, accessories.getId());
            details.setCreatedAt(product.getCreatedDate());
            details=accessoriesDetailService.create(details);

            // Step 4: Map all saved entities to the Response DTO
            return mapper.AccessoriesResponseDTO(product, accessories, details);

        } catch (Exception e) {
            // Log the error and rethrow as a runtime exception
            log.error("Error occurred while creating accessory: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to process accessory creation at this time.");
        }
    }



    public AccessoriesResponseDTO getPetById(Long productId) {
        Product product = productService.getOneById(productId);
        System.out.println("product found ->" +product);
        if (product == null) return null;


        Accessories pets = accessoriesService.get1ByProductId(productId);
        System.out.println("pets found ->" +pets);
        if (pets == null) return null;


        AccessoryDetails details = accessoriesDetailService.get1ByFoodId(pets.getId());
        System.out.println("details found ->" +details);
        if (details == null) return null;


        return mapper.AccessoriesResponseDTO(product, pets, details);
    }

    public List<AccessoriesResponseDTO> getAllPets() {
        List<Product> products = productService.getAll();
        List<AccessoriesResponseDTO> responses = new ArrayList<>();

        for (Product product : products) {
            Accessories pets = accessoriesService.get1ByProductId(product.getId());
            if (pets != null) {
                AccessoryDetails details = accessoriesDetailService.get1ByFoodId(pets.getId());
                if (details != null) {
                    responses.add(mapper.AccessoriesResponseDTO(product, pets, details));
                }
            }
        }

        return responses;
    }

    public List<AccessoriesResponseDTO> getAllByQuery() {
        List<AccessoriesResponseDTO> responseDTOS= accessoriesService.getAllWithConstructor();

        for(AccessoriesResponseDTO acc:responseDTOS){
          /*  String imageUrl=productService.getAllUrl(acc.getProductId());
            acc.setProductImageUrl(imageUrl);*/
        }
        return responseDTOS;

    }

    public AccessoriesResponseDTO updatePet(Long productId, AccessoriesResourceDTO resource) {
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

        // Update food
        Accessories existingPets = accessoriesService.get1ByProductId(productId);
        if (existingPets != null) {
            Accessories updatedPets = mapper.AcctoEntity(resource, productId);

//            updatedFood.setId(existingFood.getId());
            updatedPets = accessoriesService.update(updatedPets,existingPets.getId());

            System.out.println("FoodUpdate is done");

            // Update food details
            AccessoryDetails existingDetails = accessoriesDetailService.get1ByFoodId(existingPets.getId());
            if (existingDetails != null) {
                AccessoryDetails updatedDetails = mapper.AccDetailstoEntity(resource, existingPets.getId());
//                updatedDetails.setId(existingDetails.getId());
                updatedDetails = accessoriesDetailService.update(updatedDetails,existingDetails.getId());
                System.out.println("FoodDetails is done");

                return mapper.AccessoriesResponseDTO(updatedProduct, updatedPets, updatedDetails);
            }
        }
        return null;
    }

    @Transactional
    public Boolean deleteByProductId(Long id) {
        try {
            Product product = productService.getOneById(id);
            if (product == null) return false;

            Accessories food = accessoriesService.get1ByProductId(id);
            if (food != null) {
                AccessoryDetails details = accessoriesDetailService.get1ByFoodId(food.getId());
                if (details != null) {
                    accessoriesDetailService.delete(details.getId());
                }
                accessoriesService.delete(food.getId());
            }
            productService.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


