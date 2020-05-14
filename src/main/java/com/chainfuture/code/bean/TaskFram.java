package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class TaskFram implements Serializable{
    private static final long serialVersionUID = 2367367192757409187L;

    private Integer id;
    private String  taskName;
    private String  taskUrl;
    private String  moduleName;
    private Integer parentId;

    public TaskFram() {
        super();
    }

    public TaskFram(Integer id, String taskName, String taskUrl, String moduleName, Integer parentId) {
        super();
        this.id = id;
        this.taskName = taskName;
        this.taskUrl = taskUrl;
        this.moduleName = moduleName;
        this.parentId = parentId;
    }
}
