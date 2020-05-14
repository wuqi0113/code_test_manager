package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.FxOrder;
import com.chainfuture.code.mapper.FxOrderMapper;
import com.chainfuture.code.service.FxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FxOrderServiceImpl implements FxOrderService {
    @Autowired
    private FxOrderMapper fxOrderMapper;

    @Override
    public int getOrderSum(Integer id) {
        return fxOrderMapper.getOrderSum(id);
    }

    @Override
    public void insertOrder(FxOrder fxOrder) {
        fxOrderMapper.insertOrder(fxOrder);
    }
}
