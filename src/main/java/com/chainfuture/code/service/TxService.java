package com.chainfuture.code.service;

import com.chainfuture.code.bean.Tx;

import java.util.List;

/**
 * Created by admin on 2018/11/12.
 */
public interface TxService {
    //List<Tx> getUserList(String proAddress, Integer page, Integer limit);

   // int getCount(String proAddress, Integer page, Integer limit);

    List<Tx> getUserList(Tx tx);

    int getCount(Tx tx);
}
