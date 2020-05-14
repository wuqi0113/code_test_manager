package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.ProductTraceability;
import com.chainfuture.code.mapper.ProductTraceabilityMapper;
import com.chainfuture.code.service.ProductTraceabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2019/1/24.
 */
@Service("productTraceabilityServiceImpl")
public class ProductTraceabilityServiceImpl implements ProductTraceabilityService {

    @Autowired
    private ProductTraceabilityMapper productTraceabilityMapper;

    @Override
    public List<ProductTraceability> listProductTraceability(ProductTraceability model) {
        return productTraceabilityMapper.listProductTraceability(model);
    }

    @Override
    public Integer queryCount(ProductTraceability model) {
        return productTraceabilityMapper.queryCount(model);
    }

    @Override
    public ProductTraceability getById(Integer id) {
        return productTraceabilityMapper.getById(id);
    }

    @Override
    public void saveProductTraceability(ProductTraceability model) {
        productTraceabilityMapper.saveProductTraceability(model);
    }

    @Override
    public void updateProductTraceability(ProductTraceability model) {
        productTraceabilityMapper.updateProductTraceability(model);
    }
}
