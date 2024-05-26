package com.mochen.controller.uidata;

import com.mochen.model.Suipian;

public class SuipianResponse {
    private Integer id;

    private Integer num;

    private Integer jnId;

    private Integer roleId;

    private String name;

    private Integer pinji;

    private String touxiang;

    private String color;

    private Integer spnum;

    public SuipianResponse() {}

    public SuipianResponse(Suipian sp) {
        this.id = sp.getId();
        this.num = sp.getNum();
        this.jnId = sp.getJnId();
        this.roleId = sp.getRoleId();
        this.name = sp.getJn().getName();
        this.pinji = sp.getJn().getPinji();
        this.touxiang = sp.getJn().getTouxiang();
        this.color = sp.getJn().getColor();
        this.spnum = sp.getJn().getSpnum();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getJnId() {
        return jnId;
    }

    public void setJnId(Integer jnId) {
        this.jnId = jnId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPinji() {
        return pinji;
    }

    public void setPinji(Integer pinji) {
        this.pinji = pinji;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSpnum() {
        return spnum;
    }

    public void setSpnum(Integer spnum) {
        this.spnum = spnum;
    }
}
