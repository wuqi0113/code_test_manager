package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.FxOrder;

/**
 * Created by admin on 2020/4/15.
 */
public interface FxOrderMapper {
    void insertOrder(FxOrder fxOrder);

    int getOrderSum(Integer id);
}
