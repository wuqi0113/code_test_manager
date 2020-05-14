package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class InuBaseExample extends Page implements Serializable {

    private static final long serialVersionUID = -6456317805610406805L;
    private Integer  id;
    private Integer  insuranceType;//	int	5	0	-1	0	0	0				保障类型编号					0	0	0	0	0	0	0
    private String   insuranceName;//	varchar	100	0	-1	0	0	0				保障类型中文			utf8	utf8_general_ci	0	0	0	0	0	0	0
    private Integer  effectiveDate;//	int	10	0	-1	0	0	0				当前类型的等待期					0	0	0	0	0	0	0
    private String   insurancePro;//	varchar	255	0	-1	0	0	0				当前保单的地址			utf8	utf8_general_ci	0	0	0	0	0	0	0
    private String   sname;//	varchar	100	0	-1	0	0	0				当前保单地址的英文名称			utf8	utf8_general_ci	0	0	0	0	0	0	0
    private String   detailClause;

    public InuBaseExample() {super();}

    public InuBaseExample(Integer id, Integer insuranceType, String insuranceName, Integer effectiveDate, String insurancePro, String sname, String detailClause) {
        super();
        this.id = id;
        this.insuranceType = insuranceType;
        this.insuranceName = insuranceName;
        this.effectiveDate = effectiveDate;
        this.insurancePro = insurancePro;
        this.sname = sname;
        this.detailClause = detailClause;
    }
}
