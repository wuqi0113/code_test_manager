package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;


@Setter
@Getter
public class Tx extends Page implements Serializable {
    private static final long serialVersionUID = -2701582488979815248L;

    private Integer id;
    private String  user;
    private String product;
    private String  productAddress;
    private BigInteger singleProductID;
    private String  toProductTxID;
    private Integer verifiyType;
    private Integer txType;
    private String  toPlantTxID;
    private String  verifiyDate;
    private String  nickname;
    private BigInteger timeStamp;
    private BigInteger token_number;

    private String  startTime;
    private String  endTime;

    public Tx() {super();}

    public Tx(Integer id, String user, String product, String productAddress, BigInteger singleProductID, String toProductTxID, Integer verifiyType, Integer txType, String toPlantTxID, String verifiyDate, String nickname, BigInteger timeStamp, BigInteger token_number, String startTime, String endTime) {
        super();
        this.id = id;
        this.user = user;
        this.product = product;
        this.productAddress = productAddress;
        this.singleProductID = singleProductID;
        this.toProductTxID = toProductTxID;
        this.verifiyType = verifiyType;
        this.txType = txType;
        this.toPlantTxID = toPlantTxID;
        this.verifiyDate = verifiyDate;
        this.nickname = nickname;
        this.timeStamp = timeStamp;
        this.token_number = token_number;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
