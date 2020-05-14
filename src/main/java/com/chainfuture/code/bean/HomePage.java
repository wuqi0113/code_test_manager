package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class HomePage implements Serializable{
    private static final long serialVersionUID = 5768741422860139355L;

    private  Integer  id;
    private  String   headImg;
    private  String   assetName;
    private  String   companyIntroduce;
    private  String   contactInfo;
    private  String   phone;
    private  String   companyAddress;
    private  String   website;
    private  String   email;
    private  String   companyPublic;
    private  String   colorPickerHolder;
    private  String   honorBanner;
    private  String   honorContent;
    private  Integer  assetId;

    public HomePage() {super();}

    public HomePage(Integer assetId,String colorPickerHolder,Integer id, String headImg, String assetName, String companyIntroduce, String contactInfo, String phone, String companyAddress, String website, String email, String companyPublic,String   honorBanner,String   honorContent) {
        super();
        this.assetId = assetId;
        this.id = id;
        this.colorPickerHolder = colorPickerHolder;
        this.headImg = headImg;
        this.assetName = assetName;
        this.companyIntroduce = companyIntroduce;
        this.contactInfo = contactInfo;
        this.phone = phone;
        this.companyAddress = companyAddress;
        this.website = website;
        this.email = email;
        this.companyPublic = companyPublic;
        this.honorContent = honorContent;
        this.honorBanner = honorBanner;
    }

    @Override
    public String toString() {
        return "HomePage{" +
                "id=" + id +
                ", headImg='" + headImg + '\'' +
                ", assetName='" + assetName + '\'' +
                ", companyIntroduce='" + companyIntroduce + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", phone='" + phone + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", website='" + website + '\'' +
                ", email='" + email + '\'' +
                ", companyPublic='" + companyPublic + '\'' +
                ", colorPickerHolder='" + colorPickerHolder + '\'' +
                ", honorBanner='" + honorBanner + '\'' +
                ", honorContent='" + honorContent + '\'' +
                ", assetId=" + assetId +
                '}';
    }
}
