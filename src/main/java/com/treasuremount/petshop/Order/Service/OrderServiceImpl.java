package com.treasuremount.petshop.Order.Service;



import com.treasuremount.petshop.Delivery.CustomService;
import com.treasuremount.petshop.Delivery.ShippmentDTO.ShipmentRequestDTO;
import com.treasuremount.petshop.Entity.Product;
import com.treasuremount.petshop.Entity.ShippingAddress;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.Order.DTO.OrderCustomDTO;
import com.treasuremount.petshop.Order.DTO.OrderGetOneDTO;
import com.treasuremount.petshop.Order.DTO.OrderStatusNameDTO;
import com.treasuremount.petshop.Order.Entity.OrderStatusHistory;
import com.treasuremount.petshop.Order.Entity.OrderStatusS;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.Entity.Sales;
import com.treasuremount.petshop.Order.Repository.OrderStatusHistoryRepo;
import com.treasuremount.petshop.Order.Repository.OrdersRepo;
import com.treasuremount.petshop.Order.Repository.SalesRepo;

import com.treasuremount.petshop.Repository.VendorRepo;
import com.treasuremount.petshop.Service.EmailService;
import com.treasuremount.petshop.Service.ProductServiceImpl;
import com.treasuremount.petshop.Service.ShippingService;
import com.treasuremount.petshop.Service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private OrderStatusHistoryRepo orderStatusHistoryRepo;

    @Autowired
    private VendorRepo vendorRepository;

   @Autowired
   private ProductServiceImpl productService;

    @Autowired
    private SalesRepo salesRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomService dehiveryCustomService;


    @Autowired
    private ShippingService userShippingService;

    private static final String application_name = "Treasure Mount";

    //order Related

   private static final Long OUT_OF_STOCK=5L;

    private static final Long RE_STOCK=6L;

    private static final Long AVAILABLE=7L;


    //orderStatus
    private static final Long RETURN=8L;

    private static final Long CONFIRMED=2L;

    private static final Long CANCEL=7L;

    private static final Long  DELIVERY=6L;

    private static final Long REFUND=9L;


    // payment Mode

    private static  final String PAYMENT_MODE="Prepaid";





    @Transactional
    public List<Orders> createOrder(List<Orders> orderList) {

        // Extract all unique product IDs from the order list
        List<Long> productIds = orderList.stream()
                .map(Orders::getProductId)
                .distinct()
                .collect(Collectors.toList());

        // Batch retrieve all products in a single query
        Map<Long, Product> productMap = productService.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        // Process each order

        System.err.println("all Product map"+ productMap);
        return orderList.stream().map(order -> {

            Product product = productMap.get(order.getProductId());

            if (product == null) {
                throw new RuntimeException("Product not found: " + order.getProductId());
            }

            if (product.getStockQuantity() < order.getQuantity()) {
                throw new RuntimeException("Insufficient product quantity for product: " + product.getId());
            }

            // Reduce product quantity
            Long items=product.getStockQuantity() - order.getQuantity();

            if(items <= product.getMinStockLevel()){
                //set the stock alert
                // 0
                if(items ==0){
                    product.setProductStatusId(OUT_OF_STOCK);

                }
                else{
                    product.setProductStatusId(RE_STOCK);
                }
                System.out.println("Im inside the  changing product Status"+items+""+product.getMinStockLevel());

            }
            product.setStockQuantity(items);
            System.out.println("product -- >" +product);
            Product savedProduct= productService.update(product,product.getId());

            Sales sales = createSalesRecord(order,savedProduct.getDiscount());
            salesRepo.save(sales);

            Vendor vendor = vendorRepository.findByUserId(product.getUserId());
            Long vendorId = (vendor != null) ? vendor.getUserId() : null;

            if (vendorId == null) {
                throw new RuntimeException("Vendor not found for product: " + product.getId());
            }

            // Save the order
            Orders savedOrder = ordersService.create(order);
            sendOrderConfirmationEmail(savedOrder, order.getUserId());
            sendOrderEmailForVendor(savedOrder, vendorId);
            updateOrderStatus(savedOrder.getId(),1L,"Initial Placement", order.getUserId());
            return savedOrder;
        }).collect(Collectors.toList());
}


    // Send Mail Notification for Order Confirmed
    private void sendOrderConfirmationEmail(Orders savedOrder, Long userId) {
        // Fetch user email using UserService
        String userEmail = userService.getUserEmailById(userId);

        // Construct the email content
        String subject = "Order Confirmation - Order #" + savedOrder.getUniqueOrderId();
        String body = "Dear customer,\n\nYour order with Order ID: " + savedOrder.getUniqueOrderId() + " has been successfully placed.\n\nThank you for shopping with us!";

        // Send email using the EmailService
        emailService.sendEmailNotification(userEmail, subject, body);
    }

    // Send Mail Notification for Vendor Order Confirmed
    private void sendOrderEmailForVendor(Orders savedOrder, Long vendorUserId) {
        // Fetch vendor using vendorUserId
        Optional<Vendor> vendor = vendorRepository.findByVendorUserId(vendorUserId);

        // Check if the vendor is present
        if (vendor.isPresent()) {
            // Fetch vendor email (safe to call get() as we already checked presence)
            String vendorEmail = vendor.get().getUser().getEmailId(); // Assuming getEmailId() is the correct method to get the email

            // Now, get the Product using the productId from the savedOrder
            Product product = productService.getOneById(savedOrder.getProductId());

            // Check if the product is present
            if (product != null) {
                // Construct the email content for the vendor
                String subject = "New Order Notification - Order #" + savedOrder.getUniqueOrderId();
                String body = "Dear Vendor,\n\nYour product with Order ID: " + savedOrder.getUniqueOrderId() + " has been ordered by a customer.\n\n"
                        + "Thank you for your service!\n\nOrder Details:\n"
                        + "Order ID: " + savedOrder.getUniqueOrderId() + "\n"
                        + "Product: " + product.getName() + "\n"
                        + "Quantity: " + savedOrder.getQuantity() + "\n"
                        + "Total Amount: " + savedOrder.getTotalAmount();

                // Send email to the vendor using the EmailService
                emailService.sendEmailNotification(vendorEmail, subject, body);
            } else {
                throw new RuntimeException("Product not found for Order ID: " + savedOrder.getUniqueOrderId());
            }
        } else {
            // If vendor is not found, throw an exception
            throw new RuntimeException("Vendor not found for userId: " + vendorUserId);
        }
    }





    /*
     *  basic need
     *  unit price
     *  quantity
     *  tax
     *  discount
     *
     *  unit price+quantity => sub total
     *   total_amount = (unit price * (quantity * tax * discount)) +  shipping charge
     * */


    private Sales createSalesRecord(Orders order,double discount) {

        Sales sales = new Sales();
        sales.setUserId(order.getUserId());
        sales.setProductId(order.getProductId());
        sales.setQuantity(order.getQuantity());
        sales.setSalesDate(new Date());
        sales.setUnitPrice(order.getUnitPrice());
        sales.setTotalAmount(order.getTotalAmount());
        sales.setTax(order.getTax());
        sales.setDiscount(discount);
        return sales;
    }


    public List<Orders> getAllByUser(Long userId){
        return ordersService.getOrdersByUserId(userId);
    }

    public Orders getOne(Long userId){
        return ordersService.getOneById(userId);
    }

    public OrderGetOneDTO getOneCustom(Long userId){
        return ordersService.getOneByIdCustom(userId);
    }


    public List<OrderCustomDTO> getAll(){
        return ordersService.getAllByAdmin();
    }

    public List<Orders> naiveGetAll(){
        return ordersService.getAll();
    }

    public List<OrderCustomDTO> getByUserandVendor(Long productId,Boolean isUser){
        return ordersService.getAllByCustom(productId,isUser);
    }

    public List<OrderCustomDTO> getByUserandVendorv1(Long productId, Boolean isUser, Long orderStatusId,LocalDate startDate, LocalDate endDate){
        return ordersService.getAllByCustomv1(productId,isUser,orderStatusId,startDate,endDate);
    }
/*
    void createInitialOrderStatusHistory(Long OrderId,Long userId){
        OrderStatusHistory history=new OrderStatusHistory();
        history.setOrderId(OrderId);
        history.setUserId(userId);
        history.setPreviousStatus(1L);
        history.setNewStatus(1L);
        history.setReason("Initial Placement");
        history.setCreatedDate(new Date());

    }*/


    private ShipmentRequestDTO mappingWithNecessaryDetails(ShipmentRequestDTO dto,ShippingAddress userAddress){
         ShipmentRequestDTO.Shipment shipment= dto.getShipments().get(0);
         String name = userAddress.getFullName();
         String add=userAddress.getAddressLine1();
         String pin=userAddress.getPostalCode();
         String phone=userAddress.getPhoneNumber();
         String city=userAddress.getCity();
         shipment.setCity(city);
         shipment.setPhone(phone);
         shipment.setName(name);
         shipment.setAdd(add);
         shipment.setPin(pin);

         shipment.setPayment_mode(PAYMENT_MODE);

return dto;

    }
    @Transactional
    public OrderStatusHistory updateOrderStatus(Long orderId, Long orderStatusId, String reason, Long userId) {
        Orders order = ordersRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

       /* if(orderStatusId.equals(CONFIRMED)){

            // initiate the shipment for order
            ShipmentRequestDTO dto=new ShipmentRequestDTO();
            ShippingAddress address=userShippingService.getOneById(order.getShippingAddressId());
            mappingWithNecessaryDetails(dto,address);
            dto.getShipments().get(0).setOrder(order.getUniqueOrderId());


            Date orderDate = order.getOrderDate(); // Current Date

            // Convert Date to Instant
            Instant instant = orderDate.toInstant();

            // Format Instant to ISO 8601 format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .withZone(ZoneId.of("UTC"));

            String formattedDate = formatter.format(instant);
            System.out.println(formattedDate); // Example: 2025-02-19T18:30:00.000Z

            dto.getShipments().get(0).setOrder_date(formattedDate);

            dehiveryCustomService.ShipmentCreation(dto);



        }*/

       if(orderStatusId.equals(DELIVERY)) order.setDeliveryDate(new Date());

       Long previousId= order.getOrderStatusId();
        order.setOrderStatusId(orderStatusId);
        ordersRepo.save(order);

        //creating and saving the log

        OrderStatusHistory statusHistory = new OrderStatusHistory();
        statusHistory.setId(0L);
        statusHistory.setOrderId(orderId);
        statusHistory.setPreviousStatus(previousId);
        statusHistory.setOrderStatusId(orderStatusId);
         statusHistory.setUserId(userId);
         statusHistory.setReason(reason);
         statusHistory.setCreatedDate(new Date());


        return orderStatusHistoryRepo.save(statusHistory);
    }

    /*public List<OrderStatusNameDTO> getOrderStatusName(Long userId) {
        List<Orders> all = ordersRepo.findByUserId(userId);
        List<OrderStatusNameDTO> response = new ArrayList<>();
        for (Orders assignedOrders : all) {
            OrderStatusS orderStatus = assignedOrders.getOrderStatusS();
            OrderStatusNameDTO assignedOrderStatusName = new OrderStatusNameDTO(
                    assignedOrders.getShippingAddressId(),
                    assignedOrders.getId(),
                    assignedOrders.getUserId(),
                    assignedOrders.getProductId(),
                    assignedOrders.getUnitPrice(),
                    assignedOrders.getQuantity(),
                    assignedOrders.getTax(),
                    assignedOrders.getShippingCharge(),
                    assignedOrders.getSubtotal(),
                    assignedOrders.getOrderStatusId(),
                    orderStatus != null ? orderStatus.getName() : "",  // Check if orderStatus is null
                    assignedOrders.getTotalAmount(),
                    assignedOrders.getOrderDate(),
                    assignedOrders.getDeliveryDate(),
                    assignedOrders.getCancelled(),
                    assignedOrders.getReturned()
            );
            response.add(assignedOrderStatusName);
        }

        return response;
    }
*/

    public List<OrderCustomDTO> getOrderStatusName(Long userId) {

        try{

            List<Object[]> all = ordersRepo.findByUserIdCustom(userId);
            return ordersService.convertIntoOrderCustomDTO(all);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }



    }


    public static Long getRE_STOCK() {
        return RE_STOCK;
    }

    public static Long getOUT_OF_STOCK() {
        return OUT_OF_STOCK;
    }
    public static Long getAVAILABLE() {
        return AVAILABLE;
    }

    public static Long getCANCEL() {
        return CANCEL;
    }
    public static Long getRETURN() {
        return RETURN;
    }

    public static Long getREFUND(){
        return REFUND;
    }



}

