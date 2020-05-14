package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.SysCarteExample;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by admin on 2019/6/3.
 */
public interface SysCarteMapper {
    @Select("SELECT * FROM sys_user_carte  suc  LEFT JOIN  sys_carte  sc  on suc.carteId = sc.id  WHERE suc.userAddress =  #{userAddress}")
    List<SysCarteExample> getSysCartes(String userAddress);
    @Select("SELECT * FROM sys_user_carte  suc  LEFT JOIN  sys_carte  sc  on suc.carteId = sc.id  WHERE sc.href =  #{href}")
    SysCarteExample selSysCarte(String href);
}
