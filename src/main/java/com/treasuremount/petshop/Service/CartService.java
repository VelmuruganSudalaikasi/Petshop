package com.treasuremount.petshop.Service;

import com.treasuremount.petshop.DTO.CartItemDto;
import com.treasuremount.petshop.Entity.Blog;
import com.treasuremount.petshop.Entity.Cart;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Repository.CartRepo;
import com.treasuremount.petshop.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepository;

    @Autowired
    private ProductRepo productRepo;

    @Transactional
    public Cart addToCart(Long userId, Long productId,Long quantity) {
        // Check if the product already exists in the user's cart
        Cart existingCartItem = cartRepository.findByUserIdAndProductId(userId, productId);

        if (existingCartItem != null) {
            // If product already exists in cart, throw an exception
            throw new RuntimeException("Product already exists in cart. Cannot add duplicate product.");
        }

        // If product doesn't exist in cart, create new cart item
        Cart newCartItem = new Cart();
        newCartItem.setUserId(userId);
        newCartItem.setProductId(productId);
        newCartItem.setQuantity(quantity);

        return cartRepository.save(newCartItem);
    }

    @Transactional
    public void removeFromCart(Long userId, Long cartId) {
        Cart cartItem = cartRepository.findByUserIdAndCartId(userId, cartId);
        if (cartItem != null) {
            cartRepository.delete(cartItem);
        }
    }


    public  CartItemDto getOneById(Long id) {
        try {
            return cartRepository.getOneByCart(id);

        } catch (Exception ex) {
            return null;
        }
    }

    public String increaseQuantity(Long id,Long increment){
        return cartRepository.findById(id)
                .map(cart -> {

                    cart.setQuantity(


                            increment);
                    cartRepository.save(cart);
                    return ("Quantity updated successfully");
                })
                .orElse(null);
    }

     /*   return ResponseEntity.ok("Quantity updated successfully");

            .orElse(ResponseEntity.notFound().build());*/



  /*  @Transactional(readOnly = true)
    public List<CartItemDto> getAllCartItemsByUserId(Long userId) {
        // Retrieve all cart items for the user
        List<Cart> cartItems = cartRepository.findAllByUserId(userId);

        // Transform cart items to DTOs with product details
        return cartItems.stream()
                .map(cartItem -> {
                    // Fetch the full product details
                    Product product = productRepo.findById(cartItem.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    System.err.println("productFetch from cart"+ product);

                    // Create DTO with cart and product details
                    return new CartItemDto(
                            cartItem.getId(),
                            product.getId(),
                            cartItem.getUserId(),
                            product.getCategory().getName(),
                            "",
                            product.getName(),
                            product.getPrice(),
                            product.getImageUrl(),
                            product.getStockQuantity(),
                            cartItem.getQuantity()
                    );
                })
                .collect(Collectors.toList());
    }*/

    @Transactional(readOnly = true)
    public List<CartItemDto> getAllCartItemsByUserIdByQuery(Long userId) {
        return cartRepository.custom(userId);
    }

    public boolean isProductInCart(Long userId, Long productId) {
        return cartRepository.findByUserIdAndProductId(userId, productId) != null;
    }

    public Cart getByProductIdAndUserId(Long productId,Long userId){
        return cartRepository.findByUserIdAndProductId(userId,productId);
    }



}
