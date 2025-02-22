/*
package com.treasuremount.petshop.Controller;


import com.treasuremount.petshop.Documents.Certificate.CertificationService;
import com.treasuremount.petshop.Entity.Category;
import com.treasuremount.petshop.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    CertificationService certificationService;


    @Autowired
    CategoryService categoryService;

     private final String identificationPath="category/";

     @Value("${file.upload-dir}")
     private String uploadDir;

    public String upload(MultipartFile file,Long categoryId) throws IOException  {
         String tempUpload = uploadDir + "/category/" + categoryId ;
         String imageUrl;
         if(!file.isEmpty()){
             String apiPath = "\"/api/public/resource/category/files/\"";
             imageUrl =certificationService.createImage(categoryId,file,tempUpload, apiPath);

         }else{
             throw  new RuntimeException("multi -partfile is empty");

         }
        UpdateImageUrl(categoryId,imageUrl);
          return imageUrl;
     }

     public void UpdateImageUrl(Long categoryId,String imageUrl){
         Category category=categoryService.getOneById(categoryId);
         if(category==null) throw new RuntimeException("CategoryId not found");
         category.setImageUrl(imageUrl);
         categoryService.create(category);

     }

     public void delete(String imageUrl) throws IOException {
         String categoryId=certificationService.deleteFile(imageUrl,identificationPath,"");
         updateImage(Long.parseLong(categoryId),null);

     }

     public void updateImage(Long categoryId,MultipartFile file) throws IOException {
         Category category=categoryService.getOneById(categoryId);
         if(category ==null){
             throw  new RuntimeException("Category Image not found");
         }
         String existingImageUrl= category.getImageUrl();
         if(existingImageUrl!=null){
             delete(existingImageUrl);
         }
         UpdateImageUrl(categoryId,upload(file,categoryId));


     }


     public ResponseEntity<Resource> serve(String fileName){
       return certificationService.serveFile(fileName,identificationPath,"");
     }






}
*/
