package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.PrimeTxExample;

import java.util.List;
import java.util.Map;

public interface PrimeTxMapper {
    void addPrimeTx(PrimeTxExample primeTxExample);

    List<PrimeTxExample> selPrimeTxByFromAndToAddr(PrimeTxExample ptx);

    List<Map<String,Object>> selPrimeTxByPerson(Map<String, Object> map);
}
