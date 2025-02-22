package com.treasuremount.petshop.ProductImage;

import com.treasuremount.petshop.Documents.Certificate.CertificateServiceWithAzure;
import com.treasuremount.petshop.Service.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductImagesService {


    private static final Logger logger = LoggerFactory.getLogger(CertificateServiceWithAzure.class);

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ProductImages create(ProductImages productImage) {
        return productImagesRepository.save(productImage);
    }

    public List<ProductImages> getAllProductImages(Long productId, Long positionId) {
        if (positionId == 0L) {
            return productImagesRepository.findByProductId(productId);
        } else {
            return productImagesRepository.findByProductIdAndPositionId(productId, positionId);
        }
    }

    public List<ProductImages> filterWithProductId(List<Long> productIds,Long positionId){
        return productImagesRepository.findByProductIdLst(productIds,positionId);
    }

    public ProductImages getProductImageById(Long id) {
        return productImagesRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        productImagesRepository.deleteById(id);
    }

    public void deleteImages(Long productId, Long positionId) {
        if (positionId == 0L) {
            productImagesRepository.deleteByProductId(productId);
        } else {
            List<ProductImages> images = productImagesRepository.findByProductIdAndPositionId(productId, positionId);
            for (ProductImages image : images) {
                deleteImageFile(image.getImageUrl());
                productImagesRepository.delete(image);
            }
        }
    }
    public List<ProductImages> uploadImages(Long productId, Long positionId, MultipartFile file) throws IOException {
        // Define the upload directory
        Path uploadPath = Paths.get(uploadDir, "product_images", String.valueOf(productId)).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Validate file
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFileName.contains("..")) {
            throw new IllegalArgumentException("Invalid file path: " + originalFileName);
        }

        // Generate unique filename with productId included
        String uniqueFileName = productId + "_" + UUID.randomUUID().toString() + "_" + originalFileName;

        // Create path for saving the new file
        Path filePath = uploadPath.resolve(uniqueFileName);
        file.transferTo(filePath);

        // Generate the URL for the new file
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/public/productImages/files/")
                .path(uniqueFileName)
                .toUriString();

        // Check if there's an existing image with the same positionId for the given productId
        List<ProductImages> existingImages = productImagesRepository.findByProductIdAndPositionId(productId, positionId);

        if (!existingImages.isEmpty()) {
            // If an image exists, delete the old image from the filesystem and update the record
            ProductImages existingImage = existingImages.get(0);
            deleteImageFile(existingImage.getImageUrl()); // Delete the old image
            existingImage.setImageUrl(fileDownloadUri);  // Update the image URL with the new file URL
            productImagesRepository.save(existingImage); // Save the updated image record
        } else {
            // If no image exists, create a new record
            ProductImages productImage = new ProductImages();
            productImage.setProductId(productId);
            productImage.setPositionId(positionId);
            productImage.setImageUrl(fileDownloadUri);
            productImage.setActiveStatus(true);

            productImagesRepository.save(productImage); // Save the new image record
        }

        // Return the updated list of images for this productId and positionId
        return productImagesRepository.findByProductIdAndPositionId(productId, positionId);
    }



    private void deleteImageFile(String imageUrl) {
        try {
            // Extract the filename from the imageUrl
            String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1); // Extracts "12345_abc123_image.jpg" from the URL
            String[] parts = fileName.split("_");

            // The first part is the productId (convert it to Long)


            // Construct the file path using the extracted filename
            Path filePath = Paths.get(uploadDir, "product_images",parts[0]).toAbsolutePath().normalize()
                    .resolve(fileName); // Resolve the file using the filename

            // Delete the file if it exists
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            log.error("Failed to delete file: {}", imageUrl, ex);
        }
    }

    public ResponseEntity<Resource> serveFile(String fileName) {
        try {
            // Extract productId from the filename (assuming it starts with productId_)
            String productIdStr = fileName.split("_")[0];
            Long productId = Long.parseLong(productIdStr);

            // Build the correct file path using the productId and fileName
            Path filePath = Paths.get(uploadDir).resolve("product_images/" + productId +"/").resolve(fileName).normalize();

            // Log the path to ensure it's correct
            logger.info("Trying to serve file from: {}", filePath);

            Resource resource = new UrlResource(filePath.toUri());
//resource.getFilename()
            // Check if file exists and is readable
            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header("Content-Disposition", "inline; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                // If file is not found or unreadable
                logger.error("File not found or unreadable: {}", fileName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            // Log and return an error if an exception occurs
            logger.error("Error serving file: {}", fileName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
