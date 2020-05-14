package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.Product;
import com.chainfuture.code.bean.ProductExample;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/4/8.
 */
public interface AuthProductMapper {
    void saveProduct(Product model);

    Product selProductById(int id);

    void upProduct(Product product);

    Integer getProductCount();

    List<Product> selProductList(Product product);

    ProductExample selBySname(String sname);

    ProductExample getProductById(Integer id);

    List<Product> getBatchList(Product product);

    Integer getBatchCount(Product product);

    List<ProductExample> selVipProductList(Integer type);

    void addProduct(ProductExample product);

    void updateProduct(ProductExample product);

    void updateCancleOrder(Map<String, Object> selfMap);

    Map<String,Object> selectMaxOrder();

    ProductExample getProductByAddress(String proAddress);

    Map<String,Object> selProTotalAmount();
}
