package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SysUserCarteExample implements Serializable{
    private static final long serialVersionUID = -7042341648404291441L;

    private Integer id;
    private String  userAddress;
    private Integer carteId;

    public SysUserCarteExample() {
        super();
    }

    public SysUserCarteExample(Integer id, String userAddress, Integer carteId) {
        super();
        this.id = id;
        this.userAddress = userAddress;
        this.carteId = carteId;
    }
}
