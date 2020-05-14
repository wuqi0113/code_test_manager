package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SysModule  implements Serializable{
    private static final long serialVersionUID = -2794259905111321437L;

    private Integer id;
    private String  moduleName;
    private String  sname;
    private String  address;
    private String  workDescribe;
    private String  perms;

    public SysModule() {
        super();
    }

    public SysModule(Integer id, String moduleName, String sname, String address, String workDescribe, String perms) {
        super();
        this.id = id;
        this.moduleName = moduleName;
        this.sname = sname;
        this.address = address;
        this.workDescribe = workDescribe;
        this.perms = perms;
    }

    @Override
    public String toString() {
        return "SysModule{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\'' +
                ", sname='" + sname + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
