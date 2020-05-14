package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.SysTxFrom;
import com.chainfuture.code.bean.SysTxTo;

import java.util.List;

/**
 * Created by admin on 2019/4/11.
 */
public interface SysTxFromMapper {

    List<SysTxFrom> selSysTxList(SysTxFrom sysTx);

    int  selSysTxCount();

    int addSysTxFrom(SysTxFrom sysTxFrom);
}
