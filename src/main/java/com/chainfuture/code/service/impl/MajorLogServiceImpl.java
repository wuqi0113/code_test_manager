package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.MajorLog;
import com.chainfuture.code.mapper.MajorLogMapper;
import com.chainfuture.code.service.MajorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("majorLogServiceImpl")
public class MajorLogServiceImpl implements MajorLogService {

    @Autowired
    private MajorLogMapper majorLogMapper;


    @Override
    public List<MajorLog> listMajorLog(MajorLog model) {
        return majorLogMapper.listMajorLog(model);
    }

    @Override
    public int queryCount(MajorLog model) {
        return majorLogMapper.queryCount(model);
    }

    @Override
    public void insertMajorLog(MajorLog model) {
        majorLogMapper.insertMajorLog(model);
    }

    @Override
    public List<MajorLog> getById(Integer id) {
        return majorLogMapper.getById(id);
    }
}
