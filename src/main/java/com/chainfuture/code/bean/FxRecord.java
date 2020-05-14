package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class FxRecord implements Serializable{
    private static final long serialVersionUID = 9003091349772007667L;

    private Integer id;
    private Integer userId;
    private Integer fxUserId;
    private String orderId;
    private Integer status;
    private String  time;
    private BigDecimal money;

    public FxRecord() {super();}

    public FxRecord(Integer id, Integer userId, Integer fxUserId, String orderId, Integer status, String time, BigDecimal money) {
        super();
        this.id = id;
        this.userId = userId;
        this.fxUserId = fxUserId;
        this.orderId = orderId;
        this.status = status;
        this.time = time;
        this.money = money;
    }

    @Override
    public String toString() {
        return "FxRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", fxUserId=" + fxUserId +
                ", orderId=" + orderId +
                ", status=" + status +
                ", time='" + time + '\'' +
                ", money=" + money +
                '}';
    }
}
