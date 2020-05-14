package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ProductTrace implements Serializable {
    private static final long serialVersionUID = -2280260434884673427L;
    private  Integer  id;
    private  String   proAddress;
    private  String   message;
    private  String   location;
    private  String   img;
    private  String   openId;
    private  String  singleId;

    public ProductTrace() {super();}

    public ProductTrace(Integer id, String proAddress, String message, String location, String img, String openId, String singleId) {
        super();
        this.id = id;
        this.proAddress = proAddress;
        this.message = message;
        this.location = location;
        this.img = img;
        this.openId = openId;
        this.singleId = singleId;
    }
}
