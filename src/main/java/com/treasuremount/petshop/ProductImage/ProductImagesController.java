package com.treasuremount.petshop.ProductImage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/public/productImages")
@Slf4j
public class ProductImagesController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ProductImagesService productImagesService;

    @Autowired
    private ProductImageServiceAzure productImageServiceAzure;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ProductImages>> uploadImage(
            @RequestParam("productId") Long productId,
            @RequestParam("positionId") Long positionId,
            @RequestParam MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        try {
            List<ProductImages> response = productImageServiceAzure.uploadImages(productId, positionId, file);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    @GetMapping("/getAll/{productId}")
    public ResponseEntity<List<ProductImages>> getAllProductImages(
            @PathVariable("productId") Long productId,
            @RequestParam(value = "positionId", defaultValue = "0") Long positionId) {

        List<ProductImages> productImagesList = productImagesService.getAllProductImages(productId, positionId);

        if (productImagesList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productImagesList);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductImages>> getAllProductImages(
            @RequestParam(value = "productId") List<Long> productId,
            @RequestParam(value = "positionId", defaultValue = "0") Long positionId) {
        try {
            System.out.println("Product IDs: " + productId);
            List<ProductImages> productImagesList = productImagesService.filterWithProductId(productId, positionId);

            return ResponseEntity.ok(productImagesList);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Map<String, String>> deleteImages(
            @PathVariable("productId") Long productId,
            @RequestParam(value = "positionId", defaultValue = "0") Long positionId) {

        try {
            productImageServiceAzure.deleteImages(productId, positionId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Images deleted successfully"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Failed to delete images"));
        }
    }
/*

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        try {
            // Extract the file name from the provided fileName (which contains the productId)
            String[] parts = fileName.split("_");
            Path filePath = Paths.get(uploadDir, "product_images",parts[0]).toAbsolutePath().normalize()
                    .resolve(fileName);  // Directly use the file name passed in the URL
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header("Content-Disposition", "inline; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
*/

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String fileName) {
        try {
            return productImageServiceAzure.serveFile(fileName);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


}





