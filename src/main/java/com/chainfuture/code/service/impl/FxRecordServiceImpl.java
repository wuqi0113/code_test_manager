package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.FxRecord;
import com.chainfuture.code.mapper.FxRecordMapper;
import com.chainfuture.code.service.FxRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FxRecordServiceImpl implements FxRecordService {

    @Autowired
    private FxRecordMapper fxRecordMapper;

    @Override
    public void insertFxRecord(FxRecord fxRecord) {
        fxRecordMapper.insertFxRecord(fxRecord);
    }
}
