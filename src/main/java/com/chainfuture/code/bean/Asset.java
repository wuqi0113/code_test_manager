package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Asset implements Serializable{
    private static final long serialVersionUID = -2748505166104863563L;

    private java.lang.Integer id;
    private java.lang.String sname;
    private java.lang.String name;
    private java.lang.String name_en;
    private java.lang.Long circulation;
    private java.lang.String issueAddress;
    private java.lang.String issueTime;
    private java.lang.Integer status;
    private java.lang.String imageUrl;
    private java.lang.String produtIntroUrl;
    private java.lang.String issueDatum;
    private java.lang.String title;
    private java.lang.String content;

    public Asset() {super();}

    public Asset(Integer id, String sname, String name, String name_en, Long circulation, String issueAddress, String issueTime, Integer status, String imageUrl, String produtIntroUrl, String issueDatum, String title, String content) {
        super();
        this.id = id;
        this.sname = sname;
        this.name = name;
        this.name_en = name_en;
        this.circulation = circulation;
        this.issueAddress = issueAddress;
        this.issueTime = issueTime;
        this.status = status;
        this.imageUrl = imageUrl;
        this.produtIntroUrl = produtIntroUrl;
        this.issueDatum = issueDatum;
        this.title = title;
        this.content = content;
    }
}
