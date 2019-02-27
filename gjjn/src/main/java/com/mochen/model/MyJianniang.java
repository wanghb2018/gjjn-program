package com.mochen.model;

public class MyJianniang {
	private Integer id;

	private Integer gongji;

	private Integer fangyu;

	private Integer xueliang;

	private Integer sudu;

	private Integer baoji;

	private Integer duobi;

	private Integer star;

	private Integer level;

	private Integer iswar;

	private Integer zdl;

	private Integer jingyan;

	private Integer jnId;

	private Integer roleId;

	public MyJianniang() {

	}

	public MyJianniang(Integer roleId, JianniangWithBLOBs jn, Integer isWar) {
		this.gongji = jn.getGongji();
		this.fangyu = jn.getFangyu();
		this.xueliang = jn.getXueliang();
		this.sudu = jn.getSudu();
		this.baoji = jn.getBaoji();
		this.duobi = jn.getDuobi();
		this.star = jn.getStar();
		this.level = 1;
		this.jingyan = 0;
		this.iswar = isWar;
		this.jnId = jn.getId();
		this.roleId = roleId;
	}

	public void calShuxing(Jianniang jn, Keyan keyan, double jxRate) {
		double gjRate = 1.1 * keyan.getGjdj() * keyan.getGjdj();
		double fyRate = 1.1 * keyan.getFydj() * keyan.getFydj();
		double xlRate = 1.1 * keyan.getXldj() * keyan.getXldj();
		double sdRate = 1.1 * keyan.getSddj() * keyan.getSddj();
		double bjRate = 2 * keyan.getBjdj();
		double dbRate = 2 * keyan.getDbdj();
		double lvRate = 1 + (0.11 + 0.02 * jn.getPinji()) * (this.level - 1);
		double r = lvRate * jxRate;
		this.gongji =  (int) (jn.getGongji() * gjRate * r);
		this.fangyu = (int) (jn.getFangyu() * fyRate * r);
		this.xueliang = (int) (jn.getXueliang() * xlRate * r);
		this.sudu = (int) (jn.getSudu() * sdRate * r);
		this.baoji = (int) (jn.getBaoji() + bjRate);
		this.duobi = (int) (jn.getDuobi() + dbRate);
		calJNZdl();
	}

	public void calJNZdl() {
		this.zdl = (int) ((this.gongji * (1 + (this.baoji * 0.5 + this.duobi) / 100) + this.fangyu + this.xueliang
				+ this.sudu * 0.6) * (1 + 1 * this.star));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGongji() {
		return gongji;
	}

	public void setGongji(Integer gongji) {
		this.gongji = gongji;
	}

	public Integer getFangyu() {
		return fangyu;
	}

	public void setFangyu(Integer fangyu) {
		this.fangyu = fangyu;
	}

	public Integer getXueliang() {
		return xueliang;
	}

	public void setXueliang(Integer xueliang) {
		this.xueliang = xueliang;
	}

	public Integer getSudu() {
		return sudu;
	}

	public void setSudu(Integer sudu) {
		this.sudu = sudu;
	}

	public Integer getBaoji() {
		return baoji;
	}

	public void setBaoji(Integer baoji) {
		this.baoji = baoji;
	}

	public Integer getDuobi() {
		return duobi;
	}

	public void setDuobi(Integer duobi) {
		this.duobi = duobi;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getIswar() {
		return iswar;
	}

	public void setIswar(Integer iswar) {
		this.iswar = iswar;
	}

	public Integer getZdl() {
		return zdl;
	}

	public void setZdl(Integer zdl) {
		this.zdl = zdl;
	}

	public Integer getJingyan() {
		return jingyan;
	}

	public void setJingyan(Integer jingyan) {
		this.jingyan = jingyan;
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
}