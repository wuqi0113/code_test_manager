package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.SysModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2019/7/16.
 */
public interface SysModuleMapper {
    List<SysModule> getModuleName(SysModule sysModule);

    SysModule moduleInfo(@Param("address") String address);

    SysModule selSysModuleByPerms(@Param("perms") String perms);
}
