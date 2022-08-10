package com.springmall.demo.service;

import com.springmall.demo.dto.CreateOrderRequest;
import com.springmall.demo.dto.OrderQueryParams;
import com.springmall.demo.model.Order;

import java.util.List;

public interface OrderService {
    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}