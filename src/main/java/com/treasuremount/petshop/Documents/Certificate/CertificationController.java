package com.treasuremount.petshop.Documents.Certificate;
import com.treasuremount.petshop.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RequestMapping("/api/public/resource/certification")
@RestController
public class CertificationController {
    @Autowired
    private CertificationService service;

    @Autowired
    private  CertificateServiceWithAzure azureService;

    @Autowired
    private PetService petService;

    @PostMapping(value = "/upload/{productId}/{documentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCertificate(
            @PathVariable Long productId,
            @PathVariable Long documentId,
            @RequestParam(value = "file") MultipartFile file) throws IOException {

        try {
            Certificates certificates = azureService.upload(productId, documentId, file);
            return ResponseEntity.ok(certificates);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Certificates>> getAllCertificates(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "0") Long documentId) {

        List<Certificates> certificates = service.getAll(productId, documentId);
        return ResponseEntity.ok(certificates);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCertificate(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "0") Long documentId) {

        azureService.delete(productId, documentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String fileName) {
        System.out.println("serving the filename"+ fileName);
        return azureService.serveFile(fileName);
    }
}

