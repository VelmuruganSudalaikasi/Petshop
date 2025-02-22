/*
package com.treasuremount.petshop.config;

import com.azure.storage.blob.BlobContainerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/blob")
public class BlobContainerManagement {

    @Autowired
    BlobContainerClient blobContainerClient;

    @Value("${azure.storage.container-name}")
    String containerName;

    @PutMapping("/Delete")
    public ResponseEntity<?> deleteContainer(){
        if(blobContainerClient.deleteIfExists()){

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();

    }
}
*/
