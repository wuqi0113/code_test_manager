package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserMedal extends Page implements Serializable {
    private static final long serialVersionUID = 6404200642313844800L;

    private  Integer  id;
    private  Integer  userId;
    private  Integer  medalId;
    private  Integer  total;

    public UserMedal() {super();}

    public UserMedal(Integer id, Integer userId, Integer medalId, Integer total) {
        super();
        this.id = id;
        this.userId = userId;
        this.medalId = medalId;
        this.total = total;
    }
}
