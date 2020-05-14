package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.Product;
import com.chainfuture.code.bean.ProductExample;
import com.chainfuture.code.mapper.AuthProductMapper;
import com.chainfuture.code.service.AuthProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthProductServiceImpl implements AuthProductService {

    @Autowired
    private AuthProductMapper authProductMapper;

    @Override
    public void saveProduct(Product model) {
        authProductMapper.saveProduct(model);
    }

    @Override
    public Integer getProductCount() {
        return authProductMapper.getProductCount();
    }

    @Override
    public List<Product> selProductList(Product product) {
        return authProductMapper.selProductList(product);
    }

    @Override
    public ProductExample selBySname(String sname) {
        return authProductMapper.selBySname(sname);
    }

    @Override
    public ProductExample getProductById(Integer id) {
        return authProductMapper.getProductById(id);
    }

    @Override
    public List<Product> getBatchList(Product product) {
        return authProductMapper.getBatchList(product);
    }

    @Override
    public Integer getBatchCount(Product product) {
        return authProductMapper.getBatchCount(product);
    }

    @Override
    public List<ProductExample> selVipProductList(Integer type) {
        return authProductMapper.selVipProductList(type);
    }

    @Override
    public void addProduct(ProductExample product) {
        authProductMapper.addProduct(product);
    }

    @Override
    public void upProduct(ProductExample product) {
        authProductMapper.updateProduct(product);
    }

    @Override
    public void updateCancleOrder(Map<String, Object> selfMap) {
        authProductMapper.updateCancleOrder(selfMap);
    }

    @Override
    public Map<String, Object> selectMaxOrder() {
        return authProductMapper.selectMaxOrder();
    }

    @Override
    public ProductExample getProductByAddress(String proAddress) {
        return authProductMapper.getProductByAddress(proAddress);
    }

    @Override
    public Map<String, Object> selProTotalAmount() {
        return authProductMapper.selProTotalAmount();
    }
}
