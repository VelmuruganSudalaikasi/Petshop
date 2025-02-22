package com.treasuremount.petshop.Repository;



import com.treasuremount.petshop.DTO.BlogDTO;
import com.treasuremount.petshop.Entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlogRepo extends JpaRepository<Blog,Long> {

    @Query(value = """
    SELECT * FROM blog b 
    WHERE b.id <> :id OR :id = 0 
    ORDER BY 
        CASE 
            WHEN :id = 0 THEN b.id  -- If id is 0, sort by id (recent blogs)
            ELSE (b.category_id = (SELECT category_id FROM blog WHERE id = :id)) 
        END DESC, 
        b.category_id, 
        b.id DESC 
    LIMIT 3
    """, nativeQuery = true)
    List<Blog> findLastThreeBlogs(@Param("id") Long id);





    @Query("SELECT new com.treasuremount.petshop.DTO.BlogDTO( " +
            "b.id, b.userId,b.imageUrl, b.CategoryId, c.name, b.heading, b.activeStatus, " +
            "b.shortDescription, b.Description, b.createdDate, b.modifiedDate) " +
            "FROM Blog b " +
            "LEFT JOIN Category c ON b.CategoryId = c.id")
    List<BlogDTO> findAllBlogsWithCategoryName();


}
