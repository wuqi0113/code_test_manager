package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.ProductTrace;
import com.chainfuture.code.mapper.ProductTraceMapper;
import com.chainfuture.code.service.ProductTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTraceServiceImpl implements ProductTraceService {

    @Autowired
    private ProductTraceMapper productTraceMapper;
    @Override
    public void insertProductTrace(ProductTrace productTrace) {
        productTraceMapper.insertProductTrace(productTrace);
    }
}
