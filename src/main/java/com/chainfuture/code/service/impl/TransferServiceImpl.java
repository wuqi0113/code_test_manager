package com.chainfuture.code.service.impl;

import com.chainfuture.code.mapper.TransferMapper;
import com.chainfuture.code.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("transferServiceImpl")
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferMapper transferMapper;

    @Override
    public void insertTransfer(HashMap<String, Object> map) {
        transferMapper.insertTransfer(map);
    }
}
