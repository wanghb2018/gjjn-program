package com.mochen.model;

public class Work {
	private Integer id;
	private String code;
	private String desc;
	private String myBranch;
	private String nowBranch;
	private String parentBranch;
	private String remark;
	private Integer status;
	private String version;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getMyBranch() {
		return myBranch;
	}
	public void setMyBranch(String myBranch) {
		this.myBranch = myBranch;
	}
	public String getNowBranch() {
		return nowBranch;
	}
	public void setNowBranch(String nowBranch) {
		this.nowBranch = nowBranch;
	}
	public String getParentBranch() {
		return parentBranch;
	}
	public void setParentBranch(String parentBranch) {
		this.parentBranch = parentBranch;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
