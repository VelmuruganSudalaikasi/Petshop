/*
package com.treasuremount.petshop.Order.Service;

import com.treasuremount.petshop.Order.DTO.OrdersDTO;
import com.treasuremount.petshop.Order.Entity.OrderItems;
import com.treasuremount.petshop.Order.Entity.OrderStatusHistory;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.Exception.InsufficientStockException;
import com.treasuremount.petshop.Order.Exception.InvalidOrderException;
import com.treasuremount.petshop.Order.Repository.OrderItemsRepo;
import com.treasuremount.petshop.Order.Repository.OrderStatusHistoryRepo;
import com.treasuremount.petshop.Order.Repository.OrdersRepo;
import com.treasuremount.petshop.Service.*;
import com.treasuremount.petshop.utils.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

public class TemporyOrderService {

    private final OrdersRepo orderRepository;
    private final OrderItemsRepo orderItemRepository;
    private final OrderStatusHistoryRepo statusHistoryRepository;
    private final Mapper mapper;
    private final ProductServiceImpl productService;
    private final FoodServiceImpl foodService;
    private final AccessoriesImpl accessoryService;
    private final PetService petService;
    private final OrderLogService orderLogService;
    private final OrderStatusHistoryService orderStatusHistoryService;
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderItemsService orderItemsService;


    @Autowired
    private OrderStatusHistoryService orderStatusHistoryService;

    // Constructor injection
    public TemporyOrderService(OrdersRepo orderRepository,
                        OrderItemsRepo orderItemRepository,
                        OrderStatusHistoryRepo statusHistoryRepository,
                        Mapper mapper,
                        ProductServiceImpl productService,
                        FoodServiceImpl foodService,
                        AccessoriesImpl accessoryService,
                        PetService petService,
                        OrderLogService orderLogService,
                        OrderStatusHistoryService orderStatusHistoryService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.mapper = mapper;
        this.productService = productService;
        this.foodService = foodService;
        this.accessoryService = accessoryService;
        this.petService = petService;
        this.orderLogService = orderLogService;
        this.orderStatusHistoryService = orderStatusHistoryService;
    }

    @Transactional
    public OrdersDTO createOrder(OrdersDTO orderDTO) throws Exception {

        validateOrderInput(orderDTO);


        StockValidationResult validationResult = validateAndReserveStock(
                orderDTO.getProductId(),
                orderDTO.getCategory_Id(),
                orderDTO.getQuantity()
        );

        if (!validationResult.isAvailable()) {
            throw new InsufficientStockException("Insufficient stock for the ordered quantity");
        }

        // Create order
        Orders order = createOrderEntity(orderDTO);

        // Create order items
        OrderItems orderItems = createOrderItemsEntity(order, orderDTO);

        // Create initial order status history
        createInitialOrderStatusHistory(order,orderDTO);

        // Trigger post-order events (if needed)
        triggerPostOrderProcessing(order, orderItems);

        return mapper.toEntity(order, orderItems);
    }

    private void validateOrderInput(OrdersDTO orderDTO) throws InvalidOrderException {
        if (orderDTO == null) {
            throw new InvalidOrderException("Order cannot be null");
        }

        if (orderDTO.getQuantity() <= 0) {
            throw new InvalidOrderException("Order quantity must be positive");
        }

        // Add more validation rules as needed
    }

    private class StockValidationResult {
        private boolean available;
        private Integer currentStock;

        public StockValidationResult(boolean available, Integer currentStock) {
            this.available = available;
            this.currentStock = currentStock;
        }

        public StockValidationResult() {
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public Integer getCurrentStock() {
            return currentStock;
        }

        public void setCurrentStock(Integer currentStock) {
            this.currentStock = currentStock;
        }

        // Getters and constructors
    }

    private StockValidationResult validateAndReserveStock(Long productId, Long categoryId, int requestedQuantity) {
        Integer currentStock = getStockByCategory(productId, categoryId);

        if (currentStock == null || currentStock < requestedQuantity) {
            return new StockValidationResult(false, currentStock);
        }

        // Optionally, implement a stock reservation mechanism
        reserveStock(productId, categoryId, requestedQuantity);

        return new StockValidationResult(true, currentStock);
    }

    private void triggerPostOrderProcessing(Orders order, OrderItems orderItems) {
        // Send order confirmation email
        emailService.sendOrderConfirmation(order);

        // Update product popularity metrics
        productMetricsService.incrementOrderCount(orderItems.getProductId());

        // Trigger inventory management processes
        inventoryService.checkRestockLevels(orderItems.getProductId());
    }

    public Orders createOrderEntity(OrdersDTO ordersDTO ){
        Orders order=mapper.orderToEntity(ordersDTO);
        return ordersService.create(order);
    }

    public OrderItems createOrderItemsEntity (Orders order,OrdersDTO ordersDTO ){
        OrderItems request=mapper.orderItemsToEntity(ordersDTO,order.getId());
        OrderItems response=orderItemsService.create(request);
        return response;
    }

    public OrderStatusHistory createInitialOrderStatusHistory(){


    }


}
*/
