package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BindUserExample implements Serializable{
    private static final long serialVersionUID = 4524347030672101814L;

    private Integer  id;
    private String   userId;
    private String   address;

    public BindUserExample() {
        super();
    }

    public BindUserExample(Integer id, String userId, String address) {
        super();
        this.id = id;
        this.userId = userId;
        this.address = address;
    }
}
