package com.mochen.controller.uidata;

import java.util.List;

import com.mochen.model.Suipian;

public class MapBossData {
	private List<Suipian> sps;
	private Integer zhgjy;
	private Integer jnjy;
	private Integer wz;
	private Integer openId;
	private Integer guajiId;

	public List<Suipian> getSps() {
		return sps;
	}

	public void setSps(List<Suipian> sps) {
		this.sps = sps;
	}

	public Integer getZhgjy() {
		return zhgjy;
	}

	public void setZhgjy(Integer zhgjy) {
		this.zhgjy = zhgjy;
	}

	public Integer getJnjy() {
		return jnjy;
	}

	public void setJnjy(Integer jnjy) {
		this.jnjy = jnjy;
	}

	public Integer getWz() {
		return wz;
	}

	public void setWz(Integer wz) {
		this.wz = wz;
	}

	public Integer getOpenId() {
		return openId;
	}

	public void setOpenId(Integer openId) {
		this.openId = openId;
	}

	public Integer getGuajiId() {
		return guajiId;
	}

	public void setGuajiId(Integer guajiId) {
		this.guajiId = guajiId;
	}
}
