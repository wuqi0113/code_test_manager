package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ProductExample  implements Serializable{

    private static final long serialVersionUID = 7300664028853277208L;
    private  Integer  id;
    private  String   proName;
    private  String   proAddress;
    private  String   proDescribe;
    private  String   proDisplay;
    private  int      reward=1;
    private  Integer  medalId;
    private  String   rewardWhere;
    private  String   createTime;
    private  Integer  status;
    private  int      proNum=0;
    private  String   producer;
    private  String   updateTime;
    private  String   primeAddr;
    private  String   sname;
    private  Integer  assetId;
    private  String   proAdvertImg;
    private  String   extraInfo;
    private  String   productLicense;
    private  String   productSize;
    private  String   qualityPeriod;
    private  String   classify;
    private  String   proThumbnail;
    private  Integer  orderId;
    private  Integer  type;
    private  Integer  activityCodeType;
    private  Integer  isGroup;
    private  Integer  productPeriod;
    private  Integer  bouns;

    private  String   name_en;
    private String  verfiyNum;

    public ProductExample() {super();}

    public ProductExample(Integer bouns,Integer productPeriod,Integer isGroup,Integer activityCodeType,Integer type,Integer id, String proThumbnail, String classify, String proName, String proAddress, String proDescribe, String proDisplay, int reward, Integer medalId, String rewardWhere, String createTime, Integer status, int proNum, String producer, String updateTime, String primeAddr, String sname, Integer assetId, String proAdvertImg, String extraInfo, String productLicense, String productSize, String qualityPeriod, String name_en, String verfiyNum, Integer orderId) {
        super();
        this.bouns = bouns;
        this.productPeriod = productPeriod;
        this.isGroup = isGroup;
        this.type = type;
        this.id = id;
        this.proThumbnail = proThumbnail;
        this.classify = classify;
        this.proName = proName;
        this.proAddress = proAddress;
        this.proDescribe = proDescribe;
        this.proDisplay = proDisplay;
        this.reward = reward;
        this.medalId = medalId;
        this.rewardWhere = rewardWhere;
        this.createTime = createTime;
        this.status = status;
        this.proNum = proNum;
        this.producer = producer;
        this.updateTime = updateTime;
        this.primeAddr = primeAddr;
        this.sname = sname;
        this.assetId = assetId;
        this.proAdvertImg = proAdvertImg;
        this.extraInfo = extraInfo;
        this.productLicense = productLicense;
        this.productSize = productSize;
        this.qualityPeriod = qualityPeriod;
        this.name_en = name_en;
        this.verfiyNum = verfiyNum;
        this.orderId = orderId;
        this.activityCodeType = activityCodeType;
    }

    @Override
    public String toString() {
        return "ProductExample{" +
                "id=" + id +
                ", proName='" + proName + '\'' +
                ", proAddress='" + proAddress + '\'' +
                ", proDescribe='" + proDescribe + '\'' +
                ", proDisplay='" + proDisplay + '\'' +
                ", reward=" + reward +
                ", medalId=" + medalId +
                ", rewardWhere='" + rewardWhere + '\'' +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                ", proNum=" + proNum +
                ", producer='" + producer + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", primeAddr='" + primeAddr + '\'' +
                ", sname='" + sname + '\'' +
                ", assetId=" + assetId +
                ", proAdvertImg='" + proAdvertImg + '\'' +
                ", extraInfo='" + extraInfo + '\'' +
                ", productLicense='" + productLicense + '\'' +
                ", productSize='" + productSize + '\'' +
                ", qualityPeriod='" + qualityPeriod + '\'' +
                ", classify='" + classify + '\'' +
                ", proThumbnail='" + proThumbnail + '\'' +
                ", orderId=" + orderId +
                ", type=" + type +
                ", name_en='" + name_en + '\'' +
                ", verfiyNum='" + verfiyNum + '\'' +
                '}';
    }
}
