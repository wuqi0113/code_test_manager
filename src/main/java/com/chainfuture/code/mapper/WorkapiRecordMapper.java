package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.WorkapiRecord;

import java.util.HashMap;
import java.util.List;

public interface WorkapiRecordMapper {
    void addWorkapiRecord(WorkapiRecord wr);

    int updateById(WorkapiRecord wr);

    Integer updateforPayNum(WorkapiRecord wr);

    List<HashMap<String,String>> getUnopenedInsurance(String province);

    List<WorkapiRecord> getEffectiveInsurance(String receiver);

    WorkapiRecord selWorkapiRecordById(int id);

    List<HashMap<String,Object>> getPurchaseRecord(String address);

    List<WorkapiRecord> getExtensionRecord(HashMap<String,Object> map);

    List<WorkapiRecord> selWorkapiRecordMax(String address);

    WorkapiRecord selWorkapiRecordByTimeAndAddr(WorkapiRecord wr);

    HashMap<String, Object> selProfit(HashMap<String, Object> map);

    List<HashMap<String,String>> selFiledComment();

    WorkapiRecord getById(int workId);

    List<WorkapiRecord> getYearSection(String address);

    List<WorkapiRecord> selWorkapiRecordSectionMax(HashMap<String, String> hashMap);

    List<WorkapiRecord> getExtensionRecordSection(HashMap<String, Object> map);

    HashMap<String,Object> selProfitSection(HashMap<String, Object> map);

    List<WorkapiRecord> selWRSection(HashMap<String, Object> hashMap);

    List<WorkapiRecord> selExtensionList(String address);

    List<WorkapiRecord> selMaxProfitSection(HashMap<String, Object> map);

    WorkapiRecord selWorkapiRecordByOrderNum(String orderNum);
}
