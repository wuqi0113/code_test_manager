package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class FxUser implements Serializable{
    private static final long serialVersionUID = 7044836188584983865L;

    private Integer id;
    private String  userName;
    private String  headImage;
    private Integer level;
    private String  address;
    private String  idNum;
    private String  busiLicense;
    private String  region;
    private String  phone;
    private Integer status;
    private String  inviteCode;
    private Integer oneCount;
    private Integer twoCount;
    private Integer umbrellaCount;
    private Integer pid;


    public FxUser() {super();}

    public FxUser(Integer id, String userName, String headImage, Integer level, String address, String idNum, String busiLicense, String region, String phone, Integer status, String inviteCode) {
        super();
        this.id = id;
        this.userName = userName;
        this.headImage = headImage;
        this.level = level;
        this.address = address;
        this.idNum = idNum;
        this.busiLicense = busiLicense;
        this.region = region;
        this.phone = phone;
        this.status = status;
        this.inviteCode = inviteCode;
    }


}
