package com.springmall.demo.service;

import com.springmall.demo.dto.ProductRequest;
import com.springmall.demo.model.Product;

public interface ProductService {
  Product getByProductId(Integer productId);
  Integer createProduct(ProductRequest productRequest);
  void updateProduct(Integer productId,ProductRequest productRequest);
  void deleteProductById(Integer productId);
}
