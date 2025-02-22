package com.treasuremount.petshop.Documents.Certificate;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.treasuremount.petshop.Service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class CertificateServiceWithAzure {

    private static final Logger logger = LoggerFactory.getLogger(CertificateServiceWithAzure.class);

    @Autowired
    private CertificationRepo repo;

    @Autowired
    private PetService petService;

    private final BlobContainerClient blobContainerClient;

    @Autowired
    public CertificateServiceWithAzure(BlobContainerClient blobContainerClient) {
        this.blobContainerClient = blobContainerClient;
    }

    public Certificates upload(Long productId, Long documentId, MultipartFile file) throws IOException {
        logger.info("Uploading certificate for productId: {} and documentId: {}", productId, documentId);

        // Check if the product is a pet
        if (petService.get1ByProductId(productId) == null) {
            logger.error("Invalid Product: ProductId {} is not a pet", productId);
            throw new RuntimeException("Invalid Product: Not a pet.");
        }

        // Directory for certificates
        String tempUpload = "product_images/" + productId + "/Documents/Certificates";

        // Find the existing certificate
        Certificates response = repo.findByProductIdAndDocumentId(productId, documentId);
        if (response == null) {
            response = new Certificates();
            response.setProductId(productId);
            response.setDocumentId(documentId);
            logger.info("No existing certificate found. Creating a new one.");
        }

        // Handle file upload
        if (file != null) {
            if (response.getImageUrl() != null) {
                logger.info("Deleting old certificate file.");
                deleteFile(response.getImageUrl());
            }
            logger.info("Saving new certificate file.");
            response.setImageUrl(createImage(productId, file, tempUpload));
        }

        try {
            Certificates savedCert = repo.save(response);
            logger.info("Successfully saved certificate for productId: {} and documentId: {}", productId, documentId);
            return savedCert;
        } catch (Exception e) {
            logger.error("Error saving certificate: ", e);
            throw new RuntimeException("Error saving certificate", e);
        }
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

    public void deleteFile(String imageUrl) {
        try {
            logger.info("Attempting to delete file: {}", imageUrl);
            String decodedUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8);
            String fileName = Paths.get(new java.net.URL(decodedUrl).getPath()).getFileName().toString();
            String productId = fileName.split("_")[0];
            String subDir = "product_images/" + productId + "/Documents/Certificates";

            BlobClient client = blobContainerClient.getBlobClient(subDir + "/" + fileName);
            if (client.exists()) {
                client.delete();
                logger.info("Deleted file: {}", fileName);
            } else {
                logger.warn("File not found: {}", fileName);
            }
        } catch (Exception e) {
            logger.error("Error deleting file: ", e);
            throw new RuntimeException("Could not delete file: " + imageUrl, e);
        }
    }

    private String createImage(Long productId, MultipartFile file, String path) throws IOException {
        if (file.isEmpty()) {
            logger.warn("Received empty file for productId: {}", productId);
            return null;
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFileName.contains("..")) {
            logger.error("Invalid file path detected: {}", originalFileName);
            throw new IllegalArgumentException("Invalid file path: " + originalFileName);
        }

        String uniqueFileName = productId + "_" + UUID.randomUUID() + "_" + originalFileName;
        BlobClient client = blobContainerClient.getBlobClient(path + "/" + uniqueFileName);
        client.upload(file.getInputStream(), file.getSize(), true);

        logger.info("File uploaded successfully with unique name: {}", uniqueFileName);
        logger.info("acutal file Uploaded place {}",client.getBlobUrl());

        return generateServerUrl(uniqueFileName);
    }

    private String generateServerUrl(String uniqueFileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/public/resource/certification/files/")
                .path(uniqueFileName)
                .toUriString();
    }

    public ResponseEntity<byte[]> serveFile(String fileName) {
        try {
            String productIdStr = fileName.split("_")[0];
            Long productId = Long.parseLong(productIdStr);
            String location = "product_images/" + productId + "/Documents/Certificates";

            BlobClient client = blobContainerClient.getBlobClient(location + "/" + fileName);
            byte[] response = client.downloadContent().toBytes();

            logger.info("Serving file from: {}", client.getBlobUrl());

            MediaType contentType = MediaType.IMAGE_JPEG;
            if (fileName.endsWith(".png")) {
                contentType = MediaType.IMAGE_PNG;
            } else if (fileName.endsWith(".gif")) {
                contentType = MediaType.IMAGE_GIF;
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .contentType(contentType)
                    .body(response);
        } catch (Exception e) {
            logger.error("Error serving file: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}




