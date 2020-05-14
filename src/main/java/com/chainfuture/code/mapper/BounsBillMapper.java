package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.BounsBill;

import java.util.List;

/**
 * Created by admin on 2019/12/30.
 */
public interface BounsBillMapper {
    List<BounsBill> selBounsBillList(int status);

    void insertBounsBill(BounsBill bounsBill);

    void updateBounsBill(BounsBill bounsBill);
}
