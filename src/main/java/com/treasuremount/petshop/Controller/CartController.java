package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.DTO.CartDTO;
import com.treasuremount.petshop.DTO.CartItemDto;
import com.treasuremount.petshop.DTO.ProductDemoDTO;
import com.treasuremount.petshop.Entity.Cart;

import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartDTO dto) {
        try {


            Cart cartItem = cartService.addToCart(dto.getUserId(), dto.getProductId(),dto.getInitialQuantity());
            return ResponseEntity.ok(cartItem);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestParam Long userId, @RequestParam Long cartId) {
        cartService.removeFromCart(userId, cartId);
        return ResponseEntity.ok().build();
    }

  @GetMapping("/getAll")
    public ResponseEntity<List<CartItemDto>> getCartItems(@RequestParam Long userId) {
        List<CartItemDto> cartItems = cartService.getAllCartItemsByUserIdByQuery(userId);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<CartItemDto> getOneByCart(@PathVariable("id") Long id) {
        CartItemDto user = cartService.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }
    @PatchMapping("addQuantity/{id}")
    public ResponseEntity<String> increaseQuantity(@PathVariable Long id, @RequestParam Long incrementBy) {
        if (incrementBy <= 0) {
            return ResponseEntity.badRequest().body("Increment value must be greater than 0");
        }
        if(cartService.increaseQuantity(id,incrementBy)==null){
            return ResponseEntity.badRequest().body("something went wrong");
        }
        return ResponseEntity.ok("successfully added");

    }

    @GetMapping("/getOne")
    public ResponseEntity<Cart> getCartByProductIdAndUserId(
                                    @RequestParam("ProductId")Long productId,
                                    @RequestParam("userId") Long userId){
        try{
            Cart userCart=cartService.getByProductIdAndUserId(productId,userId);
            return ResponseEntity.ok(userCart);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


   /* @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateUser(@RequestBody @Valid Product user, @PathVariable("id") Long id) {
        Product updatedUser = service.update(user, id);
        if (updatedUser != null) {
            return ResponseEntity.ok().body(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

      /*

    @GetMapping("/total")
    public ResponseEntity<Double> getCartTotal(@RequestParam Long userId) {
        Double totalValue = cartService.calculateTotalCartValue(userId);
        return ResponseEntity.ok(totalValue);
    }
*/
}

