package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ProductTraceability extends Page implements Serializable {
    private static final long serialVersionUID = 2267525168207697087L;

    private Integer  id;
    private String  proAddress;
    private String  enterPrise;
    private String  handler;
    private String  itemName;
    private String  itemSize;
    private String  time;
    private String  remarks;
    private String  longitude;
    private String  latitude;
    private String  country;
    private String  province;
    private String  city;
    private String  area;
    private String  detailInfo;
    private String  image;


    public ProductTraceability() {super();}

    public ProductTraceability(String image,Integer id, String proAddress, String enterPrise, String handler, String itemName, String itemSize, String time, String remarks, String longitude, String latitude, String country, String province, String city, String area, String detailInfo) {
        super();
        this.image = image;
        this.id = id;
        this.proAddress = proAddress;
        this.enterPrise = enterPrise;
        this.handler = handler;
        this.itemName = itemName;
        this.itemSize = itemSize;
        this.time = time;
        this.remarks = remarks;
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
        this.province = province;
        this.city = city;
        this.area = area;
        this.detailInfo = detailInfo;
    }

    @Override
    public String toString() {
        return "ProductTraceability{" +
                "id='" + id + '\'' +
                ", proAddress='" + proAddress + '\'' +
                ", enterPrise='" + enterPrise + '\'' +
                ", handler='" + handler + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemSize='" + itemSize + '\'' +
                ", time='" + time + '\'' +
                ", remarks='" + remarks + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", detailInfo='" + detailInfo + '\'' +
                '}';
    }
}
