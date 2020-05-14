package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.SysUser;
import com.chainfuture.code.mapper.SysUserMapper;
import com.chainfuture.code.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysUserServiceImpl")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getByUserName(String username) {
        return sysUserMapper.getByUserName(username);
    }

    @Override
    public void updateSysUserName(SysUser user) {
        sysUserMapper.updateSysUserName(user);
    }
}
