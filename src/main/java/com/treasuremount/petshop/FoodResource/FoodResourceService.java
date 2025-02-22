package com.treasuremount.petshop.FoodResource;



import com.treasuremount.petshop.AccessoriesResource.AccessoriesResponseDTO;
import com.treasuremount.petshop.Entity.FoodDetails;
import com.treasuremount.petshop.Entity.Foods;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Service.FoodDetailsService;
import com.treasuremount.petshop.Service.FoodServiceImpl;
import com.treasuremount.petshop.Service.ProductServiceImpl;
import com.treasuremount.petshop.utils.Mapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public  class FoodResourceService {

    @Autowired
    private FoodServiceImpl foodService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private FoodDetailsService foodDetailsService;

    @Autowired
    private Mapper mapper;


    public FoodResponseDTO createFoodResource(FoodResourceDTO resource) {
        // Create product
        Product product = mapper.ProducttoEntity(resource);
        product = productService.create(product);
        log.info("Product created: {}", product);

        // Create food entity
        Foods food = mapper.FoodtoEntity(resource, product.getId());
        food = foodService.create(food);
        log.info("Food entity created: {}", food);

        // Create food details
        FoodDetails details = mapper.FooodDetailstoEntity(resource, food.getId());
        details.setCreatedAt(product.getCreatedDate());
        foodDetailsService.create(details);
        log.info("Food details created: {}", details);

        // Map response
        return mapper.FoodDTOtoResponse(product, food, details);
    }




    public FoodResponseDTO getFoodById(Long productId) {
        Product product = productService.getOneById(productId);

        if (product == null) return null;

        Foods food = foodService.get1ByProductId(productId);
        if (food == null) return null;

        FoodDetails details = foodDetailsService.get1ByFoodId(food.getId());
        if (details == null) return null;

        return mapper.FoodDTOtoResponse(product, food, details);
    }

    public List<FoodResponseDTO> getAllFoods() {
        List<Product> products = productService.getAll();
        List<FoodResponseDTO> responses = new ArrayList<>();

        for (Product product : products) {
            Foods food = foodService.get1ByProductId(product.getId());
            if (food != null) {
                FoodDetails details = foodDetailsService.get1ByFoodId(food.getId());
                if (details != null) {
                    responses.add(mapper.FoodDTOtoResponse(product, food, details));
                }
            }
        }

        return responses;
    }

    public List<FoodResponseDTO> getAllByQuery() {
        List<FoodResponseDTO> responseDTOS= foodService.getAllWithConstructor();
        for(FoodResponseDTO acc:responseDTOS){
           /* String imageUrl=productService.getAllUrl(acc.getProductId());
            acc.setProductImageUrl(imageUrl);*/
        }
        return responseDTOS;
    }

    public FoodResponseDTO updateFood(Long productId, FoodResourceDTO resource) {
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
        Foods existingFood = foodService.get1ByProductId(productId);
        if (existingFood != null) {
            Foods updatedFood = mapper.FoodtoEntity(resource, productId);

            //            updatedFood.setId(existingFood.getId());
            updatedFood = foodService.update(updatedFood,existingFood.getId());

            System.out.println("FoodUpdate is done");

            // Update food details
            FoodDetails existingDetails = foodDetailsService.get1ByFoodId(existingFood.getId());
            if (existingDetails != null) {
                FoodDetails updatedDetails = mapper.FooodDetailstoEntity(resource, existingFood.getId());
                //                updatedDetails.setId(existingDetails.getId());
                updatedDetails = foodDetailsService.update(updatedDetails,existingDetails.getId());
                System.out.println("FoodDetails is done");

                return mapper.FoodDTOtoResponse(updatedProduct, updatedFood, updatedDetails);
            }
        }
        return null;
    }

    @Transactional
    public Boolean deleteByProductId(Long id) {
        try {
            Product product = productService.getOneById(id);
            if (product == null) return false;

            Foods food = foodService.get1ByProductId(id);
            if (food != null) {
                FoodDetails details = foodDetailsService.get1ByFoodId(food.getId());
                if (details != null) {
                    foodDetailsService.delete(details.getId());
                }
                foodService.delete(food.getId());
            }
            productService.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

