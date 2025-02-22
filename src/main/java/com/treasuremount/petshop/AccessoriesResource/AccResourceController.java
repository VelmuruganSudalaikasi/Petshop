package com.treasuremount.petshop.AccessoriesResource;



import com.treasuremount.petshop.Entity.Accessories;
import com.treasuremount.petshop.Entity.AccessoryDetails;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.utils.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;
/*
*   @PostMapping("/add")
*   @GetMapping("/getOne/{id}")
*   @GetMapping("/getAll")
*   @PutMapping("/update/{id}")
*   @DeleteMapping("/delete/{id}")
*
* */

@RestController
@RequestMapping("/api/public/resource/accessories")
@Transactional
public class AccResourceController {

    @Autowired
    private Mapper mapper;

    @Autowired
    private AccResourceService service;

    @PostMapping("/add")
    public ResponseEntity<AccessoriesResponseDTO> createFood(@RequestBody @Valid AccessoriesResourceDTO resource) {

        try{
            AccessoriesResponseDTO responseDTO=service.createAccessory(resource);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
          return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<AccessoriesResponseDTO> getFoodById(@PathVariable("id") Long id) {
        try {
            AccessoriesResponseDTO response = service.getPetById(id);
            return response != null ?
                    ResponseEntity.ok(response) :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<AccessoriesResponseDTO>> getAllFoods() {
        try {
            List<AccessoriesResponseDTO> foods = service.getAllByQuery();
            return  ResponseEntity.ok(foods);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccessoriesResponseDTO> updateFood(
            @PathVariable("id") Long id,
            @RequestBody @Valid AccessoriesResourceDTO resource) {
        try {
            AccessoriesResponseDTO updated = service.updatePet(id, resource);
            return updated != null ?
                    ResponseEntity.ok(updated) :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable("id") Long id) {
        try {
            boolean deleted = service.deleteByProductId(id);
            return deleted ?
                    ResponseEntity.ok().build() :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

/*
@RestController
@RequestMapping("/api/public/resource")
public class ResourceController {

    */
/*
 *  get for get the full product
 *  add the product
 *  update the product
 *  delete the product
 *
 * *//*

    @Autowired
    Mapper mapper;

    @Autowired
    ResourceService service;


    @PostMapping("/add/food")
    public ResponseEntity<FoodResourceDTO> createUser(@RequestBody @Valid FoodResourceDTO resource) {

   try{
    Product product=mapper.ProducttoEntity(resource);
    product=service.createProduct(product);
    Foods food=mapper.FoodtoEntity(resource,product.getId());
    food= service.createFood(food);
    FoodDetails details=mapper.FooodDetailstoEntity(resource,food.getId());
    service.createFoodDetails(details);
    FoodResponseDTO response=mapper.FoodDTOtoResponse(product,food,details);
    System.out.println(response);
   } catch (Exception e) {
       throw new RuntimeException(e);
   }


    return new ResponseEntity<>(resource,HttpStatus.CREATED);

    }


    @DeleteMapping("/delete/food/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {


    }

    @GetMapping("/getALL")






*/
/*
    @GetMapping("/getAll")
    public ResponseEntity<List<Accessories>> getAllUsers() {
        List<Accessories> users = service.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Accessories> getOneUser(@PathVariable("id") Long id) {
        Accessories user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Accessories> updateUser(@RequestBody @Valid Accessories user, @PathVariable("id") Long id) {
        Accessories updatedUser = service.update(user, id);
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
    }*//*





}
*/
