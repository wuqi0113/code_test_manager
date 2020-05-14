package com.chainfuture.code.service;

import com.chainfuture.code.bean.Product;
import com.chainfuture.code.bean.ProductExample;

import java.util.List;
import java.util.Map;


public interface AuthProductService {
    void saveProduct(Product model);

    Integer getProductCount();

    List<Product> selProductList(Product product);

    ProductExample selBySname(String sname);

    ProductExample getProductById(Integer id);

    List<Product> getBatchList(Product product);

    Integer getBatchCount(Product product);

    List<ProductExample> selVipProductList(Integer type);

    void addProduct(ProductExample product);

    void upProduct(ProductExample product);

    void updateCancleOrder(Map<String, Object> selfMap);

    Map<String,Object> selectMaxOrder();

    ProductExample getProductByAddress(String productAddress);

    Map<String,Object> selProTotalAmount();
}
