package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.SysModule;
import com.chainfuture.code.mapper.SysModuleMapper;
import com.chainfuture.code.service.SysModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysModuleServiceImpl implements SysModuleService {

    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Override
    public List<SysModule> getModuleName(SysModule sysModule) {
        return sysModuleMapper.getModuleName(sysModule);
    }

    @Override
    public SysModule moduleInfo(String address) {
        return sysModuleMapper.moduleInfo(address);
    }

    @Override
    public SysModule selSysModuleByPerms(String perms) {
        return sysModuleMapper.selSysModuleByPerms(perms);
    }
}
