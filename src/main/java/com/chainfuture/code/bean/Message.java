package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Message extends Page implements Serializable {
    private static final long serialVersionUID = -2280260434884673427L;

    private  Integer  id;
    private  String   address;
    private  String   originMessage;
    private  String   signMessage;
    private  Integer  codeId;

    public Message() {super();}

    public Message(Integer id, String address, String originMessage, String signMessage, Integer codeId) {
        super();
        this.id = id;
        this.address = address;
        this.originMessage = originMessage;
        this.signMessage = signMessage;
        this.codeId = codeId;
    }
}
