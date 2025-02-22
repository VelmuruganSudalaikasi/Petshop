package com.treasuremount.petshop.Pages.HomPage1;


import com.treasuremount.petshop.Service.ImageService;
import com.treasuremount.petshop.Service.ImageServiceAzure;
import com.treasuremount.petshop.utils.FolderPathStrategy;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/*
public class HomePage1ServiceController {
}


package com.treasuremount.petshop.Controller;

import java.io.IOException;
import java.util.List;

import com.treasuremount.petshop.DTO.SubCategoryInfoDTO;
import com.treasuremount.petshop.Service.imageServiceAzure;
import com.treasuremount.petshop.Service.SubCategoryService;
import com.treasuremount.petshop.utils.FolderPathStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import org.springframework.core.io.Resource;
import com.treasuremount.petshop.DTO.SubCategoryDTO;
import com.treasuremount.petshop.Entity.SubCategory;
import org.springframework.web.multipart.MultipartFile;
*/


@Slf4j
@RestController
@RequestMapping("/api/public/homePage1")
public class HomePage1ServiceController {

    @Autowired
    private HomePage1Service service;

    @Autowired
    private ImageServiceAzure imageServiceAzure;

/*
    @Autowired
    private ImageService imageServiceAzure;*/


    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HomePage1> createSubCategory(
            @RequestParam (required = false,defaultValue = "0") Long id,
            @RequestParam (required = false) String title,
            @RequestParam (required = false) String subtitle,
            @RequestParam (required = false)String shortDescription,
            @RequestParam (required = false) String button,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) String redirectUrl,
            @RequestParam(value = "image", required = true) MultipartFile image) throws IOException {


        HomePage1 homePage1=new HomePage1();
        homePage1.setId(id);
        homePage1.setTitle(title);
        homePage1.setSubtitle(subtitle);
        homePage1.setShortDescription(shortDescription);
        homePage1.setImageUrl(imageUrl);
        homePage1.setButton(button);
        homePage1.setButton(redirectUrl);
        // Create the SubCategory in the database
        HomePage1 savedPage = service.create(homePage1);
        final Long savedId=savedPage.getId();
        // Upload image using Lambda for folder path strategy
        String imageFileName = imageServiceAzure.createImage(savedPage.getId(), image,
                entityId -> "homePage/homePage1/" + entityId); // Lambda expression for SubCategory

        savedPage.setImageUrl(imageServiceAzure.generateServerUrl(imageFileName, "/api/public/homePage1/serve/"));

        // Update SubCategory with the new image URL
        savedPage = service.update(savedPage, savedPage.getId());

        return new ResponseEntity<>(savedPage, HttpStatus.CREATED);
    }



    @GetMapping("/getAll")
    public ResponseEntity<List<HomePage1>> getAllSubCategory() {
        List<HomePage1> subCategory = service.getAll();

        return ResponseEntity.ok(subCategory);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<HomePage1> getSubCategoryById(@PathVariable("id") Long id) {
        HomePage1 subCategory = service.getOne(id);
        if (subCategory != null) {
            return ResponseEntity.ok(subCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HomePage1> updateSubCategory(@RequestBody HomePage1 subCategory,
                                                         @PathVariable("id") Long id) {
        HomePage1 updatedSubCategory = service.update(subCategory, id);
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
            }else{
                throw new Exception("Some went wrong with image Deletion");
            }

            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
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
                 imageServiceAzure.deleteImage(oldImageFileName, "subcategory", subCategoryId);
             } catch (Exception e) {
                 e.printStackTrace(); // Log and ignore errors during image deletion
             }
         }

         // Upload the new image
         String newImageFileName = imageServiceAzure.createImage(subCategoryId, image, "subcategory");

         // Set the new image URL to the subcategory
         subCategory.setImageUrl(imageServiceAzure.generateServerUrl(newImageFileName, "/api/public/subCategory/serve/"));

         // Save the updated subcategory with the new image URL
         SubCategory updatedSubCategory = service.update(subCategory, subCategoryId);

         return ResponseEntity.ok(updatedSubCategory);
     }
 */
    @PutMapping(value = "/updateImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HomePage1> updateSubCategoryImage(
            @RequestParam (required = false) Long id,
            @RequestParam (required = false) String title,
            @RequestParam (required = false) String subtitle,
            @RequestParam (required = false)String shortDescription,
            @RequestParam (required = false) String button,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) String redirectUrl,

            @RequestParam(value = "image",required = false)  MultipartFile image) throws IOException {

        HomePage1 subCategory = new HomePage1();
        subCategory.setId(id);
        subCategory.setTitle(title);
        subCategory.setSubtitle(subtitle);
        subCategory.setShortDescription(shortDescription);
        subCategory.setButton(button);
        subCategory.setImageUrl(imageUrl);
        subCategory.setImageUrl(redirectUrl);

        if (subCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Lambda for SubCategory path strategy
//        FolderPathStrategy pathStrategy = entityId -> "categories/" + subCategory.getCategoryId() + "/subcategories/" + entityId;


        if(!( image==null || image.isEmpty() )){
            FolderPathStrategy pathStrategy =entityId -> "homePage/homePage1/" + entityId;
            // Check and delete existing image if present
            if (subCategory.getImageUrl() != null && !subCategory.getImageUrl().isEmpty()) {
                String oldImageFileName = subCategory.getImageUrl().substring(subCategory.getImageUrl().lastIndexOf("/") + 1);
                imageServiceAzure.deleteImage(oldImageFileName, pathStrategy, id);
            }

            // Upload the new image using Lambda for SubCategory path
            String newImageFileName = imageServiceAzure.createImage(id, image, pathStrategy);

            // Set the new image URL to the subcategory
            subCategory.setImageUrl(imageServiceAzure.generateServerUrl(newImageFileName, "/api/public/homePage1/serve/"));

            // Save the updated subcategory with the new image URL

        }
        subCategory = service.update(subCategory, id);


        return ResponseEntity.ok(subCategory);
    }

    /*// Method to delete an image associated with a subcategory
    @DeleteMapping("/deleteImage/{id}")
    public ResponseEntity<Void> deleteSubCategoryImage(@PathVariable("id") Long subCategoryId) {
        try {
            SubCategory subCategory = service.getOne(subCategoryId);
            if (subCategory != null && subCategory.getImageUrl() != null) {
                String imageFileName = subCategory.getImageUrl().substring(subCategory.getImageUrl().lastIndexOf("/") + 1);
                imageServiceAzure.deleteImage(imageFileName, "subcategory", subCategoryId);

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
    public ResponseEntity<Void> deleteSubCategoryImage(@PathVariable("id") Long imageId) {
        try {
            HomePage1 subCategory = service.getOne(imageId);
            if (subCategory != null && subCategory.getImageUrl() != null) {
                // Lambda to generate the folder path for subcategory
                FolderPathStrategy pathStrategy = entityId ->"homePage/homePage1/" + entityId;

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


    private  Boolean deleteImageById(Long id){

        try {

            HomePage1 subCategory = service.getOne(id);

            if (subCategory != null && subCategory.getImageUrl() != null) {
                // Lambda to generate the folder path for subcategory
                FolderPathStrategy pathStrategy = entityId ->"homePage/homePage1/" + entityId;

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
        return imageServiceAzure.serveFile(fileName, "subcategory", Long.valueOf(entityId));
    }*/

    @GetMapping("/serve/{fileName}")
    public ResponseEntity<byte[]> serveSubCategoryImage(@PathVariable("fileName") String fileName) {
        try {
            String entityIdStr = fileName.split("_")[0]; // Extract subcategory ID from the filename
            Long entityId = Long.valueOf(entityIdStr);

            // Lambda for SubCategory path strategy
            FolderPathStrategy pathStrategy = entityId1 -> "homePage/homePage1/" + entityId ;


            return imageServiceAzure.serveFile(fileName, pathStrategy, entityId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}