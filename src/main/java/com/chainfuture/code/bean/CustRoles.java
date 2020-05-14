package com.chainfuture.code.bean;

public class CustRoles {
    private Integer id;

    private String name;

    private Integer parentid;

    private String roleaddress;

    private String sname;

    private String rolesinfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getRoleaddress() {
        return roleaddress;
    }

    public void setRoleaddress(String roleaddress) {
        this.roleaddress = roleaddress == null ? null : roleaddress.trim();
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public String getRolesinfo() {
        return rolesinfo;
    }

    public void setRolesinfo(String rolesinfo) {
        this.rolesinfo = rolesinfo == null ? null : rolesinfo.trim();
    }
}