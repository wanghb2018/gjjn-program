package com.mochen.model;

import java.util.Optional;

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

	private Jianniang jn;

	public MyJianniang() {
	}

	public MyJianniang(Integer roleId, Jianniang jn, Integer iswar) {
		this.gongji = jn.getGongji();
		this.fangyu = jn.getFangyu();
		this.xueliang = jn.getXueliang();
		this.sudu = jn.getSudu();
		this.baoji = jn.getBaoji();
		this.duobi = jn.getDuobi();
		this.star = jn.getStar();
		this.iswar = iswar;
		this.jnId = jn.getId();
		this.roleId = roleId;
		this.level = 1;
		this.jingyan = 0;
		this.jn = jn;
	}

	public void calShuxing(Keyan keyan) {
		double gjRate = Optional.ofNullable(keyan).map(Keyan::getGjdj).orElse(0) * 0.1;
		double fyRate = Optional.ofNullable(keyan).map(Keyan::getFydj).orElse(0) * 0.1;
		double xlRate = Optional.ofNullable(keyan).map(Keyan::getXldj).orElse(0) * 0.1;
		double sdRate = Optional.ofNullable(keyan).map(Keyan::getSddj).orElse(0) * 0.1;
		double bjRate = Optional.ofNullable(keyan).map(Keyan::getBjdj).orElse(0);
		double dbRate = Optional.ofNullable(keyan).map(Keyan::getDbdj).orElse(0);
		double lvRate = (0.11 + 0.01 * jn.getPinji()) * (this.level - 1);
		int startDiff = this.star - jn.getStar();
		double startRate = 0;
		if (startDiff >= 1) {
			startRate = Math.log(startDiff) + 0.2 * startDiff + 0.8;
		}
		this.gongji = (int) (jn.getGongji() * (1 + lvRate + gjRate + startRate));
		this.fangyu = (int) (jn.getFangyu() * (1 + lvRate + fyRate + startRate));
		this.xueliang = (int) (jn.getXueliang() * (1 + lvRate + xlRate + startRate));
		this.sudu = (int) (jn.getSudu() * (1 + lvRate + sdRate + startRate));
		this.baoji = (int) (jn.getBaoji() + bjRate);
		this.duobi = (int) (jn.getDuobi() + dbRate);
		this.zdl = (int) (this.gongji * (1 + (this.baoji * 0.5 + this.duobi) / 100) + this.fangyu + this.xueliang
				+ this.sudu * 0.6);
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

	public Jianniang getJn() {
		return jn;
	}

	public void setJn(Jianniang jn) {
		this.jn = jn;
	}
}