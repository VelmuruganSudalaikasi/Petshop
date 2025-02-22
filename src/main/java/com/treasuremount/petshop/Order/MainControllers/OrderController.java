package com.treasuremount.petshop.Order.MainControllers;
import com.treasuremount.petshop.Order.DTO.OrderCustomDTO;


import com.treasuremount.petshop.Order.DTO.OrderGetOneDTO;
import com.treasuremount.petshop.Order.DTO.OrderStatusNameDTO;
import com.treasuremount.petshop.Order.Entity.OrderStatusHistory;
import com.treasuremount.petshop.Order.Entity.OrderStatusS;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.OrderStatus;
import com.treasuremount.petshop.Order.Service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/orders")
@Transactional
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/add")
    public ResponseEntity<?> createOrder(@RequestBody List<Orders> orders) {
        try{
            List<Orders> createdOrder = orderService.createOrder(orders);
            if(createdOrder==null) return new ResponseEntity<>(createdOrder, HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
            return ResponseEntity.ok(createdOrder);
        }catch (Exception e){
            e.printStackTrace();
              return ResponseEntity.ok(e.getMessage());
        }

    }

    @PatchMapping("/updateOrderStatus/{orderId}")
    public ResponseEntity<OrderStatusHistory> updateOrderStatus(@PathVariable Long orderId, @RequestParam Long orderStatusId,
                                                                @RequestParam Long userId, @RequestParam String reason) {

        try {
            OrderStatusHistory updatedOrder = orderService.updateOrderStatus(orderId, orderStatusId, reason , userId);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*@GetMapping("return/{ProductId}")
    public ResponseEntity<Boolean> returnSatus(@PathVariable Long ProductId){
        Boolean status=orderService.ReturnIsEligible(ProductId);
        return ResponseEntity.ok(status);

    }*/
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<OrderCustomDTO>> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<OrderCustomDTO> orders = orderService.getOrderStatusName(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getOne/{OrderId}")
    public OrderGetOneDTO getOne(@PathVariable Long OrderId) {
        return orderService.getOneCustom(OrderId);
    }

    @GetMapping("naive/getAll/")
    public ResponseEntity<List<Orders>> naiveGetAll(){
        List<Orders> lst=orderService.naiveGetAll();
        return ResponseEntity.ok(lst);

    }


    @GetMapping("/getAll")
    public ResponseEntity<List<OrderCustomDTO>> getAll(){
        List<OrderCustomDTO> lst=orderService.getAll();
        return ResponseEntity.ok(lst);



    }
    @GetMapping("/getAllUserandVendor/{userId}")
    public List<OrderCustomDTO> getOrdersByUserId(@PathVariable Long userId,
                     @RequestParam(required =true,defaultValue = "0") Boolean isUser) {
        return orderService.getByUserandVendor(userId,isUser);
    }

    @GetMapping("v1/getAllUserandVendor/")
    public List<OrderCustomDTO> getOrdersByUserIdv1(@RequestParam Long userId,
                       @RequestParam(required =false)
                       Boolean isUser,
                       @RequestParam(required = true, value = "OrderStatus",defaultValue = "0")
                       Long orderStatusId,
                       @RequestParam (required = false) LocalDate startDate ,
                        @RequestParam(required = false) LocalDate endDate
                                                    )

    {

        return orderService.getByUserandVendorv1(userId,isUser,orderStatusId,startDate,endDate);
    }



}
