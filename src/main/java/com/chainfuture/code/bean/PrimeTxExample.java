package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
@Getter
@Setter
public class PrimeTxExample implements Serializable{
    private static final long serialVersionUID = 7319524072072399907L;

    private BigInteger id;
    private String     fromAddress;
    private String     toTxData;
    private String     toAddress;
    private String     busiJson;
    private Integer    amount;
    private String     productId;
    private String     workId;
    private String     createTime;
    private Integer    payStatus;
    private String     busiTxId;
    private String     platformTxId;
    private String     primeAddress;
    private String     errorMsg;
    private String    codeInfo;

    public PrimeTxExample() {
        super();
    }

    public PrimeTxExample(String codeInfo,String errorMsg,BigInteger id, String fromAddress, String toTxData, String toAddress, String busiJson, Integer amount, String productId, String workId, String createTime, Integer payStatus, String busiTxId, String platformTxId, String primeAddress) {
        super();
        this.codeInfo = codeInfo;
        this.errorMsg = errorMsg;
        this.id = id;
        this.fromAddress = fromAddress;
        this.toTxData = toTxData;
        this.toAddress = toAddress;
        this.busiJson = busiJson;
        this.amount = amount;
        this.productId = productId;
        this.workId = workId;
        this.createTime = createTime;
        this.payStatus = payStatus;
        this.busiTxId = busiTxId;
        this.platformTxId = platformTxId;
        this.primeAddress = primeAddress;
    }

    @Override
    public String toString() {
        return "PrimeTxExample{" +
                "id=" + id +
                ", fromAddress='" + fromAddress + '\'' +
                ", toTxData='" + toTxData + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", busiJson='" + busiJson + '\'' +
                ", amount=" + amount +
                ", productId='" + productId + '\'' +
                ", workId='" + workId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", payStatus=" + payStatus +
                ", busiTxId='" + busiTxId + '\'' +
                ", platformTxId='" + platformTxId + '\'' +
                ", primeAddress='" + primeAddress + '\'' +
                '}';
    }
}
