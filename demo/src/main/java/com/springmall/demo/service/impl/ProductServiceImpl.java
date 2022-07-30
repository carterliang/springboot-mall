package com.springmall.demo.service.impl;

import com.springmall.demo.constant.ProductCategory;
import com.springmall.demo.dao.ProductDao;
import com.springmall.demo.dao.ProductQueryParams;
import com.springmall.demo.dto.ProductRequest;
import com.springmall.demo.model.Product;
import com.springmall.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams){
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getByProductId(Integer productId){
        return productDao.getProductById(productId);
    }
    @Override
    public Integer createProduct(ProductRequest productRequest){
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
       productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}

