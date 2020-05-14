package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.SignMsg;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/5/12.
 */
public interface SignMsgMapper {

    void insertSignMsg(SignMsg signMsg);

    List<SignMsg> selSignMsgList(SignMsg signMsg);

    int selSignMsgCount(SignMsg signMsg);

    List<Map<String,Object>> queryBatchList(Integer mold);

    List<Map<String,Object>> getBatchListByAddress(String address);

    Map<String,Object> selTotalAmount();

    Map<String,Object> getBatchById(Integer id);

    SignMsg getBatchByAddress(String batchAddress);

    void updateSignMsg(SignMsg signMsg);

    SignMsg getBatchBySname(String sname);

    SignMsg getSignMsgByAddrAndSignId(Map<String, Object> map);

    SignMsg selSignMsgByAddrAndSignId(SignMsg s1);
}
