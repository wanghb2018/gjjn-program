package com.mochen.controller.uidata;

import java.util.Date;
import java.util.List;

import com.mochen.model.Suipian;

public class MapGuajiData {
	private List<Suipian> sps;
	private Integer jy;
	private Integer wz;
	private Integer sec;
	private Date guajitime;

	public MapGuajiData() {
	}

	public MapGuajiData(List<Suipian> sps, Integer jy, Integer wz, Integer sec, Date guajitime) {
		this.sps = sps;
		this.jy = jy;
		this.wz = wz;
		this.sec = sec;
		this.guajitime = guajitime;
	}

	public List<Suipian> getSps() {
		return sps;
	}

	public void setSps(List<Suipian> sps) {
		this.sps = sps;
	}

	public Integer getJy() {
		return jy;
	}

	public void setJy(Integer jy) {
		this.jy = jy;
	}

	public Integer getWz() {
		return wz;
	}

	public void setWz(Integer wz) {
		this.wz = wz;
	}

	public Integer getSec() {
		return sec;
	}

	public void setSec(Integer sec) {
		this.sec = sec;
	}

	public Date getGuajitime() {
		return guajitime;
	}

	public void setGuajitime(Date guajitime) {
		this.guajitime = guajitime;
	}
}
