package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.Medal;
import com.chainfuture.code.bean.Product;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/10/22.
 */
public interface ProductMapper {

    void insertProduct(Product model);

    void updateProduct(Product model);

    List<Product> listProduct(Product model);

    Integer queryCount(Product model);

    void updateStatus(Map<String, Object> map);

    Product getById(Integer id);

    List<Product> getAssetList();

    Product getTransferInfo(String proAddress);

    List<Product> monitorAddr(Integer id);

    List<Product> getProductAddr();

    List<Product> queryAll();

    List<Product> queryBySname(Product model);

    int getBySnameLike(Product model);

    List<Product> getChildProList(Product model);
}
