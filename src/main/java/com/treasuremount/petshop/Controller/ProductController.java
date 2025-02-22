package com.treasuremount.petshop.Controller;


import com.treasuremount.petshop.DTO.ProductDTO;
import com.treasuremount.petshop.DTO.ProductDemoDTO;
import com.treasuremount.petshop.DTO.ProductUpdateDTO;
import com.treasuremount.petshop.Entity.Cart;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Enum.Carts;
import com.treasuremount.petshop.Service.ProductServiceImpl;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl service;

    public static List<Integer> lst=new ArrayList<>();

    @PostMapping("/add")
    public ResponseEntity<Product> createUser(@RequestBody @Valid Product user) {
        Product createdUser = service.create(user);
        if(createdUser!=null) {
            return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(createdUser,HttpStatus.NOT_FOUND);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDTO>> getAll(
            @RequestParam(required = false, defaultValue = "0") Long userId,
            @RequestParam(required = false, defaultValue = "0") Long categoryId,
            @RequestParam(required = false, defaultValue = "0") Long subCategoryId,
            @RequestParam(required = false, defaultValue = "0") Double minPrice,
            @RequestParam(required = false, defaultValue = "0") Double maxPrice,
            @RequestParam(required = false, defaultValue = "0") Long ProductStatusId,
            @RequestParam(required = false, defaultValue = "0") Boolean isAdmin,
            @RequestParam(required = false, defaultValue = "") String searchWord
    ) {
        List<ProductDTO> products = service.getAll1(userId, categoryId, subCategoryId,
                minPrice, maxPrice, ProductStatusId, isAdmin, searchWord);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/getRelevantProduct")
    public ResponseEntity<List<ProductDTO>> getAllModern(
            @RequestParam("ProductId") Long productId)
    {
                List<ProductDTO> relevantProduct=service.getAllRelevantProduct(productId);
                return ResponseEntity.ok(relevantProduct);
    }


    @GetMapping("/homePageCarts")
    public ResponseEntity<List<ProductDTO>> getAllModern(
            @RequestParam("cart") Carts cart,
            @RequestParam("limit") Long limit)


    {
        List<ProductDTO> relevantProduct=service.getHomePageCarts(cart,limit);
        return ResponseEntity.ok(relevantProduct);
    }



    @GetMapping("/getAll/temp")
    public ResponseEntity<List<ProductDTO>> getAll(
            @RequestParam(required = false,defaultValue = "0") Long userId,
            @RequestParam(required = false) List<Long> categoryId,
            @RequestParam(required = false) List<Long> subCategoryId,
            @RequestParam(required = false,defaultValue = "0") Double minPrice,
            @RequestParam(required = false,defaultValue = "0") Double maxPrice,
            @RequestParam(required = false) List<Long> ProductStatusId,
            @RequestParam(required = false,defaultValue = "0") Boolean isAdmin
    ) {





        System.err.println("userId ->>>>>>>>"+ userId);
        System.err.println("categoryId ->>>>>>>>"+ categoryId);
        System.err.println("subCategoryId ->>>>>>>>"+ subCategoryId);
        System.err.println("minPrice ->>>>>>>>"+ minPrice);
        System.err.println("maxPrice ->>>>>>>>"+ maxPrice);
        System.err.println("ProductStatusId ->>>>>>>>"+ ProductStatusId);
        System.err.println("isAdmin ->>>>>>>>"+ isAdmin);



        List<ProductDTO> products = service.getAll2(userId,isEmptySetNull(categoryId),categoryId,
                isEmptySetNull(subCategoryId),subCategoryId,minPrice,maxPrice,
                isEmptySetNull(ProductStatusId),ProductStatusId,isAdmin);

        return ResponseEntity.ok(products);
    }


    public Boolean isEmptySetNull(List<Long> temp){
        System.out.println("Check---->"+temp);
        return (temp==null || temp.isEmpty());


    }



    @PatchMapping("/edit/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody ProductUpdateDTO dto) {
        Product response= service.partialUpdate(id,dto);
        if(response!=null){
            return  ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();

    }



    @GetMapping("/getOne/{id}")
    public ResponseEntity<ProductDemoDTO> getOneUser(@PathVariable("id") Long id) {
        ProductDemoDTO user = service.getOneByIdDto(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateUser(@RequestBody @Valid Product user, @PathVariable("id") Long id) {
        Product updatedUser = service.update(user, id);
        if (updatedUser != null) {
            return ResponseEntity.ok().body(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }


}
