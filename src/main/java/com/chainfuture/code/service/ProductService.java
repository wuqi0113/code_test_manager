package com.chainfuture.code.service;

import com.chainfuture.code.bean.Medal;
import com.chainfuture.code.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface ProductService {

    void saveProduct(Product model);

    void updateStatus(Integer rowId,Integer status);

    Product getById(Integer id);

    List<Product> getAssetList();

    Product getTransferInfo(String proAddress);

    List<Product> monitorAddr(Integer id);

    List<Product> listProduct(Product model);

    Integer queryCount(Product model);

    List<Product> getProductAddr();

    List<Product> queryAll();

    List<Product> queryBySname(Product model);

    int getBySnameLike(Product model);

    List<Product> getChildProList(Product model);
}
