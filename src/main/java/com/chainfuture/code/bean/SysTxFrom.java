package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
public class SysTxFrom extends Page implements Serializable {

    private static final long serialVersionUID = -7067451108770069145L;
    private Integer id;
    private String  Txid;
    private String  FromAddress;
    private String  ToAddress;
    private Integer AssetId;
    private Integer Amount;
    private Integer AssetId2;
    private Integer IsSendBack;
    private Integer Amount2;
    private Integer Tag;
    private String  PairId;
    private String  Data;


    public SysTxFrom() {super();}

    public SysTxFrom(Integer id, String txid, String fromAddress, String toAddress, Integer assetId, Integer amount, Integer assetId2, Integer isSendBack, Integer amount2, Integer tag, String pairId, String data) {
        this.id = id;
        Txid = txid;
        FromAddress = fromAddress;
        ToAddress = toAddress;
        AssetId = assetId;
        Amount = amount;
        AssetId2 = assetId2;
        IsSendBack = isSendBack;
        Amount2 = amount2;
        Tag = tag;
        PairId = pairId;
        Data = data;
    }
}
