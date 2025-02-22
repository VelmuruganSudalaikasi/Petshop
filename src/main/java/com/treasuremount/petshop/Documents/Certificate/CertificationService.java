package com.treasuremount.petshop.Documents.Certificate;
import com.treasuremount.petshop.Service.PetService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;



//note every file name is unique we want to query the db and with id then only we can erase the previous one
@Service
public class CertificationService {

    private static final Logger logger = LoggerFactory.getLogger(CertificationService.class);

    @Autowired
    private CertificationRepo repo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    PetService petService;

    // Upload Functionality - Handles new file upload, replace if already exists
    public Certificates upload(Long productId, Long documentId, MultipartFile file) throws IOException {
        logger.info("Uploading certificate for productId: {} and documentId: {}", productId, documentId);

        // Check if the product is a pet
        if (petService.get1ByProductId(productId) == null) {
            logger.error("Invalid Product: ProductId {} is not a pet", productId);
            throw new RuntimeException("Invalid Product: Not a pet.");
        }

        // Update tempUpload to include 'product_images' directory
        String tempUpload = uploadDir + "/product_images/" + productId + "/Documents/Certificates";

        // Find the existing certificate for the given productId and documentId
        Certificates response = repo.findByProductIdAndDocumentId(productId, documentId);
        if (response == null) {
            response = new Certificates();
            response.setProductId(productId);
            response.setDocumentId(documentId);
            logger.info("No existing certificate found for productId: {} and documentId: {}, creating new one.", productId, documentId);
        }

        // Handle file upload (replace old file if it exists)
        if (file != null) {
            if (response.getImageUrl() != null) {
                logger.info("Deleting old certificate file for productId: {} and documentId: {}", productId, documentId);
                deleteFile(response.getImageUrl()); // Delete old file if it exists
            }
            logger.info("Saving new certificate file for productId: {} and documentId: {}", productId, documentId);
            response.setImageUrl(createImage(productId, file, tempUpload));
        }

        try {
            Certificates savedCert = repo.save(response);
            logger.info("Successfully saved certificate for productId: {} and documentId: {}", productId, documentId);
            return savedCert;
        } catch (Exception e) {
            logger.error("Error saving certificate for productId: {} and documentId: {}", productId, documentId, e);
            throw new RuntimeException("Error saving certificate", e);
        }
    }









    // Method to create and store the file on disk
    private String createImage(Long productId, MultipartFile file, String path) throws IOException {
        if (file.isEmpty()) {
            logger.warn("Received empty file for productId: {}", productId);
            return null;
        }

        Path uploadPath = Paths.get(path).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            logger.info("Created directories for file upload at path: {}", uploadPath);
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFileName.contains("..")) {
            logger.error("Invalid file path detected: {}", originalFileName);
            throw new IllegalArgumentException("Invalid file path: " + originalFileName);
        }

        String uniqueFileName = productId + "_" + UUID.randomUUID() + "_" + originalFileName;
        Path filePath = uploadPath.resolve(uniqueFileName);
        file.transferTo(filePath);

        logger.info("File uploaded successfully for productId: {} with unique name: {}", productId, uniqueFileName);
        return generateServerUrl(uniqueFileName);
    }

    // Generate the URL for the stored image
    private String generateServerUrl(String uniqueFileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/public/resource/certification/files/")
                .path(uniqueFileName)
                .toUriString();
    }

    // Delete the file from memory
    public void deleteFile(String imageUrl) {
        try {
            logger.info("Attempting to delete file: {}", imageUrl);
            String decodedUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8);
            String fileName = Paths.get(new java.net.URL(decodedUrl).getPath()).getFileName().toString();
            String productId = fileName.split("_")[0]; // Assuming productId is part of the filename
            String subDir = "product_images/" + productId + "/Documents/Certificates"; // Updated path to include product_images

            Path filePath = Paths.get(uploadDir, subDir, fileName).normalize();
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                logger.info("Deleted file: {}", filePath);
            } else {
                logger.warn("File not found: {}", filePath);
            }
        } catch (IOException ex) {
            logger.error("Error deleting file: {}", imageUrl, ex);
            throw new RuntimeException("Could not delete file: " + imageUrl, ex);
        }
    }

    public List<Certificates> getAll(Long productId, Long documentId) {
        logger.info("Fetching certificates for productId: {} and documentId: {}", productId, documentId);
        if (documentId == 0) {
            List<Certificates> certificates = repo.findByProductId(productId);
            if (certificates.isEmpty()) {
                logger.warn("No certificates found for productId: {}", productId);
            }
            return certificates;
        }
        Certificates certificate = repo.findByProductIdAndDocumentId(productId, documentId);
        if (certificate == null) {
            logger.warn("No certificate found for productId: {} and documentId: {}", productId, documentId);
        }
        return certificate != null ? List.of(certificate) : List.of();
    }

    // Delete certificates based on productId and documentId
    public void delete(Long productId, Long documentId) {
        logger.info("Deleting certificates for productId: {} and documentId: {}", productId, documentId);
        if (documentId == 0) {
            // Delete all certificates for the product
            List<Certificates> certs = repo.findByProductId(productId);
            for (Certificates cert : certs) {
                deleteFile(cert.getImageUrl());
            }
            repo.deleteByProductId(productId);
            logger.info("Deleted all certificates for productId: {}", productId);
        } else {
            // Delete specific certificate
            Certificates cert = repo.findByProductIdAndDocumentId(productId, documentId);
            if (cert != null) {
                deleteFile(cert.getImageUrl());
                repo.delete(cert);
                logger.info("Deleted certificate for productId: {} and documentId: {}", productId, documentId);
            } else {
                logger.warn("Certificate not found for productId: {} and documentId: {}", productId, documentId);
            }
        }
    }

    public ResponseEntity<Resource> serveFile(String fileName) {
        try {
            // Extract productId from the filename (assuming it starts with productId_)
            String productIdStr = fileName.split("_")[0];
            Long productId = Long.parseLong(productIdStr);

            // Build the correct file path using the productId and fileName
            Path filePath = Paths.get(uploadDir).resolve("product_images/" + productId + "/Documents/Certificates").resolve(fileName).normalize();

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
