package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class InuPriceExample  implements Serializable{

    private static final long serialVersionUID = -5261253024150334207L;
    private Integer  id;
    private BigDecimal price;
    private String recordTime;

    public InuPriceExample() {
        super();
    }

    public InuPriceExample(Integer id, BigDecimal price, String recordTime) {
        super();
        this.id = id;
        this.price = price;
        this.recordTime = recordTime;
    }
}
