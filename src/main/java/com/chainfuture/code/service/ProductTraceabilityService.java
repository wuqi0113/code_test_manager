package com.chainfuture.code.service;

import com.chainfuture.code.bean.ProductTraceability;

import java.util.List;

/**
 * Created by admin on 2019/1/24.
 */
public interface ProductTraceabilityService {
    List<ProductTraceability> listProductTraceability(ProductTraceability model);

    Integer queryCount(ProductTraceability model);

    ProductTraceability getById(Integer id);

    void saveProductTraceability(ProductTraceability model);

    void updateProductTraceability(ProductTraceability model);
}
