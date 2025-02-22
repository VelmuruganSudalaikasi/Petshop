package com.treasuremount.petshop.Profile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.treasuremount.petshop.Documents.Certificate.CertificateServiceWithAzure;
import com.treasuremount.petshop.utils.FolderPathStrategy;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.UUID;


@Slf4j
@Service
public class ProfileImageServiceWithAzure {

    // three controller were using the class
    // 1. category
    // 2. subcategory
    // 3. homepage1

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final BlobContainerClient blobContainerClient;

    private static final Logger logger = LoggerFactory.getLogger(CertificateServiceWithAzure.class);

    @Autowired
    public ProfileImageServiceWithAzure(BlobContainerClient blobContainerClient) {
        this.blobContainerClient = blobContainerClient;
    }

    // Method to upload image for any entity type (Category/SubCategory)
    public String createImage(Long entityId, MultipartFile file, FolderPathStrategy pathStrategy) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String location=pathStrategy.getFolderPath(entityId);

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFileName.contains("..")) {
            throw new IllegalArgumentException("Invalid file path: " + originalFileName);
        }

        String uniqueFileName = entityId + "_" + UUID.randomUUID() + "_" + originalFileName;

        BlobClient client=blobContainerClient.getBlobClient(location+"/"+uniqueFileName);
        client.upload(file.getInputStream(),file.getSize(),true);
        logger.info("Actual saved Location on  {} image size {}",client.getBlobUrl(),file.getSize());
        log.info("Uploaded image with unique name {}", uniqueFileName);

        return uniqueFileName;
    }

    // Method to generate URL for the image to serve
    public String generateServerUrl(String uniqueFileName, String apiPath) {
        return ServletUriComponentsBuilder.fromHttpUrl("https://petsshopapi-d6dccjc9bne5g7ce.centralindia-01.azurewebsites.net/")
                .path(apiPath)
                .path(uniqueFileName)
                .toUriString();
    }

    // Method to serve the image
/*    public ResponseEntity<Resource> serveFile(String fileName, FolderPathStrategy pathStrategy, Long entityId) {
        try {
            String folderPath = pathStrategy.getFolderPath(entityId);
            Path filePath = Paths.get(uploadDir, folderPath).toAbsolutePath().normalize().resolve(fileName).normalize();
            log.info("Image folder path while serving {}",filePath);
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
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            log.error("Error serving file", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/


    public ResponseEntity<byte[]> serveFile(String fileName, FolderPathStrategy pathStrategy, Long entityId)  {
        try {

            String location=pathStrategy.getFolderPath(entityId);
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

    // Method to delete image
    public void deleteImage(String fileName, FolderPathStrategy pathStrategy, Long entityId) {
        try {
            String location=pathStrategy.getFolderPath(entityId);
            BlobClient client = blobContainerClient.getBlobClient(location + "/" + fileName);

            if (client.exists()) {
                client.delete();
                logger.info("Deleted file: {}", fileName);
            } else {
                logger.warn("File not found: {}", fileName);
            }
        } catch (Exception ex) {
            log.error("Error deleting file", ex);
            throw new RuntimeException("Could not delete file: " + fileName, ex);
        }
    }
}
