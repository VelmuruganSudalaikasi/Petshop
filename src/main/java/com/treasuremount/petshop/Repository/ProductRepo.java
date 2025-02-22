package com.treasuremount.petshop.Repository;



import com.treasuremount.petshop.Controller.ProductController;
import com.treasuremount.petshop.DTO.ProductDTO;
import com.treasuremount.petshop.Entity.Inventory;
import com.treasuremount.petshop.Entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "SELECT " +
            "p.id AS product_id, " +
            "u.id AS user_id, " +
            "u.first_name AS user_name, " +
            "v.shop_name AS shop_name, " +
            "p.name AS product_name, " +
            "p.active_status AS is_approved " +
            "FROM product p " +
            "LEFT JOIN user u ON p.user_id = u.id " +
            "LEFT JOIN vendor v ON u.id = v.user_id",
            nativeQuery = true)
    List<Object[]> fetchApprovalData();

    @Query(value = "SELECT " +
            "p.id AS product_id, " +
            "u.id AS user_id, " +
            "u.first_name AS user_name, " +
            "v.shop_name AS shop_name, " +
            "p.name AS product_name, " +
            "p.active_status AS is_approved " +
            "FROM product p " +
            "LEFT JOIN user u ON p.user_id = u.id " +
            "LEFT JOIN vendor v ON u.id = v.user_id where p.id =:ProductId ",
            nativeQuery = true)
    List<Object[]> fetchApprovalDataById(@Param("ProductId") Long id);



    @Modifying
    @Query(value = """
    UPDATE product 
    SET is_approved = :isApproved 
    WHERE id = :productId
    """, nativeQuery = true)
    void updateProductApprovalStatus(@Param("productId") Long productId, @Param("isApproved") Boolean isApproved);



    @Modifying
    @Query(value = """
    UPDATE product as p
    SET p.image_url = :image
    WHERE p.id = :productId
    """, nativeQuery = true)
    void updateImage(@Param("productId") Long productId, @Param("image") String image);




    @Query(value = "  SELECT \n" +
            "    p.id AS id, \n" +
            "    p.name AS productName, \n" +
            "    p.image_url AS imageUrl, \n" +
            "    p.price AS price, \n" +
            "    p.active_status AS activeStatus, \n" +
            "    p.created_date AS createdDate, \n" +
            "    p.updated_date AS updatedDate, \n" +
            "    p.return_within AS returnWithin, \n" +
            "    p.user_id AS userId, \n" +
            "    p.category_id AS categoryId, \n" +
            "    p.sub_category_id AS subCategoryId, \n" +
            "    p.discount AS discount, \n" +
            "    p.min_stock_level AS minStockLevel, \n" +
            "    c.name AS categoryName, \n" +
            "    sc.name AS subCategoryName, \n" +
            "    v.shop_name AS shopName, \n" +
            "    u.first_name AS userName,\n" +
            "    ps.id AS productStatusId,\n" +
            "    ps.name AS ProductStatusName,\n" +
            "    p.stock_quantity AS StockQuantity,\n" +
            "    p.video_url AS videoUrl\n" +
            "FROM product p\n" +
            "JOIN category c ON p.category_id = c.id\n" +
            "JOIN product_status ps ON p.product_status_id = ps.id\n"+
            "JOIN sub_category sc ON p.sub_category_id = sc.id\n" +
            "LEFT JOIN vendor v ON p.user_id = v.user_id\n" +
            "LEFT JOIN user u ON p.user_id = u.id\n" +
            "WHERE \n" +
            "    ((NULLIF(:userId,0) IS NULL) OR p.user_id = :userId) \n" +
            "    AND ((:categoryIdCheck =1 )  OR (p.category_id IN (:categoryId))) \n" +
            "    AND ((:subCategoryIdCheck =1) OR (p.sub_category_id IN (:subCategoryId)))\n" +
            "    AND ((NULLIF(:minPrice,0) IS NULL) OR (p.price >= :minPrice)) \n" +
            "    AND ((NULLIF(:maxPrice,0)  IS NULL) OR (p.price <= :maxPrice))\n" +
            "    AND ((:ProductStatusIdCheck=1)  OR (p.product_status_id IN (:ProductStatusId)))\n"+
            "     AND ((:IsAdmin = 1) OR  (p.product_status_id  NOT IN (1, 3, 4)));",
            nativeQuery = true)
    List<Object[]> test(
            @Param("userId") Long userId,
            @Param("categoryIdCheck") Boolean categoryIdCheck,
            @Param("categoryId") List<Long> categoryId,
            @Param("subCategoryIdCheck") Boolean subCategoryIdCheck,
            @Param("subCategoryId") List<Long> subCategoryId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("ProductStatusIdCheck") Boolean ProductStatusIdCheck,
            @Param("ProductStatusId") List<Long> statusId,
            @Param("IsAdmin") Boolean isAdmin

    );




    @Query(value = """
            SELECT DISTINCT
                p.id AS id,\s
                p.name AS productName,\s
                p.image_url AS imageUrl,\s
                p.price AS price,\s
                p.active_status AS activeStatus,\s
                p.created_date AS createdDate,\s
                p.updated_date AS updatedDate,\s
                p.return_within AS returnWithin,\s
                p.user_id AS userId,\s
                p.category_id AS categoryId,\s
                p.sub_category_id AS subCategoryId,\s
                p.discount AS discount,\s
                p.min_stock_level AS minStockLevel,\s
                c.name AS categoryName,\s
                sc.name AS subCategoryName,\s
                v.shop_name AS shopName,\s
                u.first_name AS userName,
                ps.id AS productStatusId,
                ps.name AS ProductStatusName,
                p.stock_quantity AS StockQuantity,
                p.video_url AS videoUrl
            FROM product p
            JOIN category c ON p.category_id = c.id
            JOIN product_status ps ON p.product_status_id = ps.id
            JOIN sub_category sc ON p.sub_category_id = sc.id
            LEFT JOIN vendor v ON p.user_id = v.user_id
            LEFT JOIN user u ON p.user_id = u.id
            LEFT JOIN pets pet ON pet.product_id = p.id
            LEFT JOIN pet_details pd ON pd.pet_id = pet.id
            LEFT JOIN foods f ON f.product_id = p.id
            LEFT JOIN food_details fd ON fd.foods_id = f.id
            LEFT JOIN accessories acc ON acc.product_id = p.id
            LEFT JOIN accessory_details ad ON ad.accessories_id = acc.id
            WHERE\s
                (:userId = 0 OR p.user_id = :userId)\s
                AND (:categoryId = 0 OR p.category_id = :categoryId)\s
                AND (:subCategoryId = 0 OR p.sub_category_id = :subCategoryId)
                AND (:minPrice = 0 OR p.price >= :minPrice)\s
                AND (:maxPrice = 0 OR p.price <= :maxPrice)
                AND (:ProductStatusId = 0 OR p.product_status_id = :ProductStatusId)
                AND (:IsAdmin = 1 OR p.product_status_id NOT IN (1, 3, 4))
                AND (
                    :searchWord = ''\s
                    OR LOWER(p.name) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(c.name) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(sc.name) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(ps.name) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    -- Pet related searches
                    OR LOWER(pet.breed) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(pet.color) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(pd.about) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(pd.health_info) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(pd.care_instructions) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    -- Food related searches
                    OR LOWER(f.brand) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(fd.ingredients) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(fd.nutritional_info) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(fd.feeding_guidelines) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    -- Accessory related searches
                    OR LOWER(acc.brand) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(acc.color) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(ad.specifications) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(ad.usage_instructions) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(ad.care_instructions) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                )
            """,
            nativeQuery = true)
    List<Object[]> filterWithUserIdCategoryIdSubCategoryId(
            @Param("userId") Long userId,
            @Param("categoryId") Long categoryId,
            @Param("subCategoryId") Long subCategoryId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("ProductStatusId") Long statusId,
            @Param("IsAdmin") Boolean isAdmin,
            @Param("searchWord") String searchWord


    );

    @Query(value = """
    SELECT DISTINCT
        p.id AS id,
        p.name AS productName,
        pf.image_url AS imageUrl,
        p.price AS price,
        p.active_status AS activeStatus,
        p.created_date AS createdDate,
        p.updated_date AS updatedDate,
        p.return_within AS returnWithin,
        p.user_id AS userId,
        p.category_id AS categoryId,
        p.sub_category_id AS subCategoryId,
        p.discount AS discount,
        p.min_stock_level AS minStockLevel,
        c.name AS categoryName,
        sc.name AS subCategoryName,
        v.shop_name AS shopName,
        u.first_name AS userName,
        ps.id AS productStatusId,
        ps.name AS ProductStatusName,
        p.stock_quantity AS StockQuantity,
        p.video_url AS videoUrl
    FROM product p
    JOIN category c ON p.category_id = c.id
    JOIN product_status ps ON p.product_status_id = ps.id
    JOIN sub_category sc ON p.sub_category_id = sc.id
    LEFT JOIN vendor v ON p.user_id = v.user_id
    LEFT JOIN user u ON p.user_id = u.id
    LEFT JOIN pets pet ON pet.product_id = p.id
    LEFT JOIN pet_details pd ON pd.pet_id = pet.id
    LEFT JOIN foods f ON f.product_id = p.id
    LEFT JOIN food_details fd ON fd.foods_id = f.id
    LEFT JOIN accessories acc ON acc.product_id = p.id
    LEFT JOIN accessory_details ad ON ad.accessories_id = acc.id
    LEFT JOIN product_images pf ON pf.product_id = p.id AND  position_id=1  
    WHERE (:categoryId = 0 OR p.category_id = :categoryId)
    AND p.id !=  :productId
    LIMIT 4
    """, nativeQuery = true)
    List<Object[]> findRelevantProduct(@Param("productId") Long productId,@Param("categoryId") Long categoryId);




    @Query(value = """
    SELECT DISTINCT
        p.id AS id,
        p.name AS productName,
        im.image_url AS imageUrl,
        p.price AS price,
        p.active_status AS activeStatus,
        p.created_date AS createdDate,
        p.updated_date AS updatedDate,
        p.return_within AS returnWithin,
        p.user_id AS userId,
        p.category_id AS categoryId,
        p.sub_category_id AS subCategoryId,
        p.discount AS discount,
        p.min_stock_level AS minStockLevel,
        c.name AS categoryName,
        sc.name AS subCategoryName,
        v.shop_name AS shopName,
        u.first_name AS userName,
        ps.id AS productStatusId,
        ps.name AS ProductStatusName,
        p.stock_quantity AS StockQuantity,
        p.video_url AS videoUrl
    FROM product p
    JOIN category c ON p.category_id = c.id
    JOIN product_status ps ON p.product_status_id = ps.id
    JOIN sub_category sc ON p.sub_category_id = sc.id
    LEFT JOIN vendor v ON p.user_id = v.user_id
    LEFT JOIN user u ON p.user_id = u.id
    LEFT JOIN pets pet ON pet.product_id = p.id
    LEFT JOIN pet_details pd ON pd.pet_id = pet.id
    LEFT JOIN foods f ON f.product_id = p.id
    LEFT JOIN food_details fd ON fd.foods_id = f.id
    LEFT JOIN accessories acc ON acc.product_id = p.id
    LEFT JOIN accessory_details ad ON ad.accessories_id = acc.id
    LEFT JOIN product_images im ON p.id = im.product_id AND im.position_id = 1
    WHERE p.product_status_id NOT IN (1, 3, 4)
    ORDER BY p.id DESC
    LIMIT :limit
    """, nativeQuery = true)
    List<Object[]> findLatestProducts(@Param("limit") Long limit);


    @Query(value = """
            SELECT DISTINCT
                                                  p.id AS id,
                                                  p.name AS productName,
                                                  im.image_url AS imageUrl,
                                                  p.price AS price,
                                                  p.active_status AS activeStatus,
                                                  p.created_date AS createdDate,
                                                  p.updated_date AS updatedDate,
                                                  p.return_within AS returnWithin,
                                                  p.user_id AS userId,
                                                  p.category_id AS categoryId,
                                                  p.sub_category_id AS subCategoryId,
                                                  p.discount AS discount,
                                                  p.min_stock_level AS minStockLevel,
                                                  c.name AS categoryName,
                                                  sc.name AS subCategoryName,
                                                  v.shop_name AS shopName,
                                                  u.first_name AS userName,
                                                  ps.id AS productStatusId,
                                                  ps.name AS productStatusName,
                                                  p.stock_quantity AS stockQuantity,
                                                  p.video_url AS videoUrl,
                                                  COALESCE(rating.ord, 0) AS orderCount
                                              FROM\s
                                                  product p
                                              JOIN category c ON p.category_id = c.id
                                              JOIN product_status ps ON p.product_status_id = ps.id
                                              JOIN sub_category sc ON p.sub_category_id = sc.id
                                              LEFT JOIN vendor v ON p.user_id = v.user_id
                                              LEFT JOIN user u ON p.user_id = u.id
                                              LEFT JOIN (
                                                  SELECT\s
                                                      product_id,
                                                      COUNT(*) AS ord
                                                  FROM\s
                                                      orders
                                                  GROUP BY\s
                                                      product_id
                                              ) AS rating ON rating.product_id = p.id
                                              LEFT JOIN product_images im ON p.id = im.product_id AND im.position_id = 1
                                              WHERE p.product_status_id NOT IN (1, 3, 4)
                                              ORDER BY\s
                                                  orderCount DESC
                                              LIMIT :limit
    """, nativeQuery = true)
    List<Object[]> findByTopRatingProducts(@Param("limit") Long limit);

    @Query(value = """
    SELECT DISTINCT
        p.id AS id,
        p.name AS productName,
        im.image_url AS imageUrl,
        p.price AS price,
        p.active_status AS activeStatus,
        p.created_date AS createdDate,
        p.updated_date AS updatedDate,
        p.return_within AS returnWithin,
        p.user_id AS userId,
        p.category_id AS categoryId,
        p.sub_category_id AS subCategoryId,
        p.discount AS discount,
        p.min_stock_level AS minStockLevel,
        c.name AS categoryName,
        sc.name AS subCategoryName,
        v.shop_name AS shopName,
        u.first_name AS userName,
        ps.id AS productStatusId,
        ps.name AS ProductStatusName,
        p.stock_quantity AS StockQuantity,
        p.video_url AS videoUrl
    FROM product p
    JOIN category c ON p.category_id = c.id
    JOIN product_status ps ON p.product_status_id = ps.id
    JOIN sub_category sc ON p.sub_category_id = sc.id
    LEFT JOIN vendor v ON p.user_id = v.user_id
    LEFT JOIN user u ON p.user_id = u.id
    LEFT JOIN pets pet ON pet.product_id = p.id
    LEFT JOIN pet_details pd ON pd.pet_id = pet.id
    LEFT JOIN foods f ON f.product_id = p.id
    LEFT JOIN food_details fd ON fd.foods_id = f.id
    LEFT JOIN accessories acc ON acc.product_id = p.id
    LEFT JOIN accessory_details ad ON ad.accessories_id = acc.id
     LEFT JOIN
                            orders rw ON p.id = rw.product_id
                        LEFT JOIN
                             (SELECT count(rw.product_id) AS ord,
                                         rw.product_id
                                FROM
                                orders rw
                                GROUP BY
                                rw.product_id
                              ) AS rating ON rating.product_id = p.id
                         LEFT JOIN product_images im ON p.id = im.product_id AND im.position_id = 1
                       WHERE  p.product_status_id NOT IN (1, 3, 4)
                         Order by rating.ord DESC
                        LIMIT :limit;


 """, nativeQuery = true)

    List<Object[]> findByBestSelling(@Param("limit") Long limit);


    @Modifying
    @Query(value = """
    UPDATE product as p
    SET p.product_status_id = :ProductStatusId
    WHERE p.id = :productId
    """, nativeQuery = true)
    void updateProductStatus(@Param("productId") Long productId, @Param("ProductStatusId") Long ProductStatusId);
/*
*  Admin means 1
*  site means 0
* */




}


