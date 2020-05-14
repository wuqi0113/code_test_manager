package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.PublicTipsExample;
import com.chainfuture.code.mapper.PublicTipsMapper;
import com.chainfuture.code.service.PublicTipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicTipsServiceImpl implements PublicTipsService {

    @Autowired
    private PublicTipsMapper publicTipsMapper;

    @Override
    public PublicTipsExample getTipsMessage(PublicTipsExample publicTipsExample) {
        return publicTipsMapper.getTipsMessage(publicTipsExample);
    }
}
