package com.treasuremount.petshop.utils;



import com.treasuremount.petshop.AccessoriesResource.AccessoriesResourceDTO;
import com.treasuremount.petshop.AccessoriesResource.AccessoriesResponseDTO;
import com.treasuremount.petshop.DTO.*;
import com.treasuremount.petshop.Entity.*;
import com.treasuremount.petshop.FoodResource.FoodResourceDTO;
import com.treasuremount.petshop.FoodResource.FoodResponseDTO;
import com.treasuremount.petshop.Login.LoginResDTO;
import com.treasuremount.petshop.Login.LoginResponse;
import com.treasuremount.petshop.MedicineResource.Medicine;
import com.treasuremount.petshop.MedicineResource.MedicineDetails;
import com.treasuremount.petshop.MedicineResource.MedicineResourceDTO;
import com.treasuremount.petshop.Order.DTO.OrdersDTO;
import com.treasuremount.petshop.Order.DTO.ShippingAddressDTO;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.PetResource.PetResourceDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Mapper {
    private static final ModelMapper modelMapper = new ModelMapper();



    public Mapper() {
        modelMapper.getConfiguration().setAmbiguityIgnored(true).setSkipNullEnabled(true); ;

    }




    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDetailsDTO userDetailsToDTO(User user) {

        return modelMapper.map(user, UserDetailsDTO.class);
    }

    public LoginResDTO toDTO(Map<String,Object> response, String token) {

        LoginResDTO dto=modelMapper.map(response, LoginResDTO.class);
        dto.setToken(token);
        return dto;

    }
    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public Vendor RegtoEntity(VendorDTO user) {
        return modelMapper.map(user, Vendor.class);
    }

    public InventoryLocation RegtoEntity(InventoryLocationDTO user) {
        return modelMapper.map(user, InventoryLocation.class);
    }
    public InventoryLocationDTO toLocation(InventoryLocation user) {
        return modelMapper.map(user, InventoryLocationDTO.class);
    }

    public User toEntity(VendorRegisterDTO user) {
        return modelMapper.map(user, User.class);
    }



    public VendorRegisterDTO toVendorRepsonse(User user, Vendor vendor) {
        VendorRegisterDTO response=modelMapper.map(user,VendorRegisterDTO.class);
        modelMapper.map(vendor,response);
        return response;

    }

    public LoginResponse toLoginDTO(User user) {
        LoginResponse response= modelMapper.map(user, LoginResponse.class);
        response.setUserId(user.getId());
        response.setRoleId(user.getRoleId());
        return response;
    }



    public Foods FoodtoEntity(FoodResourceDTO userDTO, Long ProductId) {
        Foods food=modelMapper.map(userDTO, Foods.class);
        food.setProductId(ProductId);
        return food;
    }

    public Product ProducttoEntity(FoodResourceDTO userDTO) {

        Product product= modelMapper.map(userDTO, Product.class);

        return product;

    }

    public FoodDetails FooodDetailstoEntity(FoodResourceDTO userDTO,Long FoodId) {
        FoodDetails foodDetails= modelMapper.map(userDTO, FoodDetails.class);
        foodDetails.setFoodsId(FoodId);
        return foodDetails;
    }

    public FoodResponseDTO FoodDTOtoResponse(Product product, Foods food, FoodDetails details) {

        System.out.println(product);
        System.out.println(food);
        System.out.println(details);
        Long productId=product.getId();
        Long subCategoryId=product.getSubCategoryId();
        Long UserId=product.getUserId();
        Long categoryId=product.getCategoryId();
        Long productStatusId=product.getProductStatusId();
        FoodResponseDTO response=modelMapper.map(product, FoodResponseDTO.class);
        response.setProductStatusId(productStatusId);
        response.setProductId(productId);
        response.setUserId(UserId);
        response.setCategoryId(categoryId);
        response.setSubCategoryId(subCategoryId);
        response.setProductName(product.getName());

        System.out.println(response);
        modelMapper.map(food,response);
        System.out.println(response);
        modelMapper.map(details,response);
        System.out.println(response);
        return response;
    }

    //food is closed


    //pet is started


     //response
    public com.treasuremount.petshop.PetResource.PetResponseDTO PetsDTOtoResponse(Product product, Pets pet, PetDetails details) {

        System.out.println(product);
        System.out.println(pet);
        System.out.println(details);
        Long productId=product.getId();
        Long subCategoryId=product.getSubCategoryId();
        Long UserId=product.getUserId();
        Long categoryId=product.getCategoryId();
        Long productStatusId=product.getProductStatusId();
        com.treasuremount.petshop.PetResource.PetResponseDTO response=modelMapper.map(product, com.treasuremount.petshop.PetResource.PetResponseDTO.class);
        response.setProductStatusId(productStatusId);
        response.setProductId(productId);
        response.setUserId(UserId);
        response.setCategoryId(categoryId);
        response.setSubCategoryId(subCategoryId);
        response.setProductName(product.getName());


        System.out.println(response);
        modelMapper.map(pet,response);
        System.out.println(response);
        modelMapper.map(details,response);
        System.out.println(response);

        return response;
    }



    public Pets PettoEntity(PetResourceDTO userDTO, Long ProductId) {
        Pets food=modelMapper.map(userDTO, Pets.class);
        food.setProductId(ProductId);
        return food;
    }



    public Product ProducttoEntity(PetResourceDTO userDTO) {

        Product product= modelMapper.map(userDTO, Product.class);


        return product;

    }


    public PetDetails PetDetailstoEntity(PetResourceDTO resource, Long id) {
        PetDetails petDetails= modelMapper.map(resource, PetDetails.class);
        petDetails.setPetId(id);
        return petDetails;
    }

    //pet is closed



    // accessory is started

    public AccessoryDetails AccDetailstoEntity(AccessoriesResourceDTO userDTO, Long PetId) {
        AccessoryDetails petDetails= modelMapper.map(userDTO, AccessoryDetails.class);
        petDetails.setAccessoriesId(PetId);
        return petDetails;
    }


    public Product ProducttoEntity(AccessoriesResourceDTO resource) {
        Product product= modelMapper.map(resource, Product.class);
        return product;
    }


    public Accessories AcctoEntity(AccessoriesResourceDTO resource, Long productId) {

        Accessories food=modelMapper.map(resource, Accessories.class);
        food.setProductId(productId);
        System.out.println("from mppper"+food);
        return food;

    }


    public AccessoriesResponseDTO AccessoriesResponseDTO(Product product, Accessories acc, AccessoryDetails details) {

        System.out.println(product);

        System.out.println(acc);
        System.out.println(details);


        Long productId=product.getId();
        Long subCategoryId=product.getSubCategoryId();
        Long UserId=product.getUserId();
        Long categoryId=product.getCategoryId();
        Long productStatusId=product.getProductStatusId();

        AccessoriesResponseDTO response=modelMapper.map(product, AccessoriesResponseDTO.class);
        response.setProductStatusId(productStatusId);
        response.setProductId(productId);
        response.setUserId(UserId);
        response.setCategoryId(categoryId);
        response.setSubCategoryId(subCategoryId);
        response.setProductName(product.getName());



        System.out.println(response);
        modelMapper.map(acc,response);
        System.out.println(response);
        modelMapper.map(details,response);
        System.out.println(response);

        return response;
    }



    //accessory is colsed



    //order mapper is started
    public Orders orderToEntity(OrdersDTO dto){
        Orders response=modelMapper.map(dto,Orders.class);
        response.setId(0L);
        System.out.println(response);
        return response;
    }

    /*public OrderItems orderItemsToEntity(OrdersDTO dto,Long OrderId){
        OrderItems response=modelMapper.map(dto,OrderItems.class);
        response.setId(0L);
        response.setOrderId(OrderId);
        System.out.println(response);
        return response;
    }

    public OrdersDTO toEntity(Orders order,OrderItems item){
        OrdersDTO dto=modelMapper.map(order,OrdersDTO.class);
        modelMapper.map(item,dto);
        System.out.println(dto);
        return dto;
    }
*/



    //Vendor Response


    public VendorRegisterDTO VendorResponse(Vendor vendor, User user) {
        // Initialize the DTO and map the base UserDTO fields from the User object
        VendorRegisterDTO dto = modelMapper.map(user, VendorRegisterDTO.class);

        // Map the Vendor-specific fields
        modelMapper.map(vendor, dto);

        // Convert the locations (One-to-Many relationship)


        System.err.println("After vendor mapping: " + dto);

        return dto;
    }



    public UserDTO map(User user, Class<UserDTO> userDTOClass) {
        return modelMapper.map(user,userDTOClass);
    }

    public UserDTO userDTOtoEntity(User user, Class<UserDTO> userDTOClass) {
        return modelMapper.map(user,userDTOClass);
    }

    public VendorDTO vendorDTOtoEntity(Vendor vendor, Class<VendorDTO> vendorDTOClass) {
        return modelMapper.map(vendor,vendorDTOClass);
    }


/*
*  custom mapper for variable change
* */
    public ProductDemoDTO productTOproductDemoDTO(Product product){

        System.err.println(product);
        ProductDemoDTO dto= modelMapper.map(product,ProductDemoDTO.class);
        dto.setId(product.getId());
        dto.setCategoryId(product.getCategoryId());
        dto.setSubCategoryId(product.getSubCategoryId());
        dto.setUserId(product.getUserId());
        dto.setProductStatusId(product.getProductStatusId());
        dto.setProductName(product.getName());

        System.err.println(dto);
        return dto;
    }

    //shippping address to dto

    public ShippingAddressDTO toDTO(ShippingAddress address){
        ShippingAddressDTO response= modelMapper.map(address,ShippingAddressDTO.class);
        response.setId(address.getId());
        response.setUserId(address.getUserId());
        response.setCountryId(address.getCountryId());
        response.setStateId(address.getStateId());
        response.setCountryName(address.getCountry().getCountryName());
        response.setStateName(address.getState().getStateName());
        return response;
     }


     //medicine Table:


    public Medicine MedicineToEntity(MedicineResourceDTO resource, Long productId) {

        Medicine food=modelMapper.map(resource, Medicine.class);
        food.setProductId(productId);
        System.out.println("from medicine"+food);
        return food;

    }

    public Product ProducttoEntity(MedicineResourceDTO userDTO) {

        Product product= modelMapper.map(userDTO, Product.class);
        product.setName(userDTO.getProductName());
        return product;

    }

    public MedicineDetails MedicineDetailsToEntity(MedicineResourceDTO userDTO, Long PetId) {
        MedicineDetails petDetails= modelMapper.map(userDTO, MedicineDetails.class);
        petDetails.setMedicineId(PetId);
        return petDetails;
    }

    public MedicineResourceDTO toMedicineResourceDTO(Product product, Medicine acc, MedicineDetails details) {

        System.out.println(product);

        System.out.println(acc);
        System.out.println(details);


        Long productId=product.getId();
        Long subCategoryId=product.getSubCategoryId();
        Long UserId=product.getUserId();
        Long categoryId=product.getCategoryId();
        Long productStatusId=product.getProductStatusId();

        MedicineResourceDTO response=modelMapper.map(product, MedicineResourceDTO.class);
        response.setProductStatusId(productStatusId);
        response.setProductId(productId);
        response.setUserId(UserId);
        response.setCategoryId(categoryId);
        response.setSubCategoryId(subCategoryId);
        response.setProductName(product.getName());



        System.out.println(response);
        modelMapper.map(acc,response);
        System.out.println(response);
        modelMapper.map(details,response);
        System.out.println(response);



        return response;
    }



}
