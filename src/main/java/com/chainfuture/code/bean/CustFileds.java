package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustFileds {
    private Integer id;

    private String name;

    private String sname;

    private String unit;

    private String issingle;

    private Integer lengthes;

    private String stype;

    private Integer  parentid;

    private String isverifiy;

    public CustFileds() {super();}

    public CustFileds(Integer id, String name, String sname, String unit, String issingle, Integer lengthes, String stype, Integer parentid, String isverifiy) {
        super();
        this.id = id;
        this.name = name;
        this.sname = sname;
        this.unit = unit;
        this.issingle = issingle;
        this.lengthes = lengthes;
        this.stype = stype;
        this.parentid = parentid;
        this.isverifiy = isverifiy;
    }

    @Override
    public String toString() {
        return "CustFileds{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sname='" + sname + '\'' +
                ", unit='" + unit + '\'' +
                ", issingle='" + issingle + '\'' +
                ", lengthes=" + lengthes +
                ", stype='" + stype + '\'' +
                ", parentid=" + parentid +
                ", isverifiy='" + isverifiy + '\'' +
                '}';
    }
}