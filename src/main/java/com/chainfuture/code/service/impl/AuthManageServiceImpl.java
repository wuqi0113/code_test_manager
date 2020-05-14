package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.AuthManage;
import com.chainfuture.code.bean.AuthManageExample;
import com.chainfuture.code.mapper.AuthManageMapper;
import com.chainfuture.code.service.AuthManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AuthManageServiceImpl implements AuthManageService {

    @Autowired
    private AuthManageMapper authManageMapper;

    @Override
    public List<AuthManage> selAuthManageList(AuthManage  authManage) {
        return authManageMapper.selAuthManageList(authManage);
    }

    @Override
    public int selAuthManageCount() {
        return authManageMapper.selAuthManageCount();
    }

    @Override
    public int addAuthManage(AuthManage authManage) {
        return authManageMapper.addAuthManage(authManage);
    }

    @Override
    public AuthManage selAuthManageByAddr(String address) {
        return authManageMapper.selAuthManageByAddr(address);
    }

    @Override
    public AuthManage selAuthManageById(Integer id) {
        return authManageMapper.selAuthManageById(id);
    }

    @Override
    public AuthManage selSysManageByManageAddr(AuthManage manageAddr) {
        return authManageMapper.selSysManageByManageAddr(manageAddr);
    }

    @Override
    public List<HashMap<String,Object>> getRecordListByModule(AuthManageExample authManageExample) {
        return authManageMapper.getRecordListByModule(authManageExample);
    }

    @Override
    public List<HashMap<String, Object>> getProductRecordList(AuthManageExample authManageExample) {
        return authManageMapper.getProductRecordList(authManageExample);
    }

    @Override
    public List<HashMap<String, Object>> getInvitRecord(AuthManageExample authManageExample) {
        return authManageMapper.getInvitRecord(authManageExample);
    }

    @Override
    public List<HashMap<String, Object>> getBaseSet(AuthManageExample authManageExample) {
        return authManageMapper.getBaseSet(authManageExample);
    }

    @Override
    public List<HashMap<String, Object>> getHangUpRecordList(AuthManageExample authManageExample) {
        return authManageMapper.getHangUpRecordList(authManageExample);
    }

    @Override
    public List<HashMap<String, Object>> getBatchList(AuthManageExample authManageExample) {
        return authManageMapper.getBatchList(authManageExample);
    }
}
