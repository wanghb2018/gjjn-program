package com.mochen.controller.uidata;

import java.util.List;

import com.mochen.model.Suipian;

public class QiandaoData {
	private List<SuipianResponse> sps;
	private Integer zuanshi;
	private Integer mofang;
	private Integer shiyou;

	public List<SuipianResponse> getSps() {
		return sps;
	}

	public void setSps(List<SuipianResponse> sps) {
		this.sps = sps;
	}

	public Integer getZuanshi() {
		return zuanshi;
	}

	public void setZuanshi(Integer zuanshi) {
		this.zuanshi = zuanshi;
	}

	public Integer getMofang() {
		return mofang;
	}

	public void setMofang(Integer mofang) {
		this.mofang = mofang;
	}

	public Integer getShiyou() {
		return shiyou;
	}

	public void setShiyou(Integer shiyou) {
		this.shiyou = shiyou;
	}

}
