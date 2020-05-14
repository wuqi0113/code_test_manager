package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SysUserModuleExample implements Serializable{
    private static final long serialVersionUID = 5316164967057977840L;

    private Integer id;
    private Integer moduleId;
    private Integer userId;

    public SysUserModuleExample() {
        super();
    }

    public SysUserModuleExample(Integer id, Integer moduleId, Integer userId) {
        super();
        this.id = id;
        this.moduleId = moduleId;
        this.userId = userId;
    }
}
