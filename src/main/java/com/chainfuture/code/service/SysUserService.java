package com.chainfuture.code.service;


import com.chainfuture.code.bean.SysUser;

public interface SysUserService {


    SysUser getByUserName(String username);

    void updateSysUserName(SysUser user);
}