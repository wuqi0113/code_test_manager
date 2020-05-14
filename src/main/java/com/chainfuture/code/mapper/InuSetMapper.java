package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.InuSetExample;

import java.util.List;

/**
 * Created by admin on 2019/6/29.
 */
public interface InuSetMapper {
    List<InuSetExample> getInuSetList(Integer insuranceType);

    InuSetExample selInuSet(Integer year);
}
