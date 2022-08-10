package com.springmall.demo.dao;
import com.springmall.demo.dto.OrderQueryParams;
import com.springmall.demo.model.Order;
import com.springmall.demo.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Order getOrderById(Integer orderId);
    List <OrderItem> getOrderItemsByOrderId(Integer orderId);
    Integer createOrder(Integer userId,Integer totalAmount);
    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
    Integer countOrder (OrderQueryParams orderQueryParams);
}
