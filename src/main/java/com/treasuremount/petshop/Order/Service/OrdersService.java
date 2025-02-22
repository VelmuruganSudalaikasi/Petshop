package com.treasuremount.petshop.Order.Service;

import com.treasuremount.petshop.Entity.Blog;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.Order.DTO.OrderCustomDTO;
import com.treasuremount.petshop.Order.DTO.OrderGetOneDTO;
import com.treasuremount.petshop.Order.DTO.ShippingAddressDTO;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.Repository.OrdersRepo;
import com.treasuremount.petshop.ProductImage.ProductImages;
import com.treasuremount.petshop.ProductImage.ProductImagesService;
import com.treasuremount.petshop.Repository.BlogRepo;
import com.treasuremount.petshop.Service.Service;
import com.treasuremount.petshop.Service.VendorServiceImpl;
import com.treasuremount.petshop.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@org.springframework.stereotype.Service
public class OrdersService implements Service<Orders,Long> {
    @Autowired
    private OrdersRepo repository;

    @Autowired
    private Mapper modelmapper;
    @Autowired
    private ProductImagesService productImagesService;

    @Autowired
    private VendorServiceImpl vendorService;
    private static final String CHARACTERS = "0123456789";
    private static final int ID_LENGTH = 9;
    private static final Random SECURE_RANDOM = new SecureRandom();

    @Override
    public Orders create(Orders d) {
        try {

            String unique=null;
            do {
                unique = generateSecureRandomString(ID_LENGTH);
            }
            while (repository.existsByUniqueOrderId(unique));
            d.setUniqueOrderId(unique);
            return repository.save(d);



        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String generateSecureRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = SECURE_RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return "ORD"+sb.toString();
    }






    public List<OrderCustomDTO> getAllByCustom(Long userId,Boolean isUser){
        List<Object[]> rawResults= repository.getAllByUserVendor(userId,isUser);

        return  convertIntoOrderCustomDTO(rawResults);


    }


    public List<OrderCustomDTO> getAllByCustomv1(Long userId,Boolean isUser,Long OrderStatus,LocalDate startDate,LocalDate endDate){
        List<Object[]> rawResults= repository.getAllByUserVendorv1(userId,isUser, OrderStatus,startDate,endDate);

        return  convertIntoOrderCustomDTOV1(rawResults);


    }

    public List<OrderCustomDTO> getAllByAdmin() {
        List<Object[]> rawResults = repository.getAllByAdminNative();
         return  convertIntoOrderCustomDTO(rawResults);
    }

    public List<OrderCustomDTO> convertIntoOrderCustomDTOV1(List<Object[]> rawResults) {
        List<OrderCustomDTO> orderCustomDTOList = new ArrayList<>();

        for (Object[] result : rawResults) {
            // Convert Date to LocalDate for orderDate and deliveryDate
            LocalDate orderDate = (result[17] != null) ? ((java.sql.Date) result[17]).toLocalDate() : null;
            LocalDate deliveryDate = (result[18] != null) ? ((java.sql.Date) result[18]).toLocalDate() : null;

            // Convert Double to BigDecimal for productPrice
            BigDecimal productPrice = (result[1] != null) ? BigDecimal.valueOf((Double) result[1]) : BigDecimal.ZERO;

            // Create OrderCustomDTO instance with proper conversions
            OrderCustomDTO dto = new OrderCustomDTO(
                    (String) result[0],    // productName
                    productPrice,          // productPrice (converted to BigDecimal)
                    (String) result[2],    // contactNumber
                    (String) result[3],    // vendorName
                    (Long) result[4],      // subCategoryId
                    (String) result[5],    // subCategoryName
                    (Long) result[6],      // categoryId
                    (String) result[7],    // categoryName
                    (Long) result[8],      // orderId
                    (Long) result[9],      // userId
                    (Long) result[10],     // productId
                    ((Number) result[11]).longValue(), // quantity
                    (BigDecimal) result[12], // tax
                    (BigDecimal) result[13], // shippingCharge
                    (BigDecimal) result[14], // subtotal
                    (Long) result[15],     // orderStatusId
                    (BigDecimal) result[16], // totalAmount
                    orderDate,             // orderDate (converted to LocalDate)
                    deliveryDate,          // deliveryDate (converted to LocalDate)
                    (Boolean) result[19],  // isCancelled
                    (Boolean) result[20]   // isReturned
            );
            dto.setUniqueId((String) result[21]);
            orderCustomDTOList.add(dto);
        }
        return orderCustomDTOList;
    }

    public List<OrderCustomDTO> convertIntoOrderCustomDTO(List<Object[]> rawResults) {
        List<OrderCustomDTO> orderCustomDTOList = new ArrayList<>();

        for (Object[] result : rawResults) {
            // Convert Date to LocalDate for orderDate and deliveryDate
            LocalDate orderDate = (result[17] != null) ? ((java.sql.Date) result[17]).toLocalDate() : null;
            LocalDate deliveryDate = (result[18] != null) ? ((java.sql.Date) result[18]).toLocalDate() : null;

            // Convert Double to BigDecimal for productPrice
            BigDecimal productPrice = (result[1] != null) ? BigDecimal.valueOf((Double) result[1]) : BigDecimal.ZERO;

            // Create OrderCustomDTO instance with proper conversions
            OrderCustomDTO dto = new OrderCustomDTO(
                    (String) result[0],    // productName
                    productPrice,          // productPrice (converted to BigDecimal)
                    (String) result[2],    // contactNumber
                    (String) result[3],    // vendorName
                    (Long) result[4],      // subCategoryId
                    (String) result[5],    // subCategoryName
                    (Long) result[6],      // categoryId
                    (String) result[7],    // categoryName
                    (Long) result[8],      // orderId
                    (Long) result[9],      // userId
                    (Long) result[10],     // productId
                    ((Number) result[11]).longValue(), // quantity
                    (BigDecimal) result[12], // tax
                    (BigDecimal) result[13], // shippingCharge
                    (BigDecimal) result[14], // subtotal
                    (Long) result[15],     // orderStatusId
                    (BigDecimal) result[16], // totalAmount
                    orderDate,             // orderDate (converted to LocalDate)
                    deliveryDate,          // deliveryDate (converted to LocalDate)
                    (Boolean) result[19],  // isCancelled
                    (Boolean) result[20]   // isReturned
            );
            orderCustomDTOList.add(dto);
        }

        return orderCustomDTOList;
    }

    @Override
    public List<Orders> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    public List<Orders> getOrdersByUserId(Long userId) {
        // Use the repository method to find all orders by userId
        return repository.findByUserId(userId);
    }

    public List<Orders> saveAll(List<Orders> orders){
        return  repository.saveAll(orders);
    }




    @Override
    public Orders getOneById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }



    public OrderGetOneDTO getOneByIdCustom(Long id) {
        try {
            Orders order = repository.findById(id).orElse(null);
            if (order == null) {
                return null; // Return null if no order is found
            }
            //add the productImage


            // Map order to DTO
            OrderGetOneDTO dto = new OrderGetOneDTO(order);
            List<ProductImages> image=null;
            if(!(image=productImagesService.getAllProductImages(order.getProductId(),1L)).isEmpty()){
                dto.setImageUrl(image.get(0).getImageUrl());

            }

            // Fetch and set additional details
            if (order.getShippingAddress() != null) {
              ShippingAddressDTO dtoAddress=  modelmapper.toDTO(order.getShippingAddress());
                dto.setShippingAddress(dtoAddress);
                System.err.println(order.getShippingAddress());
            }
            User user=null;
            if ((user=order.getUser())!= null) {
                dto.setUserName(user.getFirstName());
                dto.setUserEmail(user.getEmailId());

            }
            if (order.getOrderStatusS() != null) {
                dto.setOrderStatusName(order.getOrderStatusS().getName());
                System.err.println(order.getOrderStatusS().getName());
            }
            if (order.getProduct() != null) {
                dto.setProductName(order.getProduct().getName());
                dto.setCategoryName(order.getProduct().getCategory().getName());
                dto.setSubCategoryName(order.getProduct().getSubCategory().getName());
                System.err.println(order.getProduct().getName());
            }
            Vendor vendor=null;

            if(order.getProduct()!=null && (vendor =vendorService.getOneUserById(order.getProduct().getUserId())) !=null){

                String vendorName=order.getProduct().getUser().getFirstName();

//                Vendor vendor=vendorService.getOneUserById(order.getProduct().getUserId());

                String address=vendor.getAddress();
                String shopName=vendor.getShopName();

                dto.setVendorName(vendorName);
                dto.setAddress(address);
                dto.setShopName(shopName);
            }


            // find the vendor name, vendor address, vendor shopname

            return dto;
        } catch (Exception ex) {
            // Log the exception for debugging
            ex.printStackTrace();
            return null; // Return null if any exception occurs
        }
    }



    @Override
    public Orders update(Orders d, Long id) {
        try {
            if (repository.existsById(id)) {

                d.setId(id);
                return repository.saveAndFlush(d);
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

}

