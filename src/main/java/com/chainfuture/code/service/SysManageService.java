package com.chainfuture.code.service;

import com.chainfuture.code.bean.SysManage;

import java.util.List;

/**
 * Created by admin on 2019/9/2.
 */
public interface SysManageService {
    List<SysManage> selSysManageListByAddr(String address);

    SysManage selSysManagerByUserAndModule(SysManage sysManage);
}
