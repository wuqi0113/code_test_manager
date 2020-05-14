package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.FxUser;
import com.chainfuture.code.mapper.FxUserMapper;
import com.chainfuture.code.service.FxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FxUserServiceImpl implements FxUserService {

    @Autowired
    private FxUserMapper fxUserMapper;

    @Override
    public FxUser selUserByAddr(String s) {
        return fxUserMapper.selUserByAddr(s);
    }

    @Override
    public void insertUser(FxUser uu) {
        fxUserMapper.insertUser(uu);
    }

    @Override
    public FxUser selUserByInviteCode(String s1) {
        return fxUserMapper.selUserByInviteCode(s1);
    }

    @Override
    public FxUser selUserById(int pid) {
        return fxUserMapper.selUserById(pid);
    }

    @Override
    public void updateUser(FxUser fx1) {
        fxUserMapper.updateUser(fx1);
    }
}
