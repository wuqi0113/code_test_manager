package com.chainfuture.code.service;

import com.chainfuture.code.bean.FxOrder;

/**
 * Created by admin on 2020/4/15.
 */
public interface FxOrderService {

    int getOrderSum(Integer id);

    void insertOrder(FxOrder fxOrder);
}
