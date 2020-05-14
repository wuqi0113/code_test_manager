package com.chainfuture.code.service;

import com.chainfuture.code.bean.BounsBill;

import java.util.List;

/**
 * Created by admin on 2019/12/30.
 */
public interface BounsBillService {
    List<BounsBill> selBounsBillList(int status);

    void insertBounsBill(BounsBill bounsBill);

    void updateBounsBill(BounsBill bounsBill);
}
