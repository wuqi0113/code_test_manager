package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class WorkapiRecord implements Serializable{
    private static final long serialVersionUID = -7549958114103017111L;
    private Integer id;
    private String  appId;
    private String  recordTime;
    private Integer year;
    private String  payNum;
    private String  way;
    private Integer type;
    private Integer amount;
    private String  signMsg;
    private String  receiver;
    private String  receiveTime;
    private String  busiJson;
    private String  purchaser;
    private Integer status;
    private Integer incomeStatus;
    private String  orderNum	;


    private String  realName;
    private String  idNum;

    private String startTime;
    private String endTime;

    private Integer  profitAmount;

    public WorkapiRecord() {super();}

    public WorkapiRecord(String orderNum,String startTime,String endTime,String realName,String idNum,Integer incomeStatus,Integer profitAmount,Integer status,String purchaser, Integer id, String appId, String recordTime, Integer year, String payNum, String way, Integer type, Integer amount, String signMsg, String receiver, String receiveTime,String busiJson) {
        super();
        this.orderNum = orderNum;
        this.startTime = startTime;
        this.endTime = endTime;
        this.realName = realName;
        this.idNum = idNum;
        this.incomeStatus = incomeStatus;
        this.profitAmount = profitAmount;
        this.status = status;
        this.purchaser = purchaser;
        this.id = id;
        this.appId = appId;
        this.recordTime = recordTime;
        this.year = year;
        this.payNum = payNum;
        this.way = way;
        this.type = type;
        this.amount = amount;
        this.signMsg = signMsg;
        this.receiver = receiver;
        this.receiveTime = receiveTime;
        this.busiJson = busiJson;
    }

    @Override
    public String toString() {
        return "WorkapiRecord{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", year=" + year +
                ", payNum='" + payNum + '\'' +
                ", way='" + way + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", signMsg='" + signMsg + '\'' +
                ", receiver='" + receiver + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", busiJson='" + busiJson + '\'' +
                '}';
    }
}
