package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.PrimeTxExample;
import com.chainfuture.code.mapper.PrimeTxMapper;
import com.chainfuture.code.service.PrimeTxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PrimeTxServiceImpl implements PrimeTxService {

    @Autowired
    private PrimeTxMapper primeTxMapper;

    @Override
    public void addPrimeTx(PrimeTxExample primeTxExample) {
        primeTxMapper.addPrimeTx(primeTxExample);
    }

    @Override
    public List<PrimeTxExample> selPrimeTxByFromAndToAddr(PrimeTxExample ptx) {
        return primeTxMapper.selPrimeTxByFromAndToAddr(ptx);
    }

    @Override
    public List<Map<String,Object>> selPrimeTxByPerson(Map<String, Object> map) {
        return primeTxMapper.selPrimeTxByPerson(map);
    }
}
