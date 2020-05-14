package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthManage extends Page implements Serializable{

    private static final long serialVersionUID = -5202240238826383465L;
    private Integer mid;
    private String openId;
    private String address;
    private String phone;
    private Integer status;
    private String createTime;
    private String updateTime;
    private String manageAddr;
    private String operation;
    private Integer isPayment;
    private String  extend;
    private Integer specialValid;
    private String  dutyAddress;
    private String detailClause;
    private String  upLinkInfo;

    private String insurancePro;
    private String nickName;

    public AuthManage() {super();}

    public AuthManage(String upLinkInfo,Integer mid, String openId, String address, String phone, Integer status, String createTime, String updateTime,String manageAddr,String operation,Integer isPayment,String extend, Integer specialValid, String dutyAddress) {
        super();
        this.upLinkInfo = upLinkInfo;
        this.mid = mid;
        this.openId = openId;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.manageAddr = manageAddr;
        this.operation = operation;
        this.isPayment = isPayment;
        this.extend = extend;
        this.specialValid = specialValid;
        this.dutyAddress = dutyAddress;
    }

    @Override
    public String toString() {
        return "AuthManage{" +
                "mid=" + mid +
                ", openId='" + openId + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", manageAddr='" + manageAddr + '\'' +
                ", operation='" + operation + '\'' +
                ", isPayment=" + isPayment +
                ", extend='" + extend + '\'' +
                ", specialValid=" + specialValid +
                ", dutyAddress='" + dutyAddress + '\'' +
                ", detailClause='" + detailClause + '\'' +
                ", insurancePro='" + insurancePro + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
