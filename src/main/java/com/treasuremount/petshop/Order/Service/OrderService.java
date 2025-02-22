/*
package com.treasuremount.petshop.Order.Service;


import com.treasuremount.petshop.Order.DTO.OrdersDTO;
import com.treasuremount.petshop.Order.Entity.OrderItems;
import com.treasuremount.petshop.Order.Entity.OrderLog;
import com.treasuremount.petshop.Order.Entity.OrderStatusHistory;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.OrderStatus;
import com.treasuremount.petshop.Order.Repository.OrderItemsRepo;
import com.treasuremount.petshop.Order.Repository.OrderStatusHistoryRepo;
import com.treasuremount.petshop.Order.Repository.OrdersRepo;
import com.treasuremount.petshop.Service.*;
import com.treasuremount.petshop.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
    public class OrderService {
        @Autowired
        private OrdersRepo orderRepository;

        @Autowired
        private OrderItemsRepo orderItemRepository;

        @Autowired
        private OrderStatusHistoryRepo statusHistoryRepository;

        @Autowired
        Mapper mapper;

        @Autowired
        ProductServiceImpl productService;

        @Autowired
        FoodServiceImpl foodService;

        @Autowired
        AccessoriesImpl AccessoryService;

        @Autowired
        PetService petService;

        @Autowired
        OrderLogService orderLogService;

        @Autowired
        OrderStatusHistoryService orderStatusHistoryService;


        private final static Boolean isOrdered=false;
        private final static Boolean isReturned=true;


        @Transactional
        public OrdersDTO createOrder(OrdersDTO orderDTO) {
            int orderQuantity=orderDTO.getQuantity();
            Long categoryId=orderDTO.getCategory_Id();
            Long productId=orderDTO.getProductId();


            Integer stockQuantity=null;

            if(categoryId==3){

                stockQuantity= foodService.getStock(productId);
                System.out.println("foodservice");

            }else if(categoryId==2){

                stockQuantity= AccessoryService.getStock(productId);
                System.out.println("accessoryservice");

            }else if(categoryId==1){

                stockQuantity=  petService.getStock(productId);
                System.out.println("petservice");

            }else{
               throw  new RuntimeException("CategoryId not found");
            }


            System.out.println(orderQuantity+"," +stockQuantity);
            if(orderQuantity < 0 || orderQuantity > stockQuantity ){
                System.err.println("fix the quantity of the item");
                return null;
            }

            //decrease the stock quantity
            Integer old=stockQuantity;

            stockQuantity-=orderQuantity;
            if(categoryId==3){

                 foodService.updateStockQuantity(productId,stockQuantity);

            }else if(categoryId==2){

              AccessoryService.updateStockQuantity(productId,stockQuantity);

            }else if(categoryId==1){

                  petService.updateStockQuantity(productId,stockQuantity);

            }else{
                throw  new RuntimeException("ERR Happen while updating");
            }

            createLog(productId,categoryId,old,stockQuantity);

            orderDTO.setOrderDate(LocalDateTime.now());
            orderDTO.setStatus(OrderStatus.PENDING);

            //saved the order here
            Orders order=mapper.orderToEntity(orderDTO);
            Orders orderResponse=orderRepository.save(order);

            //saved the orderItem here

            OrderItems orderItems=mapper.orderItemsToEntity(orderDTO,orderResponse.getId());
            OrderItems orderItemsResponse=orderItemRepository.save(orderItems);

            createInitialStatusHistory(orderResponse);



if(!productService.setActiveStatus(orderItemsResponse,isOrdered)){
               throw  new RuntimeException("Product Not Found");
            }

            return mapper.toEntity(orderResponse,orderItemsResponse);
        }


        @Transactional
        public void  createLog(Long productId,Long categoryId,int old,int newQuantity){

            OrderLog orderlog=new OrderLog();
            orderlog.setProductId(productId);
            orderlog.setCategoryId(categoryId);
            orderlog.setOldQuantity(old);
            orderlog.setNewQuantity(newQuantity);
            orderlog.setCreatedDate(new Date());
            orderLogService.create(orderlog);


        }


        @Transactional
        public OrderStatusHistory updateOrderStatus(Long orderId, OrderStatus newStatus, String reason,Long userId) {
            Orders order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));


            OrderStatusHistory statusHistory = new OrderStatusHistory();
            statusHistory.setUserId(userId);
            statusHistory.setOrderId(order.getId());
            statusHistory.setPreviousStatus(order.getStatus());
            statusHistory.setNewStatus(newStatus);
            statusHistory.setReason(reason);
            statusHistory.setCreatedDate(LocalDateTime.now());

            // Update order status
            order.setStatus(newStatus);

            // Handle specific status changes
            if (newStatus == OrderStatus.CANCELLED) {
                order.setCancelled(true);
            } else if (newStatus == OrderStatus.RETURNED) {
                order.setReturned(true);



            }
            OrderStatusHistory response= statusHistoryRepository.save(statusHistory);

            return response;
        }

        private void createInitialStatusHistory(Orders order) {
            OrderStatusHistory initialStatus = new OrderStatusHistory();
            initialStatus.setUserId(order.getUserId());
            initialStatus.setOrderId(order.getId());
            initialStatus.setPreviousStatus(OrderStatus.PENDING);
            initialStatus.setNewStatus(OrderStatus.PENDING);
            initialStatus.setReason("Order Created");
            initialStatus.setCreatedDate(LocalDateTime.now());

            statusHistoryRepository.save(initialStatus);
        }


        @Transactional
        public Boolean ReturnIsEligible(Long productId) {

            Integer returnPolicyDays = productService.isReturnAvailable(productId);
            if (returnPolicyDays == null) return false;

            try {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


                Date deliveredDate = orderStatusHistoryService.getDeliveredDate("PENDING");
                if (deliveredDate == null) {
                    System.out.println("Delivered date is not available");
                    return false;
                }


                String deliveredDateString = simpleDateFormat.format(deliveredDate);
                System.out.println("Delivered Date (String): " + deliveredDateString);

                // Parse the delivered date to LocalDate for further calculations
                LocalDate deliveryDate = deliveredDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();


                LocalDate currentDate = LocalDate.now();
                LocalDate lastReturnDate = deliveryDate.plusDays(returnPolicyDays);

                if (!currentDate.isAfter(lastReturnDate)) {
                    System.out.println("Eligible for Return");
                    return true;
                } else {
                    System.out.println("Return Window Expired");
                    return false;
                }
            } catch (Exception e) {

                System.err.println("Error while calculating return eligibility: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }



}

*/
