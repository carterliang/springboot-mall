package com.springmall.demo.dao;

import com.springmall.demo.dto.ProductRequest;
import com.springmall.demo.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
