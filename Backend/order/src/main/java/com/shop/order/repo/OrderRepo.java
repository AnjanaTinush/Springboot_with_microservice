package com.shop.order.repo;

import com.shop.order.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepo extends JpaRepository<Orders, Integer> {

    @Query(value = "SELECT * FROM Orders WHERE id = ?1 ",nativeQuery = true)
    Orders getUserById(Integer orderId);
}