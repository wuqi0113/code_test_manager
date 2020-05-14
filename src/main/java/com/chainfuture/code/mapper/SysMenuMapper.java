package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.SysMenu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by admin on 2019/6/3.
 */
public interface SysMenuMapper {
    @Select("SELECT * FROM sys_user_menu  sum  LEFT JOIN  sys_menu  sm  on sum.mid = sm.menuId  WHERE sum.openId =  #{openId}")
    List<SysMenu> getMenus(String openId);

    List<SysMenu> selMenuList();

    int selMenuCount();
}
