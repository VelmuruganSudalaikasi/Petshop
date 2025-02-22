package com.treasuremount.petshop.Testimonial;

import com.treasuremount.petshop.Service.ImageServiceAzure;
import com.treasuremount.petshop.utils.FolderPathStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/public/testimonial")
public class TestimonialController {

    @Autowired
    private TestimonialService service;

    @Autowired
    private ImageServiceAzure imageServiceAzure;

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<Testimonial> create(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(value = "rating") Double rating,
            @RequestParam(value = "activeStatus") Boolean activeStatus,
            @RequestParam(value = "createdDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date createdDate,
            @RequestParam(value = "modifiedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDate)throws IOException

    {

        Testimonial testimonial = new Testimonial();
        testimonial.setName(name);
        testimonial.setDescription(description);
        testimonial.setImageUrl(imageUrl);
        testimonial.setRating(rating);
        testimonial.setActiveStatus(activeStatus);
        testimonial.setCreatedDate(new  Date());
        testimonial.setModifiedDate(new Date());
        Testimonial createTestimonial = service.create(testimonial);


        String imageFileName = imageServiceAzure.createImage(createTestimonial.getId(), image,
                entityId -> "testimonial/" + entityId); // Lambda expression for SubCa

        testimonial.setImageUrl((imageServiceAzure.generateServerUrl(imageFileName, "/api/public/testimonial/serve/")));

        System.out.println("Image url is    "+ createTestimonial.getImageUrl());
        // Update SubCategory with the new image URL
        createTestimonial = service.update(createTestimonial, createTestimonial.getId());


        return new ResponseEntity<>(createTestimonial, HttpStatus.CREATED);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Testimonial>> getAllTestimonials() {
        List<Testimonial> testimonial = service.getAll();
        if (!testimonial.isEmpty()) {
            return ResponseEntity.ok(testimonial);
        } else {
            return ResponseEntity.noContent().build(); // 204 No Content if empty
        }
    }


    @GetMapping("/getOne/{id}")
    public ResponseEntity<Testimonial> getTestimonialById(@PathVariable("id") Long id) {
        Testimonial testimonial = service.getOneById(id);
        if (testimonial != null) {
            return ResponseEntity.ok(testimonial);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    @PutMapping("/update/{id}")
//    public ResponseEntity<Testimonial> updateTestimonial(@RequestBody @Valid Testimonial testimonial, @PathVariable("id") Long id) {
//        Testimonial updatedTestimonial = service.update(testimonial, id);
//        if (updatedTestimonial != null) {
//            return ResponseEntity.ok().body(updatedTestimonial);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PutMapping(value = "/update", consumes = "multipart/form-data")
    public ResponseEntity<Testimonial> updateTestimonial(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam (required = false)String imageUrl,// Accept image file as MultipartFile
            @RequestParam(value = "rating") Double rating,
            @RequestParam(value = "activeStatus") Boolean activeStatus,
            @RequestParam(value = "createdDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date createdDate,
            @RequestParam(value = "modifiedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDate)throws IOException
    {


        Testimonial testimonial = service.getOneById(id);
        testimonial.setId(id);
        testimonial.setName(name);
        testimonial.setDescription(description);
        testimonial.setImageUrl(imageUrl);
        testimonial.setRating(rating);
        testimonial.setActiveStatus(activeStatus);
        testimonial.setCreatedDate(testimonial.getCreatedDate());
        testimonial.setModifiedDate(new Date());


        if(!(image==null || image.isEmpty())) {

            // Upload image using Lambda for folder path strategy

            FolderPathStrategy pathStrategy = entityId -> "testimonial/" + entityId;


            if (testimonial.getImageUrl() != null && !testimonial.getImageUrl().isEmpty()) {
                String oldImageFileName = testimonial.getImageUrl().substring(testimonial.getImageUrl().lastIndexOf("/") + 1);
                imageServiceAzure.deleteImage(oldImageFileName, pathStrategy, id);
            }


            String imageFileName = imageServiceAzure.createImage(testimonial.getId(), image,
                    pathStrategy); // Lambda expression for SubCategory

            testimonial.setImageUrl((imageServiceAzure.generateServerUrl(imageFileName, "/api/public/testimonial/serve/")));


            // Update SubCategory with the new image URL
        }
         testimonial = service.update(testimonial, testimonial.getId());

        return new ResponseEntity<>(testimonial, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable("id") Long id) {
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

    @GetMapping("/serve/{fileName}")
    public ResponseEntity<byte[]> serveSubCategoryImage(@PathVariable("fileName") String fileName) {
        try {
            String entityIdStr = fileName.split("_")[0]; // Extract subcategory ID from the filename
            Long entityId = Long.valueOf(entityIdStr);

            // Lambda for SubCategory path strategy
            FolderPathStrategy pathStrategy = entityId1 -> "testimonial/" + entityId;


            return imageServiceAzure.serveFile(fileName, pathStrategy, entityId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    private  Boolean deleteImageById(Long id){

        try {

            Testimonial subCategory = service.getOneById(id);

            if (subCategory != null && subCategory.getImageUrl() != null) {
                // Lambda to generate the folder path for subcategory
                FolderPathStrategy pathStrategy = entityId -> "testimonial/" + entityId;

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

}
