package com.treasuremount.petshop.Controller;
import com.treasuremount.petshop.Entity.Category;
import com.treasuremount.petshop.Service.CategoryService;
import com.treasuremount.petshop.Service.ImageService;
import com.treasuremount.petshop.Service.ImageServiceAzure;
import com.treasuremount.petshop.utils.FolderPathStrategy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

    @RestController
    @RequestMapping("/api/public/category")
    public class CategoryController {

        @Autowired
        private CategoryService service;
/*
        @Autowired
        private ImageService imageServiceAzure;*/


        @Autowired
        private ImageServiceAzure imageServiceAzure;

      /*  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Category> createCategory(
                @RequestParam Long id,
                @RequestParam String name,
                @RequestParam (required = false) String imageUrl,
                @RequestParam Boolean activeStatus,
                @RequestParam(value = "image") MultipartFile image) throws IOException {

            // Create category

            Category category=new Category();
            category.setId(id);
            category.setName(name);
            category.setActiveStatus(activeStatus);
            category.setImageUrl(imageUrl);
            Category createdCategory = service.create(category);

            // Upload the image for the category
            String imageFileName = imageService.createImage(createdCategory.getId(), image, "category");
            createdCategory.setImageUrl(imageService.generateServerUrl(imageFileName, "/api/public/category/serve/"));

            // Save the category with the image URL
            createdCategory = service.update(createdCategory, createdCategory.getId()); // Update category to include image URL

            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        }*/

        @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Category> createCategory(
                @RequestParam (required = false) Long id,
                @RequestParam String name,
                @RequestParam(required = false) String imageUrl,
                @RequestParam (defaultValue = "1")Boolean activeStatus,
                @RequestParam(value = "image") MultipartFile image) throws IOException {

            // Create category
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            category.setActiveStatus(activeStatus);
            category.setImageUrl(imageUrl);

            Category createdCategory = service.create(category);

            // Lambda for path strategy (category folder path)
            FolderPathStrategy pathStrategy = entityId -> "categories/" + entityId;

            // Upload the image for the category using dynamic folder path
            String imageFileName = imageServiceAzure.createImage(createdCategory.getId(), image, pathStrategy);
            createdCategory.setImageUrl(imageServiceAzure.generateServerUrl(imageFileName, "/api/public/category/serve/"));

            // Save the category with the image URL
            createdCategory = service.update(createdCategory, createdCategory.getId()); // Update category to include image URL

            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        }



        // Existing method to get all categories
        @GetMapping("/getAll")
        public ResponseEntity<List<Category>> getAllUsers() {
            List<Category> categories = service.getAll();
            if (categories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(categories);
        }

        // Existing method to get a single category by ID
        @GetMapping("/getOne/{id}")
        public ResponseEntity<Category> getOneUser(@PathVariable("id") Long id) {
            Category category = service.getOneById(id);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(category);
        }

        // Method to update an existing category
        @PutMapping("/update/{id}")
        public ResponseEntity<Category> updateUser(@RequestBody @Valid Category user, @PathVariable("id") Long id) {
            Category updatedCategory = service.update(user, id);
            if (updatedCategory != null) {
                return ResponseEntity.ok().body(updatedCategory);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        // Existing method to delete a category
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

     /*   // New method to update the image for a category
        @PutMapping(value = "/updateImage/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Category> updateCategoryImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
            // Fetch the category by ID
            Category category = service.getOneById(id);

            if (category == null) {
                // If category does not exist, return 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Check if the category already has an image URL
            if (category.getImageUrl() != null && !category.getImageUrl().isEmpty()) {
                try {
                    // Extract the old image file name from the URL
                    String oldImageFileName = category.getImageUrl().substring(category.getImageUrl().lastIndexOf("/") + 1);

                    // Delete the old image file
                    imageService.deleteImage(oldImageFileName, "category", id);
                } catch (Exception e) {
                    // Log and ignore errors during image deletion
                    e.printStackTrace();
                }
            }

            // Upload the new image
            String newImageFileName = imageService.createImage(id, image, "category");

            // Set the new image URL to the category
            category.setImageUrl(imageService.generateServerUrl(newImageFileName, "/api/public/category/serve/"));

            // Save the updated category with the new image URL
            service.update(category, id);

            return ResponseEntity.ok(category);
        }
*/

        @PutMapping(value = "/updateImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Category> updateCategoryImage( @RequestParam(required = false)  Long id,
                                                             @RequestParam String name,
                                                             @RequestParam(required = false) String imageUrl,
                                                             @RequestParam (defaultValue = "1")Boolean activeStatus,
                                                             @RequestParam(required = false) MultipartFile image) throws IOException {
            // Fetch the category by ID
            Category category =new Category();
            category.setId(id);
            category.setName(name);
            category.setImageUrl(imageUrl);
            category.setActiveStatus(activeStatus);

            if (category == null) {
                // If category does not exist, return 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }


            if(!(image==null ||image.isEmpty())) {


                // Lambda for path strategy (category folder path)
                FolderPathStrategy pathStrategy = entityId -> "categories/" + entityId;

                // Check if the category already has an image URL
                if (category.getImageUrl() != null && !category.getImageUrl().isEmpty()) {
                    try {
                        // Extract the old image file name from the URL
                        String oldImageFileName = category.getImageUrl().substring(category.getImageUrl().lastIndexOf("/") + 1);

                        // Delete the old image file using the path strategy
                        imageServiceAzure.deleteImage(oldImageFileName, pathStrategy, id);
                    } catch (Exception e) {
                        // Log and ignore errors during image deletion
                        e.printStackTrace();
                    }
                }

                // Upload the new image using dynamic folder path
                String newImageFileName = imageServiceAzure.createImage(id, image, pathStrategy);

                // Set the new image URL to the category
                category.setImageUrl(imageServiceAzure.generateServerUrl(newImageFileName, "/api/public/category/serve/"));

                // Save the updated category with the new image URL

            }
            service.update(category, id);

            return ResponseEntity.ok(category);
        }


     /*   @DeleteMapping("/deleteImage/{id}")
        public ResponseEntity<Void> deleteCategoryImage(@PathVariable Long id) {
            try {
                Category category = service.getOneById(id);
                if (category != null && category.getImageUrl() != null) {
                    String imageFileName = category.getImageUrl().substring(category.getImageUrl().lastIndexOf("/") + 1);
                    String categoryIdFromFile = imageFileName.split("_")[0];  // Extract categoryId from filename
                    imageService.deleteImage(imageFileName, "category", Long.valueOf(categoryIdFromFile));

                    // Remove the image URL from the category and update it
                    category.setImageUrl(null);
                    service.update(category, id);
                }
                return ResponseEntity.noContent().build();
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }*/

        @DeleteMapping("/deleteImage/{id}")
        public ResponseEntity<Void> deleteCategoryImage(@PathVariable Long id) {
            try {
                Category category = service.getOneById(id);
                if (category != null && category.getImageUrl() != null) {
                    String imageFileName = category.getImageUrl().substring(category.getImageUrl().lastIndexOf("/") + 1);

                    // Lambda for path strategy (category folder path)
                    FolderPathStrategy pathStrategy = entityId -> "categories/" + entityId;

                    // Delete the image file using dynamic path strategy
                    imageServiceAzure.deleteImage(imageFileName, pathStrategy, id);

                    // Remove the image URL from the category and update it
                    category.setImageUrl(null);
                    service.update(category, id);
                }
                return ResponseEntity.noContent().build();
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        private  Boolean deleteImageById(Long id){

            try {

                Category category = service.getOneById(id);
                if (category != null && category.getImageUrl() != null) {
                    String imageFileName = category.getImageUrl().substring(category.getImageUrl().lastIndexOf("/") + 1);

                    // Lambda for path strategy (category folder path)
                    FolderPathStrategy pathStrategy = entityId -> "categories/" + entityId;

                    // Delete the image file using dynamic path strategy
                    imageServiceAzure.deleteImage(imageFileName, pathStrategy, id);
                    return true;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return false;

        }



        // New method to serve the image
      /*  @GetMapping("/serve/{fileName}")
        public ResponseEntity<Resource> serveCategoryImage(@PathVariable String fileName) {
            String entityId = fileName.split("_")[0];  // Extract categoryId from the filename
            return imageService.serveFile(fileName, "category", Long.valueOf(entityId));
        }*/

        @GetMapping("/serve/{fileName}")
        public ResponseEntity<byte[]> serveCategoryImage(@PathVariable String fileName) {
            try {
                String entityIdStr = fileName.split("_")[0];  // Extract categoryId from the filename
                Long entityId = Long.valueOf(entityIdStr);

                // Lambda for path strategy (category folder path)
                FolderPathStrategy pathStrategy = entityId1 -> "categories/" + entityId1;

                // Serve the file using dynamic path strategy
                return imageServiceAzure.serveFile(fileName, pathStrategy, entityId);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }


    }







