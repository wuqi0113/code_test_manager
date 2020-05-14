package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.Tx;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/11/12.
 */
public interface TxMapper {

    List<Tx> getUserList(Tx tx);

    int queryCount(Tx tx);
}
