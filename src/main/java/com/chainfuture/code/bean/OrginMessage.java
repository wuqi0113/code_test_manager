package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class OrginMessage extends Page implements Serializable {
    private static final long serialVersionUID = -2280260434884673427L;
    private  Integer  id;
    private  String   proAddress;
    private  String   originMessage;
    private  String   signMessage;
    private  Integer  codeId;

    public OrginMessage() {super();}

    public OrginMessage(Integer id, String proAddress, String originMessage, String signMessage, Integer codeId) {
        super();
        this.id = id;
        this.proAddress = proAddress;
        this.originMessage = originMessage;
        this.signMessage = signMessage;
        this.codeId = codeId;
    }
}
