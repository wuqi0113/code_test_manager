package com.chainfuture.code.mapper;


import com.chainfuture.code.bean.SysUser;

public interface SysUserMapper {
    SysUser getByUserName(String username);

    void updateSysUserName(SysUser user);
}
