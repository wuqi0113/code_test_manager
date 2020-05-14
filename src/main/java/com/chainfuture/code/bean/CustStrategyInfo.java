package com.chainfuture.code.bean;

public class CustStrategyInfo {
    private Integer id;

    private String stratename;

    private String stratemem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStratename() {
        return stratename;
    }

    public void setStratename(String stratename) {
        this.stratename = stratename == null ? null : stratename.trim();
    }

    public String getStratemem() {
        return stratemem;
    }

    public void setStratemem(String stratemem) {
        this.stratemem = stratemem == null ? null : stratemem.trim();
    }
}