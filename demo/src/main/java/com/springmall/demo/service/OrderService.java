package com.springmall.demo.service;

import com.springmall.demo.dto.CreateOrderRequest;
import com.springmall.demo.model.Order;

public interface OrderService {
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
