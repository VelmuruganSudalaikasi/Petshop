package com.treasuremount.petshop.Order.Service;

import com.treasuremount.petshop.Order.Entity.OrderStatusHistory;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.Repository.OrderStatusHistoryRepo;
import com.treasuremount.petshop.Order.Repository.OrdersRepo;
import com.treasuremount.petshop.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
public class OrderStatusHistoryService implements Service<OrderStatusHistory,Long> {
    @Autowired
    private OrderStatusHistoryRepo repository;


    @Override
    public OrderStatusHistory create(OrderStatusHistory d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<OrderStatusHistory> getAll() {
        try {
            return repository.findAll();

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
    public OrderStatusHistory getOneById(Long id) {
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
    public OrderStatusHistory update(OrderStatusHistory d, Long id) {
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

    public Date getDeliveredDate(String newStatus){
        List<Object[]> results=repository.findDistinctOrderStatusHistoryWithProductIdAndStatus(newStatus);

        for (Object[] result : results) {
            Long orderId = (Long) result[0];
            Long productId = (Long) result[1];
            String status = (String) result[2];
            Date createdAt = (Date) result[3];



            System.out.println("Order ID: " + orderId);
            System.out.println("Product ID: " + productId);
            System.out.println("Status: " + status);
            System.out.println("Created At: " + createdAt);

            return (Date)createdAt;

            // Add custom logic or return as DTO

        }


      return null;

    }

}


