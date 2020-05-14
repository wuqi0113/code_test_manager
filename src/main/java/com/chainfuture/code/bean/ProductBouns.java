package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductBouns implements Serializable{
    private static final long serialVersionUID = -3094952537152177165L;

    private Integer id;
    private String  time;
    private Integer type;
    private Integer pid;
    private Integer amount;

    public ProductBouns() {super();}

    public ProductBouns(Integer id, String time, Integer type, Integer pid,Integer amount) {
        super();
        this.id = id;
        this.time = time;
        this.type = type;
        this.pid = pid;
        this.amount = amount;
    }
}
