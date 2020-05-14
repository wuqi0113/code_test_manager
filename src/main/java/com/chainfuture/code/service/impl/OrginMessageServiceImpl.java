package com.chainfuture.code.service.impl;

import com.chainfuture.code.mapper.OrginMessageMapper;
import com.chainfuture.code.service.OrginMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("orginMessageServiceImpl")
public class OrginMessageServiceImpl implements OrginMessageService {

    @Autowired
    private OrginMessageMapper orginMessageMapper;

    @Override
    public void saveCode(String proAddress, String originMessage, long codeId, String signMessage) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("proAddress",proAddress);
        map.put("originMessage",originMessage);
        map.put("codeId",codeId);
        map.put("signMessage",signMessage);
        orginMessageMapper.saveCode(map);
    }
}
