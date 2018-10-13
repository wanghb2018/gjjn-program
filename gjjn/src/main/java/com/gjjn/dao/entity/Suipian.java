package com.gjjn.dao.entity;

public class Suipian {
    private Integer id;

    private Integer num;

    private Integer jianniangId;

    private Integer roleId;
    
    private String name;
    
    private String touxiang;
    
    private String color;
    
    private Integer spnum;
    
    private Integer pinji;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getPinji() {
		return pinji;
	}

	public void setPinji(Integer pinji) {
		this.pinji = pinji;
	}

	public Suipian() {
    	
    }
    
    public Suipian(Integer roleId, Integer jianniangId, Integer num) {
    	this.roleId = roleId;
    	this.jianniangId = jianniangId;
    	this.num = num;
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

    public Integer getJianniangId() {
        return jianniangId;
    }

    public void setJianniangId(Integer jianniangId) {
        this.jianniangId = jianniangId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}