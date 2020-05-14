package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class SignMsg extends Page implements Serializable {
    private static final long serialVersionUID = -584130838176017049L;

    private Long  id;
    private String proAddress;
    private Integer  num;
    private Integer  status;
    private Long  signId;
    private String bindAddress;
    private String  sname;
    private Integer type;
    private Integer amount;
    private Integer mold;
    private String producer;
    private Integer verfiyNum;

    public SignMsg() {super();}

    public SignMsg(Long id, String proAddress, Integer num, Integer status, Long signId, String bindAddress, String sname, Integer type, Integer amount, Integer mold, String producer,Integer verfiyNum) {
        super();
        this.id = id;
        this.proAddress = proAddress;
        this.num = num;
        this.status = status;
        this.signId = signId;
        this.bindAddress = bindAddress;
        this.sname = sname;
        this.type = type;
        this.amount = amount;
        this.mold = mold;
        this.producer = producer;
        this.verfiyNum = verfiyNum;
    }

    @Override
    public String toString() {
        return "SignMsg{" +
                "id=" + id +
                ", proAddress='" + proAddress + '\'' +
                ", num=" + num +
                ", status=" + status +
                ", signId=" + signId +
                '}';
    }
}
