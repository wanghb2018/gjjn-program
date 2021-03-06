package com.mochen.model;

public class Suipian {
    private Integer id;

    private Integer num = 0;

    private Integer jnId;

    private Integer roleId;

    private String name;

    private Integer pinji;

    private String touxiang;

    private String color;

    private Integer spnum;
    
    public Suipian() {}
    
    public Suipian(Integer roleId, Jianniang jn, Integer num) {
    	this.num = num;
    	this.jnId = jn.getId();
    	this.roleId = roleId;
    	this.name = jn.getName();
    	this.pinji = jn.getPinji();
    	this.touxiang = jn.getTouxiang();
    	this.color = jn.getColor();
    	this.spnum = jn.getSpnum();
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
        this.name = name == null ? null : name.trim();
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
        this.touxiang = touxiang == null ? null : touxiang.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public Integer getSpnum() {
        return spnum;
    }

    public void setSpnum(Integer spnum) {
        this.spnum = spnum;
    }
}