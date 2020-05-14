package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Setter
@Getter
public class FxOrder implements Serializable{
    private static final long serialVersionUID = -3111164508687049688L;

    private Integer id;
    private String  goodName;
    private Integer goodPrice;
    private String orderId;
    private Integer userId;
    private Integer pid;

    public FxOrder() {super();}

    public FxOrder(Integer id, String goodName, Integer goodPrice, String orderId, Integer userId, Integer pid) {
        super();
        this.id = id;
        this.goodName = goodName;
        this.goodPrice = goodPrice;
        this.orderId = orderId;
        this.userId = userId;
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "FxOrder{" +
                "id=" + id +
                ", goodName='" + goodName + '\'' +
                ", goodPrice=" + goodPrice +
                ", orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", pid=" + pid +
                '}';
    }
}
