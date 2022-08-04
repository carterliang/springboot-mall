package com.springmall.demo.service;


import com.springmall.demo.dao.ProductQueryParams;
import com.springmall.demo.dto.ProductRequest;
import com.springmall.demo.model.Product;

import java.util.List;

public interface ProductService {
  Integer countProduct(ProductQueryParams productQueryParams);
  List<Product> getProducts(ProductQueryParams productQueryParams);
  Product getByProductId(Integer productId);
  Integer createProduct(ProductRequest productRequest);
  void updateProduct(Integer productId,ProductRequest productRequest);
  void deleteProductById(Integer productId);
}
