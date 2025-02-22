package com.treasuremount.petshop.Return.Service;


import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.Repository.OrdersRepo;
import com.treasuremount.petshop.Order.Service.OrderServiceImpl;
import com.treasuremount.petshop.Repository.ProductRepo;
import com.treasuremount.petshop.Repository.UserRepo;
import com.treasuremount.petshop.Return.DTO.CancelDTO;
import com.treasuremount.petshop.Return.DTO.ReturnDTO;
import com.treasuremount.petshop.Return.Entity.Cancel;
import com.treasuremount.petshop.Return.Entity.Return;
import com.treasuremount.petshop.Return.Repository.CancelRepo;
import com.treasuremount.petshop.Return.Repository.ReturnRepo;
import com.treasuremount.petshop.Service.EmailService;
import com.treasuremount.petshop.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class CancelService implements Service<Cancel,Long> {
    @Autowired
    private CancelRepo repository;

    @Autowired
    private OrdersRepo ordersRepository;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    private static final String application_name = "Treasure Mount";

    /*public List<Product> getEligibleProductsForReturn(Long userId) {
        List<Orders> orders = ordersRepository.findEligibleOrdersByUserId(userId);

        return orders.stream()
                .filter(order -> isEligibleForReturn(order))
                .map(Orders::getProduct)
                .collect(Collectors.toList());
    }



    private boolean isEligibleForReturn(Orders order) {
        Product product = order.getProduct();
        if (product.getReturnWithin() == null) {
            return false;
        }

        // Convert Dates to LocalDate
        LocalDate orderDate = order.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();

        // Calculate difference in days
        long diffInDays = ChronoUnit.DAYS.between(orderDate, currentDate);

        return diffInDays <= product.getReturnWithin();
    }*/

    public List<CancelDTO> getNotApprovedRequests(Long vendorUserId) {
        List<Cancel> response=repository.findNotApprovedRequestsByVendorUserId(vendorUserId);
        return convertToCancelDTO(response);
    }


    public List<CancelDTO> convertToCancelDTO(List<Cancel> response){
        List<CancelDTO> returnDTOList=new ArrayList<>();
        for(Cancel res: response){
            CancelDTO obj=new CancelDTO(res);
            Orders orders=res.getOrders();
            if(orders!=null){

                obj.setReturnQuantity(orders.getQuantity());
                obj.setUniqueOrderId(orders.getUniqueOrderId());
            }
            Product product=res.getProduct();
            if(product !=null){

                obj.setStockQuantity(product.getStockQuantity());
            }
            returnDTOList.add(obj);
        }
        return returnDTOList;
    }


   public List<Cancel> getCancelListByUserId(Long userId,Boolean status){
        try
        {
            List<Cancel> lst = repository.findAllByUserId(userId,status);
            return lst;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


   }


    public CancelDTO approveReturnRequest(CancelDTO cancelDTO) {
        try {
            // Fetch the return entity
            Cancel request=new CancelDTO().DtoToCancel(cancelDTO);
            boolean isValid=repository.existsById(request.getId());
            if(!isValid) {
                throw  new RuntimeException("Orders Id not found");
            }

            Orders order;
            order = ordersRepository.findById(request.getOrderId()).get();
            Product product=productRepo.findById(request.getProductId()).get();


            order.setCancelled(true);
            ordersRepository.save(order);

            //change the order status
            ChangeOrderStatusToReturn(order.getId(),"User reason",product.getUserId()) ;

            Cancel cancel= repository.save(request);
            cancel.setOrders(order);
            cancel.setProduct(product);
            List<Cancel>  response=Collections.singletonList(cancel);
            return convertToCancelDTO(response).get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void ChangeOrderStatusToReturn( Long orderId,  String reason, Long userId){

        orderService.updateOrderStatus(orderId, OrderServiceImpl.getCANCEL(),reason,userId);
//        Long orderId, Long orderStatusId, String reason, Long userId
    }


    public void cancelProduct(String userEmail, String userName, String productName) {
        String subject = "Your Product Cancellation Request Has Been Processed";
        String body = "Dear " + userName + ",\n\n" +
                "We wanted to inform you that your request to cancel the product \"" + productName + "\" has been successfully processed.\n\n" +
                "We understand that sometimes changes need to be made, and we're here to support you through it. The product you canceled has now been removed from your order list.\n\n" +
                "If you have any further questions or need assistance with other products, feel free to reach out to our customer support team.\n\n" +
                "Thank you for being a valued part of the " + application_name + " community!\n\n" +
                "Best regards,\nThe " + application_name + " Team\n\n" +
                "---\nIf you did not request this cancellation, please contact us immediately.";

        emailService.sendEmailNotification(userEmail, subject, body);
    }


    @Override
    public Cancel create(Cancel d) {
        try {

            Cancel cancel=repository.save(d);
            Optional<User> user = userRepo.findById(d.getUserId());
            Optional<Product> product = productRepo.findById(d.getProductId());
            cancelProduct(user.get().getEmailId(), user.get().getFirstName(), product.get().getName());
            return cancel;

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Cancel> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }


    public List<Cancel> getAll(Long userId) {
        try {
            if(userId !=0 ){

                return repository.returnListByVendorId(userId);
            }
            return getAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

//    @Override
//    public List<UserDTOs> getAllUser() {
//        try {
//            List<User> all = repository.findAll();
//            List<UserDTOs> response = new ArrayList<>();
//
//            for (User assignedUser : all) {
//                Role role = assignedUser.getRole();
//
//                UserDTOs assignedUserDTO = new UserDTOs(
//                        assignedUser.getId(),
//                        assignedUser.getFirstName(),
//                        assignedUser.getLastName(),
//                        assignedUser.getEmailId(),
//                        assignedUser.getMobileNumber(),
//                        assignedUser.getPassword(),
//                        assignedUser.getConfirmPassword(),
//                        assignedUser.getActiveStatus(),
//                        assignedUser.getRoleId(),
//                        role != null ? role.getName() : null,
//                        assignedUser.getUserProfile(),
//                        assignedUser.getCreatedDate(),
//                        assignedUser.getModifiedDate()
//                );
//
//                response.add(assignedUserDTO);
//            }
//
//            return response;
//        } catch (Exception ex) {
//            return Collections.emptyList();
//        }
//    }



    @Override
    public Cancel getOneById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }


//    @Override
//    public UserDTOs getOneById(Long id) {
//        try {
//            User assignedUser = repository.findById(id).orElse(null);
//
//            if (assignedUser == null) {
//                return null; // or throw an exception if preferred
//            }
//
//            Role role = assignedUser.getRole();
//            return new UserDTOs(
//                    assignedUser.getId(),
//                    assignedUser.getFirstName(),
//                    assignedUser.getLastName(),
//                    assignedUser.getEmailId(),
//                    assignedUser.getMobileNumber(),
//                    assignedUser.getPassword(),
//                    assignedUser.getConfirmPassword(),
//                    assignedUser.getActiveStatus(),
//                    assignedUser.getRoleId(),
//                    role != null ? role.getName() : null,
//                    assignedUser.getUserProfile(),
//                    assignedUser.getCreatedDate(),
//                    assignedUser.getModifiedDate()
//            );
//        } catch (Exception ex) {
//            return null;
//        }
//    }



    @Override
    public Cancel update(Cancel d, Long id) {
        try {
            if (repository.existsById(id)) {

                d.setId(id);
                return create(d);
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


