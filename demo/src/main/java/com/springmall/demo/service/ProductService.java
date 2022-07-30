package com.springmall.demo.service;

import com.springmall.demo.constant.ProductCategory;
import com.springmall.demo.dto.ProductRequest;
import com.springmall.demo.model.Product;

import java.util.List;

public interface ProductService {
  List<Product> getProducts(ProductCategory category, String search);
  Product getByProductId(Integer productId);
  Integer createProduct(ProductRequest productRequest);
  void updateProduct(Integer productId,ProductRequest productRequest);
  void deleteProductById(Integer productId);
}
