package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class MedalAward extends Page implements Serializable {
    private static final long serialVersionUID = -5568789646977172199L;

    private Integer  id;
    private String   medalName;
    private String   prefit;
    private Integer  type;
    private Integer  status;
    private String   assetName;
    private String   product;

    public MedalAward() {super();}

    public MedalAward(Integer id, String medalName, String prefit, Integer type, Integer status,String assetName,String product) {
        super();
        this.id = id;
        this.medalName = medalName;
        this.prefit = prefit;
        this.type = type;
        this.status = status;
        this.assetName = assetName;
        this.product = product;
    }
}
