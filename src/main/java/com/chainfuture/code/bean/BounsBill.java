package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BounsBill implements Serializable{
    private static final long serialVersionUID = 8562028103616163962L;

    private Integer id;
    private String  productName;
    private Long    signId;
    private Integer amount;
    private String  createTime;
    private String  receiveTime;
    private String  receiveAddress;
    private Integer status;

    public BounsBill() {super();}

    public BounsBill(Integer id, String productName, Long signId, Integer amount, String createTime, String receiveTime, String receiveAddress, Integer status) {
        super();
        this.id = id;
        this.productName = productName;
        this.signId = signId;
        this.amount = amount;
        this.createTime = createTime;
        this.receiveTime = receiveTime;
        this.receiveAddress = receiveAddress;
        this.status = status;
    }
}
