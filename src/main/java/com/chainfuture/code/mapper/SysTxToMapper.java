package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.SysTxFrom;
import com.chainfuture.code.bean.SysTxTo;

import java.util.List;

/**
 * Created by admin on 2019/4/11.
 */
public interface SysTxToMapper {

    List<SysTxTo> selSysTxList(SysTxTo sysTx);

    int  selSysTxCount();

    int  addSysTxTo(SysTxTo sysTxTo);
}
