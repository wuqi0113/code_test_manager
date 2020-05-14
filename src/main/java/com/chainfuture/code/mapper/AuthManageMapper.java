package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.AuthManage;
import com.chainfuture.code.bean.AuthManageExample;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2019/4/7.
 */
public interface AuthManageMapper {
    List<AuthManage> selAuthManageList(AuthManage  authManage);

    int selAuthManageCount();

    int addAuthManage(AuthManage authManage);

    AuthManage selAuthManageById(int id);

    AuthManage selAuthManageByAddr(String address);

    void upSysAuthManageStatus(AuthManage  authManage);

    AuthManage selSysManageByManageAddr(AuthManage manageAddr);

    AuthManageExample selAuthManageByOpenId(String userId);

    List<HashMap<String,Object>> getRecordListByModule(AuthManageExample authManageExample);

    List<HashMap<String,Object>> getProductRecordList(AuthManageExample authManageExample);

    List<HashMap<String,Object>> getInvitRecord(AuthManageExample authManageExample);

    List<HashMap<String,Object>> getBaseSet(AuthManageExample authManageExample);

    List<HashMap<String,Object>> getHangUpRecordList(AuthManageExample authManageExample);

    List<HashMap<String,Object>> getBatchList(AuthManageExample authManageExample);
}
