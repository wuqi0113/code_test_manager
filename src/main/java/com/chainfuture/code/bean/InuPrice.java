package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class InuPrice extends Page implements Serializable{
    private static final long serialVersionUID = -3240442418952001337L;

    private Integer  id;
    private BigDecimal price;
    private String recordTime;

    public InuPrice() {
        super();
    }

    public InuPrice(Integer id, BigDecimal price, String recordTime) {
        super();
        this.id = id;
        this.price = price;
        this.recordTime = recordTime;
    }
}
