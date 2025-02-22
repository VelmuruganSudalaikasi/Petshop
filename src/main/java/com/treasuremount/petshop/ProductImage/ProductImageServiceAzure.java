package com.treasuremount.petshop.ProductImage;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
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
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;



@Service
@Slf4j
public class ProductImageServiceAzure {

    private static final Logger logger = LoggerFactory.getLogger(ProductImageServiceAzure.class);

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;


    private final BlobContainerClient blobContainerClient;

    @Autowired
    public ProductImageServiceAzure(BlobContainerClient blobContainerClient) {
        this.blobContainerClient = blobContainerClient;
    }



    public void deleteImages(Long productId, Long positionId) {
        if (positionId == 0L) {
            List<ProductImages> imagesList=productImagesRepository.findByProductId(productId);
            for (ProductImages image : imagesList) {
                logger.info("Delete image urls {}",image.getImageUrl());
                deleteImageFile(image.getImageUrl());
//                productImagesRepository.delete(image);
            }
            productImagesRepository.deleteByProductId(productId);
        } else {
            List<ProductImages> images = productImagesRepository.findByProductIdAndPositionId(productId, positionId);
            for (ProductImages image : images) {
                deleteImageFile(image.getImageUrl());
                logger.info("Delete image urls {}",image.getImageUrl());
                productImagesRepository.delete(image);
            }
        }
    }
    public List<ProductImages> uploadImages(Long productId, Long positionId, MultipartFile file) throws IOException {
        // Define the upload directory
     /*   Path uploadPath = Paths.get(uploadDir, "product_images", String.valueOf(productId)).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }*/

        String location="product_images/"+String.valueOf(productId);

        // Validate file
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFileName.contains("..")) {
            throw new IllegalArgumentException("Invalid file path: " + originalFileName);
        }

        // Generate unique filename with productId included
        String uniqueFileName = productId + "_" + UUID.randomUUID().toString() + "_" + originalFileName;

        // Create path for saving the new file
        BlobClient client=blobContainerClient.getBlobClient(location+"/"+uniqueFileName);
        client.upload(file.getInputStream(),file.getSize(),true);
        logger.info("Actual saved location {}",client.getBlobUrl());
//        Path filePath = uploadPath.resolve(uniqueFileName);
//        file.transferTo(filePath);

        // Generate the URL for the new file
        String fileDownloadUri = ServletUriComponentsBuilder.fromHttpUrl("https://petsshopapi-d6dccjc9bne5g7ce.centralindia-01.azurewebsites.net/")
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


/*

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
*/


    public void deleteImageFile(String imageUrl) {
        try {
            logger.info("Attempting to delete file: {}", imageUrl);
            String decodedUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8);
            String fileName = Paths.get(new java.net.URL(decodedUrl).getPath()).getFileName().toString();
            String productId = fileName.split("_")[0];
            String subDir = "product_images/" + productId ;

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

    public ResponseEntity<byte[]> serveFile(String fileName) {
        try {
            String productIdStr = fileName.split("_")[0];
            Long productId = Long.parseLong(productIdStr);
            String location = "product_images/" + productId ;

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
