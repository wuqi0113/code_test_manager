package com.chainfuture.code.filter;

import com.chainfuture.code.bean.Medal;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class LoginUser implements Serializable{

    private static final long serialVersionUID = 2465874784988485448L;
    private  Integer  id;
    private  String   nickname;
    private  String   openid;
    private  String   headimgurl;
    private  String   unionid;
    private  Integer  phone;
    private  String   address;
    private  int      status=0;

    public LoginUser() { super(); }

    public LoginUser(Integer id, String nickname, String openid, String headimgurl, String unionid, Integer phone, String address, int status) {
        this.id = id;
        this.nickname = nickname;
        this.openid = openid;
        this.headimgurl = headimgurl;
        this.unionid = unionid;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }
}
