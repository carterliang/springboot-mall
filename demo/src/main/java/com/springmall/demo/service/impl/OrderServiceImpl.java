package com.springmall.demo.service.impl;

import com.springmall.demo.dao.OrderDao;
import com.springmall.demo.dao.ProductDao;
import com.springmall.demo.dto.BuyItem;
import com.springmall.demo.dto.CreateOrderRequest;
import com.springmall.demo.model.OrderItem;
import com.springmall.demo.model.Product;
import com.springmall.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao  productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount =0;
        List<OrderItem> orderItemList=new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());
            //compute total amount
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount +amount;
            //轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);

        }
        // creat orders
        Integer orderId =orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId,orderItemList);

        return orderId ;
    }
}
