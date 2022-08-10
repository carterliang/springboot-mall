package com.springmall.demo.service.impl;

import com.springmall.demo.dao.OrderDao;
import com.springmall.demo.dao.ProductDao;
import com.springmall.demo.dao.UserDao;
import com.springmall.demo.dto.BuyItem;
import com.springmall.demo.dto.CreateOrderRequest;
import com.springmall.demo.dto.OrderQueryParams;
import com.springmall.demo.model.Order;
import com.springmall.demo.model.OrderItem;
import com.springmall.demo.model.Product;
import com.springmall.demo.model.User;
import com.springmall.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao  productDao;
    @Autowired
    private UserDao userDao;
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);



    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order>orderList = orderDao.getOrders(orderQueryParams);
        for(Order order : orderList){
           List<OrderItem>orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
           order.setOrderItemList(orderItemList);
        }
        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {

        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List <OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //檢查user是否存在
        User user = userDao.getUserById(userId);
        if(user==null){
            log.warn("該userId{}不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        int totalAmount =0;
        List<OrderItem> orderItemList=new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());
            //檢查product是否存在 ? 庫存是否足夠?
            if(product==null){
                log.warn("商品{}不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if(product.getStock()< buyItem.getQuantity()){
                log.warn("商品{}庫存量不足,無法購買.剩下庫存{},欲購買量{}",buyItem.getProductId(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            //扣除商品庫存
            productDao.updateStock(product.getProductId(),product.getStock()- buyItem.getQuantity());


            //compute total amount計算總價錢
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
