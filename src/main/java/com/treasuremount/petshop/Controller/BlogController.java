package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.DTO.BlogDTO;
import com.treasuremount.petshop.Entity.Blog;
import com.treasuremount.petshop.Service.BlogService;
import com.treasuremount.petshop.Service.ImageServiceAzure;
import com.treasuremount.petshop.utils.FolderPathStrategy;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/public/blog")
public class BlogController {

    @Autowired
    private BlogService service;


 @Autowired
 private ImageServiceAzure imageServiceAzure;
/*
    @PostMapping("/add")
    public ResponseEntity<Blog> createUser(@RequestBody @Valid Blog user) {


        Blog createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }*/



    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Blog> createUser1(
            @RequestParam(value = "image", required = true) MultipartFile file,
            @RequestParam Long id,
            @RequestParam Long userId,
            @RequestParam(required = false) String imageUrl,
            @RequestParam Long categoryId,
            @RequestParam String heading,
            @RequestParam (defaultValue = "1")Boolean activeStatus,
            @RequestParam String shortDescription,
            @RequestParam String description,
            @Parameter(
                    description = "Creation Date (format: yyyy-MM-dd)",
                    example = "2025-01-24")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date createDate,

            @Parameter(
                    description = "Modified Date (format: yyyy-MM-dd)",
                    example = "2025-01-25")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDate
           ) throws IOException {

        Blog blog=new Blog();
        blog.setId(id);
        blog.setUserId(userId);
        blog.setImageUrl(imageUrl);
        blog.setCategoryId(categoryId);
        blog.setHeading(heading);
        blog.setActiveStatus(activeStatus);
        blog.setShortDescription(shortDescription);
        blog.setDescription(description);
        blog.setCreatedDate(createDate);
        blog.setModifiedDate(modifiedDate);

        Blog newBlog = service.create(blog);

        // Upload image using Lambda for folder path strategy
        String imageFileName = imageServiceAzure.createImage(newBlog.getId(), file,
                entityId -> "blogImages/" + entityId); // Lambda expression for SubCategory

        newBlog.setImageUrl((imageServiceAzure.generateServerUrl(imageFileName, "api/public/blog/serve/")));

         System.out.println("Image url is    "+ blog.getImageUrl());
        // Update SubCategory with the new image URL
         newBlog = service.update(newBlog, newBlog.getId());


        return new ResponseEntity<>(newBlog, HttpStatus.CREATED);
    }



    @PutMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Blog> uploadImage(
            @RequestParam(value = "image", required = false) MultipartFile file,
            @RequestParam Long id,
            @RequestParam Long userId,
            @RequestParam (required = false)String imageUrl,
            @RequestParam Long categoryId,
            @RequestParam String heading,
            @RequestParam Boolean activeStatus,
            @RequestParam String shortDescription,
            @RequestParam String description,
            @Parameter(
                    description = "Creation Date (format: yyyy-MM-dd)",
                    example = "2025-01-24")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date createDate,

            @Parameter(
                    description = "Modified Date (format: yyyy-MM-dd)",
                    example = "2025-01-25")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDate
    ) throws IOException {

        Blog blog=new Blog();
        blog.setId(id);
        blog.setUserId(userId);
        blog.setImageUrl(imageUrl);
        blog.setCategoryId(categoryId);
        blog.setHeading(heading);
        blog.setActiveStatus(activeStatus);
        blog.setShortDescription(shortDescription);
        blog.setDescription(description);
        blog.setCreatedDate(createDate);
        blog.setModifiedDate(modifiedDate);



        if(!(file==null || file.isEmpty())) {

        // Upload image using Lambda for folder path strategy

            FolderPathStrategy pathStrategy = entityId -> "blogImages/" + entityId;


            if (blog.getImageUrl() != null && !blog.getImageUrl().isEmpty()) {
                String oldImageFileName = blog.getImageUrl().substring(blog.getImageUrl().lastIndexOf("/") + 1);
                imageServiceAzure.deleteImage(oldImageFileName, pathStrategy, id);
            }


            String imageFileName = imageServiceAzure.createImage(blog.getId(), file,
                    pathStrategy); // Lambda expression for SubCategory

            blog.setImageUrl((imageServiceAzure.generateServerUrl(imageFileName, "api/public/blog/serve/")));


        // Update SubCategory with the new image URL
        }
        Blog responseBlog = service.update(blog, blog.getId());

        return new ResponseEntity<>(responseBlog, HttpStatus.CREATED);
    }





    @GetMapping("/getAll")
    public ResponseEntity<List<BlogDTO>> getAllUsers() {
        List<BlogDTO> users = service.getAllCategoryName();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Blog> getOneUser(@PathVariable("id") Long id) {
        Blog user = service.getOneById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getByDate/LastThreeDate")
    public List<Blog> getLastThreeBlogs(
            @RequestParam(value = "id",required = false,defaultValue = "0") Long id
    ) {
        return service.getLastThreeBlogs(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Blog> updateUser(@RequestBody @Valid Blog user, @PathVariable("id") Long id) {
        Blog updatedUser = service.update(user, id);
        if (updatedUser != null) {
            return ResponseEntity.ok().body(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        try {
            if(deleteImageById(id)){
                service.delete(id);
            }else{
                throw new Exception("Some went wrong with image Deletion");
            }

            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

   /* @DeleteMapping("/deleteImage/{id}")
    public ResponseEntity<Void> deleteSubCategoryImage(@PathVariable("id") Long imageId) {
        try {
            Blog subCategory = service.getOneById(imageId);
            if (subCategory != null && subCategory.getImageUrl() != null) {
                // Lambda to generate the folder path for subcategory
                FolderPathStrategy pathStrategy = entityId ->"blogImages/" + entityId;

                String imageFileName = subCategory.getImageUrl().substring(subCategory.getImageUrl().lastIndexOf("/") + 1);
                // Pass the path strategy for dynamic path construction
                imageServiceAzure.deleteImage(imageFileName, pathStrategy, imageId);

                // Remove the image URL from the subcategory and update it
                subCategory.setImageUrl(null);
                service.update(subCategory, imageId);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
*/

   /* @DeleteMapping("/deleteByImageUrl/{id}")
    public ResponseEntity<Void> deleteImageUrl(
            @PathVariable("id") Long id,
            @RequestParam String imageFileName) {
        try {


            FolderPathStrategy pathStrategy = entityId ->"blogImages/" + entityId;
            imageServiceAzure.deleteImage(imageFileName, pathStrategy, id);

            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }
*/

    private  Boolean deleteImageById(Long id){

        try {

            Blog subCategory = service.getOneById(id);

            if (subCategory != null && subCategory.getImageUrl() != null) {
                // Lambda to generate the folder path for subcategory
                FolderPathStrategy pathStrategy = entityId ->"blogImages/" + entityId;

                String imageFileName = subCategory.getImageUrl().substring(subCategory.getImageUrl().lastIndexOf("/") + 1);
                // Pass the path strategy for dynamic path construction
                imageServiceAzure.deleteImage(imageFileName, pathStrategy, id);

                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;

    }
    @GetMapping("/serve/{fileName}")
    public ResponseEntity<byte[]> serveSubCategoryImage(@PathVariable("fileName") String fileName) {
        try {
            String entityIdStr = fileName.split("_")[0]; // Extract subcategory ID from the filename
            Long entityId = Long.valueOf(entityIdStr);

            // Lambda for SubCategory path strategy
            FolderPathStrategy pathStrategy = entityId1 -> "blogImages/" + entityId;


            return imageServiceAzure.serveFile(fileName, pathStrategy, entityId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
