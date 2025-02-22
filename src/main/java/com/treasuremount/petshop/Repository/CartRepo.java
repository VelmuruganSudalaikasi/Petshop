package com.treasuremount.petshop.Repository;

import com.treasuremount.petshop.DTO.CartItemDto;
import com.treasuremount.petshop.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;




@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    Cart findByUserIdAndProductId(Long userId, Long productId);

    @Query("SELECT c FROM Cart c WHERE c.userId = :userId AND c.id = :cartId")
    Cart findByUserIdAndCartId(Long userId, Long cartId);

    @Query("SELECT c FROM Cart c WHERE c.userId = :userId")
    List<Cart> findAllByUserId(@Param("userId") Long userId);


    @Query(value = """
        SELECT new com.treasuremount.petshop.DTO.CartItemDto(
            c.id, c.productId, c.userId, ca.name, 
            v.shopName, p.name, p.price, p.imageUrl,p.stockQuantity,c.quantity,100.00
        )
        FROM Cart c
        LEFT JOIN Product p ON c.productId = p.id
        LEFT JOIN Vendor v ON p.UserId = v.userId
        LEFT JOIN Category ca ON p.CategoryId = ca.id
        WHERE c.userId = :userId
        """)
    List<CartItemDto> custom(@Param("userId") Long userId);


    @Query(value = """
        SELECT new com.treasuremount.petshop.DTO.CartItemDto(
            c.id, c.productId, c.userId, ca.name, 
            v.shopName, p.name, p.price, p.imageUrl,p.stockQuantity,c.quantity,100.00
        )
        FROM Cart c
        LEFT JOIN Product p ON c.productId = p.id
        LEFT JOIN Vendor v ON p.UserId = v.userId
        LEFT JOIN Category ca ON p.CategoryId = ca.id
        WHERE c.id = :cartId
        """)
   CartItemDto getOneByCart(@Param("cartId")Long cartId);


}
