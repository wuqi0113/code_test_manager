package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.InuPrice;
import com.chainfuture.code.bean.InuSetExample;
import com.chainfuture.code.mapper.InuSetMapper;
import com.chainfuture.code.service.InuSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InuSetServiceImpl implements InuSetService {

    @Autowired
    private InuSetMapper inuSetMapper;

    @Override
    public List<InuSetExample> getInuSetList(Integer insuranceType) {
        return inuSetMapper.getInuSetList(insuranceType);
    }

    @Override
    public InuSetExample selInuSet(Integer year) {
        return inuSetMapper.selInuSet(year);
    }

}
