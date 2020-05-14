package com.chainfuture.code.service;

import com.chainfuture.code.bean.PrimeTxExample;

import java.util.List;
import java.util.Map;

public interface PrimeTxService {
    void addPrimeTx(PrimeTxExample primeTxExample);

    List<PrimeTxExample> selPrimeTxByFromAndToAddr(PrimeTxExample ptx);

    List<Map<String,Object>> selPrimeTxByPerson(Map<String, Object> map);
}
