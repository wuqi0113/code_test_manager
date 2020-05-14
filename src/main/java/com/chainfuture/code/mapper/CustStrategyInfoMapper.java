package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.CustStrategyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2019/3/21.
 */
public interface CustStrategyInfoMapper {
    int insert(CustStrategyInfo custStrategyInfo);

    List<CustStrategyInfo> selectStrateList(@Param("roleInfo") String roleInfo);

    CustStrategyInfo selectStrateList1(@Param("roleInfo") String roleInfo);
}
