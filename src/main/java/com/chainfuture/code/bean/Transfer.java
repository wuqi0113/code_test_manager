package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Transfer extends Page implements Serializable {

    private  Integer  id;
    private  String  assetName;
    private  String  aipName;
    private  String   product;

    public Transfer() {super();}

    public Transfer(Integer id, String assetName, String aipName,  String product) {
        super();
        this.id = id;
        this.assetName = assetName;
        this.aipName = aipName;
        this.product = product;
    }
}
