package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SysCarteExample implements Serializable{
    private static final long serialVersionUID = -74231721202118199L;

    private Integer  id;
    private String   carteName;
    private String   href;
    private Integer  isShow;

    public SysCarteExample() {
        super();
    }

    public SysCarteExample(Integer id, String carteName, String href, Integer isShow) {
        super();
        this.id = id;
        this.carteName = carteName;
        this.href = href;
        this.isShow = isShow;
    }
}
