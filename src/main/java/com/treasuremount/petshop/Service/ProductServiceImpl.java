package com.treasuremount.petshop.Service;


import com.treasuremount.petshop.DTO.*;
import com.treasuremount.petshop.Entity.Category;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Enum.Carts;
import com.treasuremount.petshop.Order.Service.OrderServiceImpl;
import com.treasuremount.petshop.ProductImage.ProductImages;
import com.treasuremount.petshop.ProductImage.ProductImagesService;
import com.treasuremount.petshop.Repository.CategoryRepo;
import com.treasuremount.petshop.Repository.ProductRepo;
import com.treasuremount.petshop.Repository.UserRepo;
import com.treasuremount.petshop.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/*
*
*
*  {
    "id": 1,
    "name": "Pending",
    "activeStatus": true
  },
  {
    "id": 2,
    "name": "Approve",
    "activeStatus": true
  },
  {
    "id": 3,
    "name": "Reject",
    "activeStatus": true
  },
  {
    "id": 4,
    "name": "Disabled",
    "activeStatus": true
  },
  {
    "id": 5,
    "name": "Out of Stock",
    "activeStatus": true
  },
  {
    "id": 6,
    "name": "Restock",
    "activeStatus": true
  },
  {
    "id": 7,
    "name": "Available",
    "activeStatus": true
  }
* */

@Slf4j
@org.springframework.stereotype.Service
public class ProductServiceImpl implements Service<Product,Long>{
    @Autowired
    private ProductRepo repository;

    @Autowired
    private VendorService vendorService;

    @Autowired
    ProductImagesService productImagesService;

    @Autowired
    Mapper mapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    private static final String application_name = "Treasure Mount";


    //productStatus
    private final static HashMap<Long,String> productStautusMap;

    static{

        productStautusMap=new HashMap<>();
        productStautusMap.put(2L,"Approve");
        productStautusMap.put(5L,"Out of Stock");
        productStautusMap.put(6L,"Restock");
        productStautusMap.put(7L,"Available");
    }



    public Boolean updateStatus(ProductStatusDTO user){
         try{
             repository.updateProductStatus(user.getProductId(), user.getProductStatusId());
             Product product=getOneById(user.getProductId());
             ChangeStatus(product,product.getStockQuantity(),product.getMinStockLevel());
             repository.save(product);
             log.info("While updating the status productStatus for productId{}",product.getId());
             return true;

         } catch (Exception e) {
             throw new RuntimeException(e);
         }


    }

    //pending is the default behaviour
    //When the quantity increased only we handle the logic
    //For admin approve or reject those only access
    //For vendor disable only access
    //approved,available,restock,outofstock


    private void  ChangeStatus(Product product,Long stockQuantity,Integer minStock){

        if(!productStautusMap.containsKey(product.getProductStatusId()))
            return;
        if(stockQuantity==0){
            product.setProductStatusId(OrderServiceImpl.getOUT_OF_STOCK());
        }else if(stockQuantity > minStock){
            product.setProductStatusId(OrderServiceImpl.getAVAILABLE());
        }else if(stockQuantity < minStock){
            product.setProductStatusId(OrderServiceImpl.getRE_STOCK());

        }
    }


    /*
     productId,productStatusId,
    */


    @Override
    public Product create(Product d) {
        try {
           /* d.setDiscount(1.00d);*/
          /*  ChangeStatus(d,d.getStockQuantity(),d.getMinStockLevel());*/

            Product product=repository.save(d);
            Optional<User> optionalUser = userRepo.findById(product.getUserId());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                sendProductAddedNotification(user.getEmailId(), user.getFirstName(), product);
            } else {
                log.warn("User not found for the given product userId: {}", product.getUserId());
                // You might want to throw an exception here or handle it as per your business logic.
            }

            return product;
        } catch (Exception ex) {
           ex.printStackTrace();
           return null;
        }
    }
    // Add Resource or Product Email
    public void sendProductAddedNotification(String vendorEmail, String vendorName, Product product) {
        Optional<Category> category = categoryRepo.findById(product.getCategoryId());
        String productName = category.map(Category::getName).orElse("Unknown Category Name");
        String subject = "Your Product Has Been Successfully Added to "+application_name;
        String body = "Dear " + vendorName + ",\n\n" +
                "Thank you for adding a new product. Your product has been successfully listed and is now available for customers to view and purchase.\n\n" +
                "Here are the details of the product you just added:\n\n" +
                "- *Product Name*: " + product.getName() + "\n" +
                "- *Product Category*: " + productName + "\n" +
                "- *Price*: " + product.getPrice() + "\n" +
                "- *Stock Availability*: " + product.getStockQuantity() + "\n" +
                "If you wish to make any updates or modifications, you can log in to your vendor account and manage your products.\n\n" +
                "*Important Reminder*: If your product requires approval before going live, our team will review it and notify you once it’s ready for sale.\n\n" +
                "Thank you for being a part of the "+application_name+" team. We appreciate your contribution to providing excellent products to our customers.\n\n" +
                "If you have any questions, feel free to reach out to our support team at [Support Email/Phone].\n\n" +
                "Best regards,\nThe "+application_name+" Team\n\n" +
                "---\nIf you didn’t add this product or didn’t register an account with us, please ignore this email.";

        // Send email notification using your email service
        emailService.sendEmailNotification(vendorEmail, subject, body);
    }



  /*  @Override
    public List<Product> getAll(){
       return  repository.findAll();
    }
    */

    public List<ProductDTO> getAll2(Long userId,Boolean categoryIdCheck, List<Long> categoryId,Boolean subCategoryIdCheck,
                                    List<Long>  subCategoryId, Double minPrice, Double maxPrice,Boolean statusIdCheck, List<Long>  statusId, Boolean isAdmin) {
        try {
            List<Object[]> result1 = repository.test(userId, categoryIdCheck, categoryId,subCategoryIdCheck, subCategoryId, minPrice, maxPrice, statusIdCheck,statusId, isAdmin);

          return convertToProductList(result1);
        } catch (Exception ex) {
            ex.printStackTrace();  // Log the exception or handle it as needed
            return Collections.emptyList();
        }

    }


    @Override
    public List<Product> getAll(){
        return  repository.findAll();
    }
    public List<ProductDTO> getAll1(Long userId, Long categoryId, Long subCategoryId,
                                    Double minPrice, Double maxPrice, Long statusId, Boolean isAdmin, String searchWord) {
        try {
            searchWord = (searchWord == null) ? "" : searchWord;
            List<Object[]> result1 = repository.filterWithUserIdCategoryIdSubCategoryId(
                    userId, categoryId, subCategoryId, minPrice, maxPrice, statusId, isAdmin, searchWord);
            return convertToProductList(result1);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }


    public List<ProductDTO> getAllRelevantProduct(Long productId){

           Long categoryId=getOneById(productId).getCategoryId();


            List<Object[]> lst=repository.findRelevantProduct(productId,categoryId);
            return convertToProductList(lst);


    }


    public List<ProductDTO> getHomePageCarts(Carts carts, Long limit){


        List<Object[]> result=null;

        switch (carts){
            case TOP -> {
               result= repository.findByTopRatingProducts(limit);
                System.out.println("here are the top products");

            }
            case BEST -> {
                System.out.println("Here are the best products");
               result= repository.findByBestSelling(limit);
            }

            case LASTEST -> {
                System.out.println("Here are the latest products");
               result=repository.findLatestProducts(limit);
            }


        }


        return convertToProductList(result);


    }

    private void SetImageUrls(List<Long> productIds){


    }
    public List<Product> findAllById(List<Long> productId){
        return repository.findAllById(productId);
   }


/*

public String getAllUrl(Long productId){
    List<ProductImages> images=productImagesServi.getImagesByPoductId(productId);
    String url="";
    for(ProductImages image: images){
        url+=image.getImageUrl()+" ";
    }
    System.out.println("Image urls" + url);
    return url;
}
*/

  public List<ProductDTO> convertToProductList(List<Object[]> source){
      List<ProductDTO> productDTOList = new ArrayList<>();

      for (Object[] row : source) {
          if( row[0]==null) continue;

          ProductDTO productDTO = new ProductDTO();
          Long productId=(Long) row[0];
       /*  String url=getAllUrl(productId);*/
          productDTO.setId(productId);
          productDTO.setProductName((String) row[1]);
          productDTO.setImageUrl((String) row[2]);
          productDTO.setPrice((Double) row[3]);
          productDTO.setActiveStatus((Boolean) row[4]);
          productDTO.setCreatedDate((Date) row[5]);
          productDTO.setUpdatedDate((Date) row[6]);
          productDTO.setReturnWithin((Integer) row[7]);
          productDTO.setUserId((Long) row[8]);
          productDTO.setCategoryId((Long) row[9]);
          productDTO.setSubCategoryId((Long) row[10]);
          productDTO.setDiscount((Double) row[11]);
          productDTO.setMinStockLevel((Integer) row[12]);
          productDTO.setCategoryName((String) row[13]);
          productDTO.setSubCategoryName((String) row[14]);
          productDTO.setShopName((String) row[15]);
          productDTO.setUserName((String) row[16]);
          productDTO.setProductStatusId((Long) row[17]);
          productDTO.setProductStatusName((String) row[18]);
          productDTO.setStockQuantity((Long)row[19]);
          productDTO.setVideoUrl((String)row[20]);
          productDTOList.add(productDTO);
      }

      return productDTOList;
  }




    @Override
    public Product getOneById(Long id) {

         Product response= repository.findById(id).orElse(null);
         if(response==null) return null;
    /*   String imageUrls=getAllUrl(response.getId());
        response.setImageUrl(imageUrls);*/
         return response;

    }

    public ProductDemoDTO getOneByIdDto(Long id) {
        Product response= repository.findById(id).orElse(null);
      /*  String imageUrls=getAllUrl(response.getId());
        response.setImageUrl(imageUrls);*/

        return  mapper.productTOproductDemoDTO(response);

    }



   /* public List<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = repository.findAll(pageable);
        return productPage.getContent();
    }
*/



    @Override
    public Product update(Product d, Long id) {
        try {
            if (repository.existsById(id)) {
                d.setId(id);
                ChangeStatus(d,d.getStockQuantity(),d.getMinStockLevel());
                Product product= repository.saveAndFlush(d);
                System.out.println(product);
                return product;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

   /* @Transactional
    public Boolean setActiveStatus(OrderItems orderList, Boolean Status) {
        Long productId=orderList.getProductId();
        Optional<Product> productOptional = repository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setActiveStatus(Status);
            repository.save(product);
            return true;
        }
        return false;

    }*/

    public Integer isReturnAvailable(Long productId){
        Optional<Product> box=repository.findById(productId);
        if(box.isEmpty())
            return null;
        Product pt=box.get();
        if(pt.getReturnWithin()==null){
            return null;
        }
        return pt.getReturnWithin();

    }

    public List<ApprovalDTO> getAllApprovalData() {
        // Fetch raw data from the repository
        List<Object[]> rawData = repository.fetchApprovalData();
        return convertToList(rawData);

    }

    public ApprovalDTO getApprovalDataById(Long id) {
        // Fetch raw data from the repository
        List<Object[]> rawData = repository.fetchApprovalDataById(id);
        List<ApprovalDTO> response=convertToList(rawData);
        // Initialize the DTO list
        if(response.size()==1)
            return response.get(0);
        return null;
    }

    private List<ApprovalDTO> convertToList(List<Object[]> rawData){

        List<ApprovalDTO> approvalDTOList = new ArrayList<>();

        // Iterate through the raw data rows
        for (Object[] row : rawData) {
            if(row[1]==null) continue;
            if (row.length == 6) { // Ensure the row has 6 expected columns
                ApprovalDTO dto = new ApprovalDTO();

                // Safely map each field to the DTO
                dto.setProductId(((Number) row[0]).longValue());  // productId at index 0
                dto.setUserId(((Number) row[1]).longValue());     // userId at index 1
                dto.setUserName((String) row[2]);                  // userName at index 2
                dto.setShopName((String) row[3]);                  // shopName at index 3
                dto.setProductName((String) row[4]);               // productName at index 4
                dto.setActiveStatus((Boolean) row[5]);                 // isApproved (activeStatus) at index 5

                // Add the mapped DTO to the list
                approvalDTOList.add(dto);
            }
        }

        return approvalDTOList;

    }




    @Transactional
    public void updateApprovalStatus(List<ApprovalDTO> approvalDTOList) {
        for (ApprovalDTO dto : approvalDTOList) {

            if(dto.getActiveStatus()){

            }
            repository.updateProductApprovalStatus(dto.getProductId(), dto.getActiveStatus());


        }
    }


    public Product partialUpdate(Long id, ProductUpdateDTO updateDTO) {
       Product existingProduct= getOneById(id);
       if(updateDTO.getPrice() !=null && updateDTO.getPrice() <= 0){
           return null;
       }
        if(existingProduct==null ){
            return null;
        }

           // Update fields if they are provided in the request
           if (updateDTO.getStockQuantity() != null) {
               existingProduct.setStockQuantity(updateDTO.getStockQuantity());
           }
           if (updateDTO.getDiscount() != null) {
               existingProduct.setDiscount(updateDTO.getDiscount());
           }
           if (updateDTO.getMinStockLevel() != null) {
               existingProduct.setMinStockLevel(updateDTO.getMinStockLevel());
           }
           if (updateDTO.getPrice() != null) {
               existingProduct.setPrice(updateDTO.getPrice());
           }

          ChangeStatus(existingProduct, existingProduct.getStockQuantity(), existingProduct.getMinStockLevel());

           Product updatedProduct = update(existingProduct, id);
           return updatedProduct;




    }

   /* public void updateImageUrls(String image,Long productId){
        repository.updateImage(productId,image);
    }*/
}
