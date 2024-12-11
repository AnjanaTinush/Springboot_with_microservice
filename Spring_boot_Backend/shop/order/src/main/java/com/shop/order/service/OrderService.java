package com.shop.order.service;

import com.shop.inventory.dto.InventoryDTO;
import com.shop.order.common.ErrroOrderResponse;
import com.shop.order.common.OrderResponse;
import com.shop.order.common.SuccessOrderResponse;
import com.shop.order.dto.OrderDTO;
import com.shop.order.model.Orders;
import com.shop.order.repo.OrderRepo;
import com.shop.product.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final WebClient inventoryWebClient;
    private final WebClient productWebClient;
    private final OrderRepo orderRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(WebClient inventoryWebClient, WebClient productWebClient, OrderRepo orderRepo, ModelMapper modelMapper) {
        this.inventoryWebClient = inventoryWebClient;
        this.productWebClient = productWebClient;
        this.orderRepo = orderRepo;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders() {
        List<Orders> ordersList = orderRepo.findAll();
        return modelMapper.map(ordersList, new TypeToken<List<OrderDTO>>() {}.getType());
    }

    public OrderResponse saveOrder(OrderDTO orderDTO) {
        Integer itemId = orderDTO.getItemId();

        try {
            // Fetch inventory details from the external service
            InventoryDTO inventoryResponse = inventoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/item/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

            if (inventoryResponse == null) {
                return new ErrroOrderResponse("Invalid inventory response");
            }

            // Check for negative quantity
            if (inventoryResponse.getQuantity() < 0) {
                return new ErrroOrderResponse("Item not available");
            }

            Integer productId = inventoryResponse.getProductId();

            ProductDTO productResponse = productWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/product/{productid}").build(productId))
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();

            if (productResponse == null) {
                return new ErrroOrderResponse("Invalid product response");
            }

            // Check if inventory exists and has sufficient quantity
            if (inventoryResponse.getQuantity() > 0) {
                if (productResponse.getForSale() == 1) {
                    orderRepo.save(modelMapper.map(orderDTO, Orders.class));
                    return new SuccessOrderResponse(orderDTO);
                } else {
                    return new ErrroOrderResponse("Item is not for sale");
                }
            } else {
                return new ErrroOrderResponse("Item not available, please try later");
            }

        } catch (WebClientResponseException e) {

            if(e.getStatusCode().is5xxServerError()) {
                return new ErrroOrderResponse("Item not found");
            }
        }
        return null;
    }

    public OrderDTO getOrderById(Integer orderId) {
        Orders order = orderRepo.getUserById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }

    public OrderDTO updateOrder(OrderDTO orderDTO) {
        orderRepo.save(modelMapper.map(orderDTO, Orders.class));
        return orderDTO;
    }

    public String deleteOrder(OrderDTO orderDTO) {
        orderRepo.delete(modelMapper.map(orderDTO, Orders.class));
        return "Order deleted";
    }

    public String deleteOrderById(Integer orderId) {
        if (!orderRepo.existsById(orderId)) {
            throw new RuntimeException("Order not found for deletion");
        }
        orderRepo.deleteById(orderId);
        return "Order deleted";
    }
}