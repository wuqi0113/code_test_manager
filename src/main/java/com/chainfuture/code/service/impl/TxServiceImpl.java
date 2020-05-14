package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.Tx;
import com.chainfuture.code.mapper.TxMapper;
import com.chainfuture.code.service.TxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("txServiceImpl")
public class TxServiceImpl implements TxService {

    @Autowired
    private TxMapper txMapper;

    @Override
    public List<Tx> getUserList(Tx tx) {
        return txMapper.getUserList(tx);
    }

    @Override
    public int getCount(Tx tx) {
        return txMapper.queryCount(tx);
    }


}
