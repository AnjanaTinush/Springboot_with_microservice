package com.shop.order.service;

import com.shop.order.dto.OrderDTO;
import com.shop.order.model.Orders;
import com.shop.order.repo.OrderRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrderDTO> getAllOrders(){
        List<Orders> ordersList=orderRepo.findAll();
        return modelMapper.map(ordersList,new TypeToken<List<OrderDTO>>(){}.getType());
    }


    public OrderDTO saveUser(OrderDTO orderDTO){
        orderRepo.save(modelMapper.map(orderDTO,Orders.class));
        return orderDTO;
    }

    public OrderDTO getOrderById(Integer orderId){
        Orders order = orderRepo.getUserById(orderId);
        return modelMapper.map(order,OrderDTO.class);
    }

    public OrderDTO updateOrder(OrderDTO orderDTo){
        orderRepo.save(modelMapper.map(orderDTo,Orders.class));
        return  orderDTo;
    }

    public String deleteOrder(OrderDTO orderDTO){
        orderRepo.delete(modelMapper.map(orderDTO,Orders.class));
        return "Order deleted";
    }

    public String deleteOrder_id(Integer orderId){
        orderRepo.deleteById(orderId);
        return "Order deleted";
    }

}
