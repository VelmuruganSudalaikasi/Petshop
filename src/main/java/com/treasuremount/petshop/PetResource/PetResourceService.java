package com.treasuremount.petshop.PetResource;


import com.treasuremount.petshop.DTO.UserDTO;
import com.treasuremount.petshop.DTO.VendorDTO;
import com.treasuremount.petshop.DTO.VendorRegisterDTO;
import com.treasuremount.petshop.Entity.*;
import com.treasuremount.petshop.FoodResource.FoodResponseDTO;
import com.treasuremount.petshop.Repository.PetRepo;
import com.treasuremount.petshop.Service.PetDetailsService;
import com.treasuremount.petshop.Service.PetService;
import com.treasuremount.petshop.Service.ProductServiceImpl;
import com.treasuremount.petshop.Service.VendorService;
import com.treasuremount.petshop.utils.Mapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.treasuremount.petshop.utils.customMapper;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@Slf4j
public class PetResourceService {

    @Autowired
    private PetService petService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private PetDetailsService petDetailsService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private customMapper customMapper;


    @Autowired
    private PetRepo repo;

    @Autowired
    private Mapper mapper;

    public PetResponseDTO createPet(PetResourceDTO resource){
        try {
            Product product = mapper.ProducttoEntity(resource);
            log.info(" {}  product before saving" ,product);
            product = productService.create(product);

            Pets pets = mapper.PettoEntity(resource, product.getId());
            pets.setCreatedAt(product.getCreatedDate());
            pets.setUpdatedAt(product.getUpdatedDate());
            log.info(" {}  pet before saving" ,pets);
            pets = petService.create(pets);

            PetDetails details = mapper.PetDetailstoEntity(resource, pets.getId());
            details.setCreatedAt(product.getCreatedDate());
            log.info(" {}  petDetails before saving" ,details);
            details= petDetailsService.create(details);

           return  mapper.PetsDTOtoResponse(product, pets, details);

        } catch (Exception e) {
            // Log the error and rethrow as a runtime exception
            log.error("Error occurred while creating accessory: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to process accessory creation at this time.");
        }
    }



    public PetResponseDTO getPetById(Long productId) {
        Product product = productService.getOneById(productId);
        System.out.println("product found ->" +product);
        if (product == null) return null;


        Pets pets = petService.get1ByProductId(productId);
        System.out.println("pets found ->" +pets);
        if (pets == null) return null;


        PetDetails details = petDetailsService.get1ByFoodId(pets.getId());
        System.out.println("details found ->" +details);
        if (details == null) return null;


        return mapper.PetsDTOtoResponse(product, pets, details);
    }

    public List<PetResponseDTO> getAllPets() {
        List<Product> products = productService.getAll();
        List<PetResponseDTO> responses = new ArrayList<>();

        for (Product product : products) {
            Pets pets = petService.get1ByProductId(product.getId());
            if (pets != null) {
                PetDetails details = petDetailsService.get1ByFoodId(pets.getId());
                if (details != null) {
                    responses.add(mapper.PetsDTOtoResponse(product, pets, details));
                }
            }
        }

        return responses;
    }

    public List<PetResponseDTO> getAllByQuery() {


        List<PetResponseDTO> responseDTOS=petService.getAllByQuery();
        for(PetResponseDTO acc:responseDTOS){
           /* String imageUrl=productService.getAllUrl(acc.getProductId());
            acc.setProductImageUrl(imageUrl);*/
        }
        return responseDTOS;
    }

    public PetResponseDTO updatePet(Long productId, PetResourceDTO resource) {
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
        Pets existingPets = petService.get1ByProductId(productId);
        if (existingPets != null) {
            Pets updatedPets = mapper.PettoEntity(resource, productId);

//            updatedFood.setId(existingFood.getId());
            updatedPets = petService.update(updatedPets,existingPets.getId());

            System.out.println("FoodUpdate is done");

            // Update food details
            PetDetails existingDetails = petDetailsService.get1ByFoodId(existingPets.getId());
            if (existingDetails != null) {
                PetDetails updatedDetails = mapper.PetDetailstoEntity(resource, existingPets.getId());
//                updatedDetails.setId(existingDetails.getId());
                updatedDetails = petDetailsService.update(updatedDetails,existingDetails.getId());
                System.out.println("FoodDetails is done");

                return mapper.PetsDTOtoResponse(updatedProduct, updatedPets, updatedDetails);
            }
        }
        return null;
    }

    @Transactional
    public Boolean deleteByProductId(Long id) {
        try {
            Product product = productService.getOneById(id);
            if (product == null) return false;

            Pets food = petService.get1ByProductId(id);
            if (food != null) {
                PetDetails details = petDetailsService.get1ByFoodId(food.getId());
                if (details != null) {
                    petDetailsService.delete(details.getId());
                }
                petService.delete(food.getId());
            }
            productService.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public VendorRegisterDTO getOwner(Long ProductId){

        if(repo.findByProductId(ProductId)!=null){

        }
        User user=productService.getOneById(ProductId).getUser();
        Vendor vendor=vendorService.getOneUserById(user.getId());
        log.info("Vendor Saved Details: {}",vendor);
        log.info("user Saved Details: {}",user);
        return (vendor == null ) ? new VendorRegisterDTO(customMapper.toUserDTO(user), null) : customMapper.toVendorRegisterDTO(user, vendor);

    }



}
