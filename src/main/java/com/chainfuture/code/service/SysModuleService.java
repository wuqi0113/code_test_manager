package com.chainfuture.code.service;

import com.chainfuture.code.bean.SysModule;

import java.util.List;

/**
 * Created by admin on 2019/7/16.
 */
public interface SysModuleService {
    List<SysModule> getModuleName(SysModule sysModule);

    SysModule moduleInfo(String address);

    SysModule selSysModuleByPerms(String perms);
}
