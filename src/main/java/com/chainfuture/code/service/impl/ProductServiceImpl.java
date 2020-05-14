package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.Product;
import com.chainfuture.code.mapper.ProductMapper;
import com.chainfuture.code.service.ProductService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    @Override
    public void saveProduct(Product model) {
        if (model.getId() == null){
            model.setStatus(1);
            model.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            productMapper.insertProduct(model);
        } else {
            model.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            productMapper.updateProduct(model);
        }
    }

    @Override
    public List<Product> listProduct(Product model) {
        return productMapper.listProduct(model);
    }

    @Override
    public Integer queryCount(Product model) {
        return productMapper.queryCount(model);
    }

    @Override
    public List<Product> getProductAddr() {
        return productMapper.getProductAddr();
    }

    @Override
    public List<Product> queryAll() {
        return productMapper.queryAll();
    }

    @Override
    public List<Product> queryBySname(Product model) {
        return productMapper.queryBySname(model);
    }

    @Override
    public int getBySnameLike(Product model) {
        return productMapper.getBySnameLike(model);
    }

    @Override
    public List<Product> getChildProList(Product product) {
        return productMapper.getChildProList(product);
    }

    @Override
    public void updateStatus(Integer rowId,Integer status) {
        Map<String, Object> map  = Maps.newHashMap();
        map.put("id",rowId);
        map.put("status",status);
        productMapper.updateStatus(map);
    }

    @Override
    public Product getById(Integer id) {
        return productMapper.getById(id);
    }

    @Override
    public List<Product> getAssetList() {
        return productMapper.getAssetList();
    }

    @Override
    public Product getTransferInfo(String proAddress) {
        return productMapper.getTransferInfo(proAddress);
    }

    @Override
    public List<Product> monitorAddr(Integer id) {
        return productMapper.monitorAddr(id);
    }
}
