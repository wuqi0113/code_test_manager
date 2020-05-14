package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SignDetail implements Serializable {
    private static final long serialVersionUID = 10862893729795052L;
    private  Long    id;
    private  Long    signId;
    private  String  effectTime;
    private  String  uneffectTime;
    private  String  receiveAddress;
    private  Integer status;
    private  String  proAddress;

    public SignDetail() {super();}

    public SignDetail(Long id, Long signId, String effectTime, String uneffectTime, String receiveAddress, Integer status, String proAddress) {
        super();
        this.id = id;
        this.signId = signId;
        this.effectTime = effectTime;
        this.uneffectTime = uneffectTime;
        this.receiveAddress = receiveAddress;
        this.status = status;
        this.proAddress = proAddress;
    }
}
