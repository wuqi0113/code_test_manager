package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SysManage implements Serializable{

    private static final long serialVersionUID = 7867600830055275062L;
    private Integer id;
    private String sname;
    private String address;
    private String phone;
    private Integer status;
    private String manageAddr;
    private String name;
    private Integer specialValid;

    public SysManage() {super();}

    public SysManage(Integer id,  String address, String phone, Integer status,  String manageAddr, String sname, String name, Integer specialValid) {
        this.id = id;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.manageAddr = manageAddr;
        this.sname = sname;
        this.name = name;
        this.specialValid = specialValid;
    }


    @Override
    public String toString() {
        return "SysManage{" +
                "id=" + id +
                ", sname='" + sname + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", manageAddr='" + manageAddr + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
