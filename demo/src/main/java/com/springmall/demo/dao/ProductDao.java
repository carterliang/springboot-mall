package com.springmall.demo.dao;

import com.springmall.demo.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
