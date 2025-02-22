package com.treasuremount.petshop.Profile;

import com.treasuremount.petshop.utils.FolderPathStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/public/resource/profileImage")
public class ProfileImageController {

    @Autowired
    private ProfileImageServiceWithAzure azureService;

    @Autowired
    private ProfileImageService service;

    @PostMapping(value = "/upload/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileImage> uploadCertificate(
            @PathVariable Long userId,
            @RequestParam(value = "file") MultipartFile image) throws IOException {

        try {

            ProfileImage profileImage=service.getOneByUserId(userId);
            FolderPathStrategy pathStrategy = entityId -> "profile/" + entityId;
            if(profileImage==null){
                System.out.println("Creating the new Object for" + userId);
                profileImage=new ProfileImage();
                profileImage.setUserId(userId);
            }else{
                if(!(profileImage.getImageUrl() ==null && profileImage.getImageUrl().isEmpty())){
                    System.out.println("Image url is null for given id"+ userId);
                }else{

                    azureService.deleteImage(profileImage.getImageUrl(),pathStrategy,userId);
                }

            }




            // Upload the image for the category using dynamic folder path
            String imageFileName = azureService.createImage(userId, image, pathStrategy);
           profileImage.setImageUrl(azureService.generateServerUrl(imageFileName,"api/public/resource/profileImage/serve/"));
           ProfileImage response=service.create(profileImage);


            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProfileImage>> getAllCertificates() {

        List<ProfileImage> certificates = service.getAll();
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/getOne/{userId}")
    public ResponseEntity<ProfileImage> getAllImageWithUserId(
            @PathVariable Long userId

    ) {

       ProfileImage certificates = service.getOneByUserId(userId);
        return ResponseEntity.ok(certificates);
    }



   /* @DeleteMapping("/delete/url")
    public ResponseEntity<Void> delete(@RequestParam String imageUrl){
        String entityIdStr = imageUrl.split("_")[0];  // Extract categoryId from the filename
        Long entityId = Long.valueOf(entityIdStr);

        // Lambda for path strategy (category folder path)
        FolderPathStrategy pathStrategy = entityId1 -> "profile/" + entityId1;
        try{

            azureService.deleteImage(imageUrl,pathStrategy,entityId);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

    }*/


    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCertificate(
            @RequestParam Long userId) {

       ProfileImage image=service.getOneByUserId(userId);
       if(image==null || image.getImageUrl() ==null || image.getImageUrl().isEmpty()){
           return ResponseEntity.notFound().build();
       }
        FolderPathStrategy pathStrategy = entityId -> "profile/" + entityId;
       azureService.deleteImage(image.getImageUrl(),pathStrategy,userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/serve/{fileName:.+}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String fileName) {
        try {
            String entityIdStr = fileName.split("_")[0];  // Extract categoryId from the filename
            Long entityId = Long.valueOf(entityIdStr);

            // Lambda for path strategy (category folder path)
            FolderPathStrategy pathStrategy = entityId1 -> "profile/" + entityId1;

            // Serve the file using dynamic path strategy
            return azureService.serveFile(fileName, pathStrategy, entityId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
