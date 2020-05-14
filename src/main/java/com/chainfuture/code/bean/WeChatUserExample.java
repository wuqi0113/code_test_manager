package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class WeChatUserExample  implements Serializable{

    private static final long serialVersionUID = 8849487369813667426L;
    private  Integer  id;
    private  String   nickname;
    private  String   openid;
    private  String   language;
    private  int     sex=0;
    private  String   province;
    private  String   city;
    private  String   country;
    private  String   headimgurl;
    private  String   unionid;
    private  String   phone;
    private  String   shareType;
    private  String   preUser;
    private  String   subscribe;
    private  String   address;
    private  int     status=0;
    private Integer   onlyOne;

    private List<WeChatUserExample>  weChatUserExampleList;

    public WeChatUserExample() { super(); }


    public WeChatUserExample(Integer id, String nickname, String openid, String language, int sex, String province, String city, String country, String headimgurl, String unionid, String phone, String shareType, String preUser, String subscribe, String address, int status, Integer onlyOne) {
        this.id = id;
        this.nickname = nickname;
        this.openid = openid;
        this.language = language;
        this.sex = sex;
        this.province = province;
        this.city = city;
        this.country = country;
        this.headimgurl = headimgurl;
        this.unionid = unionid;
        this.phone = phone;
        this.shareType = shareType;
        this.preUser = preUser;
        this.subscribe = subscribe;
        this.address = address;
        this.status = status;
        this.onlyOne = onlyOne;
    }

    @Override
    public String toString() {
        return "WeChatUserExample{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", openid='" + openid + '\'' +
                ", language='" + language + '\'' +
                ", sex=" + sex +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", unionid='" + unionid + '\'' +
                ", phone=" + phone +
                ", shareType='" + shareType + '\'' +
                ", preUser='" + preUser + '\'' +
                ", subscribe='" + subscribe + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                '}';
    }
}
