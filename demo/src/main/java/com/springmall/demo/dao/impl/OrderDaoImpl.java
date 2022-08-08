package com.springmall.demo.dao.impl;

import com.springmall.demo.dao.OrderDao;
import com.springmall.demo.model.OrderItem;
import com.springmall.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.GenericArrayType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
   // @Autowired
   // private OrderService orderService;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {

        String sql ="INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) "+
                "Values (:userId, :totalAmount, :createdDate, :lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("totalAmount",totalAmount);
        Date now = new  Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);
        KeyHolder keyHolder =new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int orderId = keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        // 使用 for loop 一條一條 sql 加入數居,效率很低
     for(OrderItem orderItem : orderItemList){
           String sql = "INSERT INTO order_item (order_id, product_id,quantity,amount)"+
                   "Values (:orderId, :productId ,:quantity, :amount)";
           Map <String,Object>map = new HashMap<>();
           map.put("orderId",orderId);
           map.put("productId",orderItem.getProductId());
           map.put("quantity",orderItem.getQuantity());
           map.put("amount",orderItem.getAmount());
           namedParameterJdbcTemplate.update(sql,map);
       }

        //使用 batchUpdate 一次性加入數據,效率告高
      /* String sql = "Insert Into order_item(order_id,product_id,quantity,amount)"+
                "Values (:orderId, :productId , :quantity, :amount)";
        MapSqlParameterSource[]parameterSources = new MapSqlParameterSource[orderItemList.size()];
        for(int i=0;i< orderItemList.size();i++){
            OrderItem orderItem =orderItemList.get(i);
            parameterSources[i]= new MapSqlParameterSource();
            parameterSources[i].addValues("orderId",orderId);
            parameterSources[i].addValues("productId",orderItem.getProductId());
            parameterSources[i].addValues("quantity",orderItem.getQuantity());
            parameterSources[i].addValues("amount",orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql,parameterSources); */
    }
}
