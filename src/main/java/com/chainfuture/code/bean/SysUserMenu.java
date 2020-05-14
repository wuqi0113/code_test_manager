package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class SysUserMenu implements Serializable {

    private static final long serialVersionUID = 6325921621476774785L;
    private Integer  id;
    private String   openId;
    private Integer  mid;

    @Override
    public String toString() {
        return "SysUserMenu{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", mid=" + mid +
                '}';
    }
}
