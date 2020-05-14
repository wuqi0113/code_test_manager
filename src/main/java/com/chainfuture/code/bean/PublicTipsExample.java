package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
public class PublicTipsExample implements Serializable{
    private static final long serialVersionUID = 859542053740171184L;

    private Integer  id;
    private String   identify;
    private String   message;
    private Integer  type;

    public PublicTipsExample() {
        super();
    }

    public PublicTipsExample(Integer id, String identify, String message, Integer type) {
        super();
        this.id = id;
        this.identify = identify;
        this.message = message;
        this.type = type;
    }
}
