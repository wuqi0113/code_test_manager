package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
public class MajorLog extends Page implements Serializable {

    private static final long serialVersionUID = 6837812734995616513L;
    private  Long    id;
    private  String  member;
    private  String  operatime;
    private  String  operation;
    private  String  method;
    private  String  remoteAddr;
    private  String  nodeAddress;


    public MajorLog() {super();}

    public MajorLog(Long id, String member, String operatime, String operation, String method, String remoteAddr,String nodeAddress) {
        super();
        this.id = id;
        this.member = member;
        this.operatime = operatime;
        this.operation = operation;
        this.method = method;
        this.remoteAddr = remoteAddr;
        this.nodeAddress = nodeAddress;
    }
}
