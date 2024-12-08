package com.shop.order.controller;

import com.shop.order.dto.OrderDTO;
import com.shop.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/getorders")

    public List<OrderDTO> getUser(){
        return orderService.getAllOrders();
    }

    @GetMapping("/order/{orderid}")
    public OrderDTO getUserById(@PathVariable Integer orderid){
        return orderService.getOrderById(orderid);
    }

    @PostMapping("/addorder")
    public OrderDTO saveUser(@RequestBody OrderDTO orderDTO){
        return orderService.saveUser(orderDTO);

    }

    @PutMapping("/updateorder")
    public OrderDTO updateuser(@RequestBody OrderDTO orderDTo){
        return orderService.updateOrder(orderDTo);
    }

    @DeleteMapping("/deleteorder")
    public String deleteOrder(@RequestBody OrderDTO orderDTo){
        return orderService.deleteOrder(orderDTo);
    }

    @DeleteMapping("deleteorder/{orderId}")
    public String deleteOrderById(@PathVariable Integer orderId) {
        return orderService.deleteOrderById(orderId);
    }

}
