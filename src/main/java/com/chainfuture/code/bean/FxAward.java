package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class FxAward implements Serializable{
    private static final long serialVersionUID = 6094616811089744255L;

    private Integer id;
    private Integer award;
    private Integer level;
    private String  levelName;
    private Integer lterm;
    private Integer hterm;

    public FxAward() {super();}

    public FxAward(Integer id, Integer award, Integer level, String levelName, Integer lterm, Integer hterm) {
        super();
        this.id = id;
        this.award = award;
        this.level = level;
        this.levelName = levelName;
        this.lterm = lterm;
        this.hterm = hterm;
    }

    @Override
    public String toString() {
        return "FxAward{" +
                "id=" + id +
                ", award=" + award +
                ", level=" + level +
                ", levelName='" + levelName + '\'' +
                ", lterm=" + lterm +
                ", hterm=" + hterm +
                '}';
    }
}
