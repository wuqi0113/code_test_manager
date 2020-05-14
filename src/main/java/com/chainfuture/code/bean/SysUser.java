package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SysUser extends Page implements Serializable {

    private  Integer id;
    private  String  nickname;
    private  String  email;
    private  String  passwd;
    private  String  createTime;
    private  String  lastLoginTime;
    private  Integer status;

    public SysUser() {super();}

    public SysUser(Integer id, String nickname, String email, String passwd, String createTime, String lastLoginTime, Integer status) {
        super();
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.passwd = passwd;
        this.createTime = createTime;
        this.lastLoginTime = lastLoginTime;
        this.status = status;
    }
}
