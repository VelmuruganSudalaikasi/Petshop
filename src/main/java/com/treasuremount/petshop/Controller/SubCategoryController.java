package com.treasuremount.petshop.Controller;

import java.io.IOException;
import java.util.List;

import com.treasuremount.petshop.DTO.SubCategoryInfoDTO;
import com.treasuremount.petshop.DTO.SubCategoryInfoImageDTO;
import com.treasuremount.petshop.Entity.Category;
import com.treasuremount.petshop.Service.ImageService;
import com.treasuremount.petshop.Service.ImageServiceAzure;
import com.treasuremount.petshop.Service.SubCategoryService;
import com.treasuremount.petshop.utils.FolderPathStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import com.treasuremount.petshop.Entity.SubCategory;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/public/subCategory")
public class SubCategoryController {

    @Autowired
    private SubCategoryService service;

/*    @Autowired
    private ImageService imageServiceAzure;*/

    @Autowired
    private ImageServiceAzure imageServiceAzure;


    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SubCategory> createSubCategory(
            @RequestParam (required = false)Long id,
            @RequestParam (required = false)String name,
            @RequestParam (required = false,defaultValue = "1")Boolean activeStatus,
            @RequestParam Long categoryId,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(value = "image", required = true) MultipartFile image) throws IOException {

        // Create SubCategory object
        SubCategory subCategory = new SubCategory();
        subCategory.setId(id);
        subCategory.setName(name);
        subCategory.setActiveStatus(activeStatus);
        subCategory.setCategoryId(categoryId);
        subCategory.setImageUrl(imageUrl);

        // Create the SubCategory in the database
        SubCategory createdSubCategory = service.create(subCategory);

        // Upload image using Lambda for folder path strategy
        String imageFileName = imageServiceAzure.createImage(createdSubCategory.getId(), image,
                entityId -> "categories/" + categoryId + "/subcategories/" + entityId); // Lambda expression for SubCategory

        createdSubCategory.setImageUrl(imageServiceAzure.generateServerUrl(imageFileName, "/api/public/subCategory/serve/"));

        // Update SubCategory with the new image URL
        createdSubCategory = service.update(createdSubCategory, createdSubCategory.getId());

        return new ResponseEntity<>(createdSubCategory, HttpStatus.CREATED);
    }



    @GetMapping("/getAll")
    public ResponseEntity<List<SubCategoryInfoImageDTO>> getAllSubCategory() {
        List<SubCategoryInfoImageDTO> subCategory = service.getWithCategoryNameAll();

            return ResponseEntity.ok(subCategory);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable("id") Long id) {
        SubCategory subCategory = service.getOne(id);
        if (subCategory != null) {
            return ResponseEntity.ok(subCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@RequestBody SubCategory subCategory,
                                                         @PathVariable("id") Long id) {
        SubCategory updatedSubCategory = service.update(subCategory, id);
        if (updatedSubCategory != null) {
            return ResponseEntity.ok(updatedSubCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable("id") Long id) {
        try {
            if(deleteImageById(id)){
                service.delete(id);
            }else
            {
                throw new Exception("Some went wrong with image Deletion");
            }
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/getAllSubCategory/{categoryId}")
    public ResponseEntity<List<SubCategoryInfoImageDTO>> getSubCategoriesByCategoryId(@PathVariable(value = "categoryId") Long categoryId )
    {

        try {
            List<SubCategoryInfoImageDTO> subCategories = service.getSubCategoryByCategoryId(categoryId);
            if (subCategories.isEmpty()) {
                return ResponseEntity.ok(subCategories); // 204 No Content
            }
            return ResponseEntity.ok(subCategories); // 200 OK with the data
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }



   /* // Method to upload and associate an image with a subcategory
    @PutMapping(value = "/updateImage/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SubCategory> updateSubCategoryImage(
            @PathVariable("id") Long subCategoryId,
            @RequestParam("image") MultipartFile image) throws IOException {

        // Fetch the subcategory by ID
        SubCategory subCategory = service.getOne(subCategoryId);
        if (subCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Check and delete existing image if present
        if (subCategory.getImageUrl() != null && !subCategory.getImageUrl().isEmpty()) {
            try {
                String oldImageFileName = subCategory.getImageUrl().substring(subCategory.getImageUrl().lastIndexOf("/") + 1);
                imageService.deleteImage(oldImageFileName, "subcategory", subCategoryId);
            } catch (Exception e) {
                e.printStackTrace(); // Log and ignore errors during image deletion
            }
        }

        // Upload the new image
        String newImageFileName = imageService.createImage(subCategoryId, image, "subcategory");

        // Set the new image URL to the subcategory
        subCategory.setImageUrl(imageService.generateServerUrl(newImageFileName, "/api/public/subCategory/serve/"));

        // Save the updated subcategory with the new image URL
        SubCategory updatedSubCategory = service.update(subCategory, subCategoryId);

        return ResponseEntity.ok(updatedSubCategory);
    }
*/
    @PutMapping(value = "/updateImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SubCategory> updateSubCategoryImage(
            @RequestParam (required = false)Long id,
            @RequestParam (required = false)String name,
            @RequestParam (required = false,defaultValue = "1")Boolean activeStatus,
            @RequestParam Long categoryId,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) MultipartFile image) throws IOException {

        SubCategory subCategory =new SubCategory();
        subCategory.setId(id);
        subCategory.setName(name);
        subCategory.setActiveStatus(activeStatus);
        subCategory.setCategoryId(categoryId);
        subCategory.setImageUrl(imageUrl);
        SubCategory subCategory1=subCategory;
//        SubCategory subCategory1=subCategory;

        if (subCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(!(image==null || image.isEmpty())) {


            // Lambda for SubCategory path strategy
            FolderPathStrategy pathStrategy = entityId -> "categories/" + subCategory1.getCategoryId() + "/subcategories/" + entityId;

            // Check and delete existing image if present

            // Upload the new image using Lambda for SubCategory path
            String newImageFileName = imageServiceAzure.createImage(id, image, pathStrategy);

            // Set the new image URL to the subcategory
            subCategory.setImageUrl(imageServiceAzure.generateServerUrl(newImageFileName, "/api/public/subCategory/serve/"));

        }

        // Save the updated subcategory with the new image URL
        SubCategory updatedSubCategory = service.update(subCategory, id);

        return ResponseEntity.ok(updatedSubCategory);
    }

    /*// Method to delete an image associated with a subcategory
    @DeleteMapping("/deleteImage/{id}")
    public ResponseEntity<Void> deleteSubCategoryImage(@PathVariable("id") Long subCategoryId) {
        try {
            SubCategory subCategory = service.getOne(subCategoryId);
            if (subCategory != null && subCategory.getImageUrl() != null) {
                String imageFileName = subCategory.getImageUrl().substring(subCategory.getImageUrl().lastIndexOf("/") + 1);
                imageService.deleteImage(imageFileName, "subcategory", subCategoryId);

                // Remove the image URL from the subcategory and update it
                subCategory.setImageUrl(null);
                service.update(subCategory, subCategoryId);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/

    // Method to delete an image associated with a subcategory
    @DeleteMapping("/deleteImage/{id}")
    public ResponseEntity<Void> deleteSubCategoryImage(@PathVariable("id") Long subCategoryId) {
        try {
            SubCategory subCategory = service.getOne(subCategoryId);
            if (subCategory != null && subCategory.getImageUrl() != null) {
                // Lambda to generate the folder path for subcategory
                FolderPathStrategy pathStrategy = entityId -> "categories/" + subCategory.getCategoryId() + "/subcategories/" + entityId;

                String imageFileName = subCategory.getImageUrl().substring(subCategory.getImageUrl().lastIndexOf("/") + 1);
                // Pass the path strategy for dynamic path construction
                imageServiceAzure.deleteImage(imageFileName, pathStrategy, subCategoryId);

                // Remove the image URL from the subcategory and update it
                subCategory.setImageUrl(null);
                service.update(subCategory, subCategoryId);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private  Boolean deleteImageById(Long id){

        try {
            SubCategory subCategory = service.getOne(id);
            if (subCategory != null && subCategory.getImageUrl() != null) {
                // Lambda to generate the folder path for subcategory
                FolderPathStrategy pathStrategy = entityId -> "categories/" + subCategory.getCategoryId() + "/subcategories/" + entityId;

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


    // Method to serve an image for a subcategory
  /*  @GetMapping("/serve/{fileName}")
    public ResponseEntity<Resource> serveSubCategoryImage(@PathVariable("fileName") String fileName) {
        String entityId = fileName.split("_")[0]; // Extract subcategory ID from the filename
        return imageService.serveFile(fileName, "subcategory", Long.valueOf(entityId));
    }*/

    @GetMapping("/serve/{fileName}")
    public ResponseEntity<byte[]> serveSubCategoryImage(@PathVariable("fileName") String fileName) {
        try {

            String entityIdStr = fileName.split("_")[0]; // Extract subcategory ID from the filename
            Long entityId = Long.valueOf(entityIdStr);
            SubCategory subCategory = service.getOne(entityId);
            // Lambda for SubCategory path strategy
//            FolderPathStrategy pathStrategy = entityId -> "categories/" + subCategory.getCategoryId() + "/subcategories/" + entityId;
            FolderPathStrategy pathStrategy = entityId1 -> "categories/" +subCategory.getCategoryId() + "/subcategories/"+entityId;

            return imageServiceAzure.serveFile(fileName, pathStrategy, entityId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}