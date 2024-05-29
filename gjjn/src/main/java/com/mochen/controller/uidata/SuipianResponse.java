package com.mochen.controller.uidata;

import com.mochen.model.Jianniang;
import com.mochen.model.Suipian;

public class SuipianResponse {
    private Integer num;

    private Integer jnId;

    private String name;

    private Integer pinji;

    private String touxiang;

    private String color;

    private Integer spnum;

    private Boolean hecheng;

    public SuipianResponse() {}

    public SuipianResponse(Jianniang jn, int num) {
        this.jnId = jn.getId();
        this.name = jn.getName();
        this.pinji = jn.getPinji();
        this.touxiang = jn.getTouxiang();
        this.color = jn.getColor();
        this.spnum = jn.getSpnum();
        this.num = num;
        this.hecheng = num >= jn.getSpnum();
    }

    public SuipianResponse(Suipian sp) {
        this.num = sp.getNum();
        this.jnId = sp.getJnId();
        this.name = sp.getJn().getName();
        this.pinji = sp.getJn().getPinji();
        this.touxiang = sp.getJn().getTouxiang();
        this.color = sp.getJn().getColor();
        this.spnum = sp.getJn().getSpnum();
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

    public Boolean getHecheng() {
        return hecheng;
    }

    public void setHecheng(Boolean hecheng) {
        this.hecheng = hecheng;
    }
}
