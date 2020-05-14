package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.SysManage;
import com.chainfuture.code.bean.SysManageExample;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysManageMapper {

    List<SysManage>  selSysManageList(SysManageExample sysManage);

    int selSysManageCount();

    int addSysManage(SysManage sysManage);

    SysManage selSysManageByAddr(String address);

    SysManage selSysManageByManageAddr(String manageAddr);

    void updSysManage(SysManage sysManage);

    List<SysManage> getManagerListByAddr(Map<String, Object> map);

    List<SysManage> checkManager(@Param("manageAddr") String manageAddr);

    void delManager(SysManage sysManage);

    List<SysManage> selSysManageListByAddr(String address);

    SysManage selSysManageByPAddrAndUAddr(Map<String, Object> map);

    List<HashMap<String,Object>> manageList(HashMap<String, Object> map);

    SysManage selSysManagerByUserAndModule(SysManage sysManage);

    SysManage selSysManagerByUserAndSname(SysManage sysManage);
}
