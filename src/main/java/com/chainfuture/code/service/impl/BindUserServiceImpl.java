package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.BindUserExample;
import com.chainfuture.code.mapper.BindUserMapper;
import com.chainfuture.code.service.BindUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BindUserServiceImpl implements BindUserService {

    @Autowired
    private BindUserMapper bindUserMapper;

    @Override
    public void addBindUser(BindUserExample bindUserExample) {
        bindUserMapper.addBindUser(bindUserExample);
    }
}
