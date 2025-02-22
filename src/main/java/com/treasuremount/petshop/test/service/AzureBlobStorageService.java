/*
package com.treasuremount.petshop.test.service;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.OutputStream;

@Service
public class AzureBlobStorageService {
    private final String connectionString;
    private final String containerName;

    private BlobContainerClient containerClient;

    @Autowired
    public AzureBlobStorageService(@Value("${azure.storage.connection-string}") String connectionString,
                                   @Value("${azure.storage.container-name}") String containerName) {

        this.containerName = containerName;

        System.out.println(containerName);
        System.out.println(connectionString);
    }

    @PostConstruct
    public void initializeBlobContainer() {
        try {
            // Log initialization attempt
            System.out.println("Initializing Azure Blob Storage Client...");

            containerClient = new BlobContainerClientBuilder()
                    .connectionString(connectionString)
                    .containerName(containerName)
                    .buildClient();

            // Log after client creation
            System.out.println("BlobContainerClient created successfully.");

            if (!containerClient.exists()) {
                containerClient.create();
                System.out.println("Container created: ");
            } else {
                System.out.println("Container already exists.");
            }
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error initializing Blob Container Client: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error initializing Azure Blob Storage", e);
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        // Ensure the container client is initialized
        if (containerClient == null) {
            throw new IllegalStateException("Blob container client is not initialized.");
        }

        // Create a BlobClient for the given file
        BlobClient blobClient = containerClient.getBlobClient(file.getOriginalFilename());

        // Upload the file to Azure Blob Storage
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        // Return the Blob URL
        return blobClient.getBlobUrl();
    }

    public byte[] downloadFile(String fileName) {
        // Ensure the container client is initialized
        if (containerClient == null) {
            throw new IllegalStateException("Blob container client is not initialized.");
        }

        // Create a BlobClient for the specified file name
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        // Download and return the file content as a byte array


        return blobClient.downloadContent().toBytes();
    }
}
*/
