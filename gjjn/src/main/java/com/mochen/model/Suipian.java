package com.mochen.model;

public class Suipian {
    private Integer id;

    private Integer num = 0;

    private Integer jnId;

    private Integer roleId;

    private Jianniang jn;
    
    public Suipian() {}
    
    public Suipian(Integer roleId, Jianniang jn, Integer num) {
    	this.num = num;
    	this.jnId = jn.getId();
    	this.roleId = roleId;
        this.jn = jn;
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

    public Integer getPinji() {
        if (jn == null) {
            return 0;
        }
        return jn.getPinji();
    }

    public Jianniang getJn() {
        return jn;
    }

    public void setJn(Jianniang jn) {
        this.jn = jn;
    }
}