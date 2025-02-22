package com.treasuremount.petshop.Service;

import com.treasuremount.petshop.utils.FolderPathStrategy;
import lombok.extern.slf4j.Slf4j;
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
import java.util.UUID;

@Slf4j
@Service
public class ImageService {

    // three controller were using the class
    // 1. category
    // 2. subcategory
    // 3. homepage1

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Method to upload image for any entity type (Category/SubCategory)
    public String createImage(Long entityId, MultipartFile file, FolderPathStrategy pathStrategy) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String folderPath = pathStrategy.getFolderPath(entityId);
        Path uploadPath = Paths.get(uploadDir, folderPath).toAbsolutePath().normalize();
        log.info("Uploading image to {}", uploadPath);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFileName.contains("..")) {
            throw new IllegalArgumentException("Invalid file path: " + originalFileName);
        }

        String uniqueFileName = entityId + "_" + UUID.randomUUID() + "_" + originalFileName;
        Path filePath = uploadPath.resolve(uniqueFileName);
        file.transferTo(filePath);
        log.info("Uploaded image with unique name {}", uniqueFileName);

        return uniqueFileName;
    }

    // Method to generate URL for the image to serve
    public String generateServerUrl(String uniqueFileName, String apiPath) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(apiPath)
                .path(uniqueFileName)
                .toUriString();
    }

    // Method to serve the image
    public ResponseEntity<Resource> serveFile(String fileName, FolderPathStrategy pathStrategy, Long entityId) {
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
    }

    // Method to delete image
    public void deleteImage(String fileName, FolderPathStrategy pathStrategy, Long entityId) {
        try {
            String folderPath = pathStrategy.getFolderPath(entityId);
            Path filePath = Paths.get(uploadDir, folderPath).toAbsolutePath().normalize().resolve(fileName);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                log.info("Deleted image: {}", filePath);
            } else {
                log.error("Image not found: {}", filePath);
            }
        } catch (IOException ex) {
            log.error("Error deleting file", ex);
            throw new RuntimeException("Could not delete file: " + fileName, ex);
        }
    }
}
