package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.UserMedal;
import com.chainfuture.code.mapper.UserMedalMapper;
import com.chainfuture.code.service.UserMedalService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userMedalServiceImpl")
public class UserMedalServiceImpl implements UserMedalService{


    @Autowired
    private UserMedalMapper userMedalMapper;

    @Override
    public List<UserMedal> selectSql() {
        return  userMedalMapper.selectSql();
    }

    @Override
    public int getMedalCount() {
        return userMedalMapper.getMedalCount();
    }
}
