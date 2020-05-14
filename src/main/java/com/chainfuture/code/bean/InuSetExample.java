package com.chainfuture.code.bean;

import com.chainfuture.code.utils.BigDecimalMaxPrecision;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter@Setter
public class InuSetExample implements Serializable {
    private static final long serialVersionUID = -6068650367873523238L;

    private Integer  id;
    private Integer  year;
    private Integer  amount;
    private Integer  rewardNum;
    private Integer  multiple;

    private Integer insuranceType;

    public InuSetExample() {
        super();
    }

    public InuSetExample(Integer id, Integer year, Integer amount, Integer rewardNum, Integer multiple, Integer insuranceType) {
        super();
        this.id = id;
        this.year = year;
        this.amount = amount;
        this.rewardNum = rewardNum;
        this.multiple = multiple;
        this.insuranceType = insuranceType;
    }

    @Override
    public String toString() {
        return "InuSetExample{" +
                "id=" + id +
                ", year=" + year +
                ", amount=" + amount +
                ", rewardNum=" + rewardNum +
                ", multiple=" + multiple +
                ", insuranceType=" + insuranceType +
                '}';
    }
}
