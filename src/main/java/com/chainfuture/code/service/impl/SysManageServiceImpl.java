package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.SysManage;
import com.chainfuture.code.mapper.SysManageMapper;
import com.chainfuture.code.service.SysManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysManageServiceImpl implements SysManageService {

    @Autowired
    private SysManageMapper sysManageMapper;

    @Override
    public List<SysManage> selSysManageListByAddr(String address) {
        return sysManageMapper.selSysManageListByAddr(address);
    }

    @Override
    public SysManage selSysManagerByUserAndModule(SysManage sysManage) {
        return sysManageMapper.selSysManagerByUserAndModule(sysManage);
    }
}
