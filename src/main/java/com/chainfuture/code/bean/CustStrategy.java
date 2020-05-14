package com.chainfuture.code.bean;

import java.util.List;

public class CustStrategy {
    private Integer id;

    private String roles;

    private String idents;

    private Integer strateid;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles == null ? null : roles.trim();
    }


    public Integer getStrateid() {
        return strateid;
    }

    public void setStrateid(Integer strateid) {
        this.strateid = strateid;
    }

    public String getIdents() {
        return idents;
    }

    public void setIdents(String idents) {
        this.idents = idents == null ? null : idents.trim();
    }

}