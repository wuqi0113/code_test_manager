package com.chainfuture.code.service;

import com.chainfuture.code.bean.InuPrice;
import com.chainfuture.code.bean.InuSetExample;

import java.util.List;

/**
 * Created by admin on 2019/6/29.
 */
public interface InuSetService {
    List<InuSetExample> getInuSetList(Integer insuranceType);

    InuSetExample selInuSet(Integer year);
}
