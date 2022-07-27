package com.springmall.demo.service.impl;

import com.springmall.demo.dao.ProductDao;
import com.springmall.demo.model.Product;
import com.springmall.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getByProductId(Integer productId) {
        return productDao.getProductById(productId);
    }
}
