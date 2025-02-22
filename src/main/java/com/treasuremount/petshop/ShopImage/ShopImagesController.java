package com.treasuremount.petshop.ShopImage;

import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.Repository.VendorRepo;
import com.treasuremount.petshop.Service.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/ShopImages")
@Slf4j
public class ShopImagesController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ShopImagesService productImagesService;

    @Autowired
    private ShopImageServiceAzure productImageServiceAzure;

    @Autowired
    private VendorService vendorService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ShopImages>> uploadImage(
            @RequestParam("vendorId") Long vendorId,
            @RequestParam("positionId") Long positionId,
            @RequestParam MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        try {
            List<ShopImages> response = productImageServiceAzure.uploadImages(vendorId, positionId, file);
            UpdateVendorImage(vendorId,response.get(0).getImageUrl());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    private void UpdateVendorImage(Long vendorId,String imageUrl){

        try
        {

            Vendor vendor=vendorService.findById(vendorId);
            vendor.setImageUrl(imageUrl);
            vendorService.create(vendor);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/getAll/{vendorId}")
    public ResponseEntity<List<ShopImages>> getAllProductImages(
            @PathVariable("vendorId") Long vendorId,
            @RequestParam(value = "positionId", defaultValue = "0") Long positionId) {

        List<ShopImages> productImagesList = productImagesService.getAllProductImages(vendorId, positionId);

        if (productImagesList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productImagesList);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ShopImages>> getAllProductImages(
            @RequestParam(value = "vendorId") List<Long> vendorId,
            @RequestParam(value = "positionId", defaultValue = "0") Long positionId) {
        try {
            System.out.println("Product IDs: " + vendorId);
            List<ShopImages> productImagesList = productImagesService.filterWithProductId(vendorId, positionId);
            if(productImagesList.isEmpty()){
                return ResponseEntity.ok(new ArrayList<>());
            }
            return ResponseEntity.ok(productImagesList);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/delete/{vendorId}")
    public ResponseEntity<Map<String, String>> deleteImages(
            @PathVariable("vendorId") Long vendorId,
            @RequestParam(value = "positionId", defaultValue = "0") Long positionId) {

        try {
            productImageServiceAzure.deleteImages(vendorId, positionId);
            UpdateVendorImage(vendorId,null);
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
            // Extract the file name from the provided fileName (which contains the vendorId)
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





