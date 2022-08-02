package com.springmall.demo.controller;

import com.springmall.demo.constant.ProductCategory;
import com.springmall.demo.dao.ProductQueryParams;
import com.springmall.demo.dto.ProductRequest;
import com.springmall.demo.model.Product;
import com.springmall.demo.service.ProductService;
import com.springmall.demo.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("products")
        public ResponseEntity<Page<Product>>getProducts(
                //查詢條件filtering
                @RequestParam(required=false) ProductCategory category,
                @RequestParam(required = false)String search ,
               //排序 sorting
                @RequestParam(defaultValue = "created_date") String orderBy,
                @RequestParam(defaultValue = "desc") String sort ,
               //Pagination
                @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
                @RequestParam(defaultValue = "0") @Min(0) Integer offset)
    {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);
        List<Product> productList =productService.getProducts(productQueryParams);
        //TO get product total
        Integer total = productService.countProduct(productQueryParams);
        //分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId)
    {
        Product product=   productService.getByProductId(productId);
        if(product!=null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/products")
    public ResponseEntity<Product>createProduct(@RequestBody @Valid ProductRequest productRequest){
      Integer productId =productService.createProduct(productRequest);
      Product product =productService.getByProductId(productId);
      return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product>updateProduct(@PathVariable Integer productId,
                                                @RequestBody @Valid ProductRequest productRequest){
        //檢查product 是否存在
        Product product = productService.getByProductId(productId);
        if(product==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //修改商品數據
        productService.updateProduct(productId,productRequest);
        Product updatedProduct = productService.getByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }


    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer productId){
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
