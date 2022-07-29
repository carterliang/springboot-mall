package com.springmall.demo.dao;

import com.springmall.demo.dto.ProductRequest;
import com.springmall.demo.model.Product;

import java.util.List;

public interface ProductDao {
    public List<Product> getProducts();
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
