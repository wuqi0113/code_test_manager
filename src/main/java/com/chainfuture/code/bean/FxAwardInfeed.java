package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FxAwardInfeed implements Serializable{
    private static final long serialVersionUID = -1670606595688819899L;

    private Integer id;
    private Integer reward;
    private Integer lcondit;
    private Integer hcondit;
    private Integer preward;

    public FxAwardInfeed() {super();}

    public FxAwardInfeed(Integer id, Integer reward, Integer lcondit, Integer hcondit, Integer preward) {
        super();
        this.id = id;
        this.reward = reward;
        this.lcondit = lcondit;
        this.hcondit = hcondit;
        this.preward = preward;
    }

    @Override
    public String toString() {
        return "FxAwardInfeed{" +
                "id=" + id +
                ", reward=" + reward +
                ", lcondit=" + lcondit +
                ", hcondit=" + hcondit +
                ", preward=" + preward +
                '}';
    }
}
