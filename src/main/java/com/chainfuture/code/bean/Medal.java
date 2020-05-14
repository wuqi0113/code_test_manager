package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class Medal extends Page implements Serializable{

    private static final long serialVersionUID = 2904930560966471223L;
    private Integer id;//id
    private String medalIcon;//图标
    private String medalName;//图标名称
    private Integer status;//状态
    private String  createTime;//创建时间
    private String updateTime;//更新时间
    private String describe;//描述
    private String medalAddr;//图标地址
    private String product;
    private String sname;
    private String user;


    public Medal() {
        super();
    }

    public Medal(Integer id, String medalIcon, String medalName, Integer status, String createTime, String updateTime, String describe, String medalAddr, String product, String sname,String user) {
        super();
        this.id = id;
        this.medalIcon = medalIcon;
        this.medalName = medalName;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.describe = describe;
        this.medalAddr = medalAddr;
        this.product = product;
        this.sname = sname;
        this.user = user;
    }
}
