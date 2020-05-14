package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class MedalIcon extends Page implements Serializable {
    private static final long serialVersionUID = 1493370282790799379L;

    private  Integer  id;
    private  String   icon;

    public MedalIcon() { super();}

    public MedalIcon(Integer id, String icon) {
        super();
        this.id = id;
        this.icon = icon;
    }
}
