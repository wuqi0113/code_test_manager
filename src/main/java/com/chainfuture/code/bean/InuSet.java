package com.chainfuture.code.bean;

import com.chainfuture.code.utils.BigDecimalMaxPrecision;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class InuSet extends Page implements Serializable {

    private static final long serialVersionUID = -79476278791698570L;
    private Integer  id;
    private Integer  year;
    private Integer  amount;
    private Integer  rewardNum;
    private Integer  multiple;

    private Integer insuranceType;

    public InuSet() {
        super();
    }

    public InuSet(Integer id, Integer year, Integer amount, Integer rewardNum, Integer multiple, Integer insuranceType) {
        super();
        this.id = id;
        this.year = year;
        this.amount = amount;
        this.rewardNum = rewardNum;
        this.multiple = multiple;
        this.insuranceType = insuranceType;
    }
}
