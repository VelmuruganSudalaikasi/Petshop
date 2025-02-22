/*
package com.treasuremount.petshop.test.controller;

import com.treasuremount.petshop.test.service.AzureBlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class BlobStorageController {

    @Autowired
    private AzureBlobStorageService blobStorageService;

    // Upload an image to Azure Blob Storage
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            blobStorageService.uploadFile(file);
            return ResponseEntity.ok("Image uploaded successfully with name: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        }
    }

    // Serve an image from the private container
    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        try {
            byte[] fileData = blobStorageService.downloadFile(fileName);

            // Determine content type based on file extension (example: JPEG)
            MediaType contentType = MediaType.IMAGE_JPEG;
            if (fileName.endsWith(".png")) {
                contentType = MediaType.IMAGE_PNG;
            } else if (fileName.endsWith(".gif")) {
                contentType = MediaType.IMAGE_GIF;
            }

            // Return the image data as a ResponseEntity
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .contentType(contentType)
                    .body(fileData);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
*/
