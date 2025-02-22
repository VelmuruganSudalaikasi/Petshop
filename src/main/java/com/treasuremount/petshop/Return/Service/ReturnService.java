package com.treasuremount.petshop.Return.Service;

import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.Repository.OrdersRepo;
import com.treasuremount.petshop.Order.Service.OrderServiceImpl;
import com.treasuremount.petshop.Repository.ProductRepo;
import com.treasuremount.petshop.Return.DTO.ReturnDTO;
import com.treasuremount.petshop.Return.Entity.Cancel;
import com.treasuremount.petshop.Return.Entity.Return;
import com.treasuremount.petshop.Return.Repository.ReturnRepo;
import com.treasuremount.petshop.Service.EmailService;
import com.treasuremount.petshop.Service.Service;
import com.treasuremount.petshop.Service.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@org.springframework.stereotype.Service
public class ReturnService implements Service<Return,Long> {
    @Autowired
    private ReturnRepo repository;

    @Autowired
    private OrdersRepo ordersRepository;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserServiceImpl userService;



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

    public List<ReturnDTO> getNotApprovedRequests(Long vendorUserId) {

        List<Return> response= repository.findNotApprovedRequestsByVendorUserId(vendorUserId);
       return convertToReturnDTO(response);
    }

    public List<ReturnDTO> convertToReturnDTO(List<Return> response){
        List<ReturnDTO> returnDTOList=new ArrayList<>();
        for(Return res: response){
            ReturnDTO obj=new ReturnDTO(res);

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



    @Transactional
    public ReturnDTO approveReturnRequest(ReturnDTO dtoRequest) {
        try {
            // Fetch the return entity
            Return request=new ReturnDTO().DtoToReturn(dtoRequest);
            boolean isValid=repository.existsById(request.getId());
            if(!isValid) {
                throw  new RuntimeException("Orders Id not found");
            }

            Orders order;
            order = ordersRepository.findById(request.getOrderId()).get();

            Product product=productRepo.findById(request.getProductId()).get();
            order.setReturned(true);
            ordersRepository.save(order);
            ChangeOrderStatusToReturn(order.getId(),"User reason",product.getUserId()) ;
            Return response=repository.save(request);
            response.setProduct(product);
            response.setOrders(order);
            List<Return> responseList = Collections.singletonList(response);
            sendOrderReturnEmail(response, product.getUserId());
            return convertToReturnDTO(responseList).get(0);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Send Mail Notification for Order Returned
    private void sendOrderReturnEmail(Return returnRequest, Long userId) {
        // Fetch user email using UserService
        String userEmail = userService.getUserEmailById(userId);

        // Construct the email content
        String subject = "Order Return Confirmation - Order #" + returnRequest.getOrders().getUniqueOrderId();
        String body = "Dear Customer,\n\n"
                + "Your return request for Order ID: " + returnRequest.getOrders().getUniqueOrderId()
                + " has been successfully processed.\n\n"
                + "Product: " + returnRequest.getProduct().getName() + "\n"
                + "Reason: " + returnRequest.getReturnReason() + "\n\n"
                + "If you have any questions, feel free to contact our support team.\n\n"
                + "Thank you for shopping with us!";

        // Send email using the EmailService
        emailService.sendEmailNotification(userEmail, subject, body);
    }


    public void ChangeOrderStatusToReturn( Long orderId,  String reason, Long userId){

        orderService.updateOrderStatus(orderId,OrderServiceImpl.getRETURN(),reason,userId);
//        Long orderId, Long orderStatusId, String reason, Long userId
    }


    public List<Return> getCancelListByUserId(Long userId, Boolean status){
        try
        {
            List<Return> lst = repository.findAllByUserId(userId,status);
            return lst;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }




    @Override
    public Return create(Return d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Return> getAll() {
        try {
            return repository.findAll();

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }


    public List<Return> getAll(Long userId) {
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
    public Return getOneById(Long id) {
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
    public Return update(Return d, Long id) {
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


