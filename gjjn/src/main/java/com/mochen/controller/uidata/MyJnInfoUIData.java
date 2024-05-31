package com.mochen.controller.uidata;

import com.mochen.model.JianniangSJ;
import com.mochen.model.MyJianniang;

public class MyJnInfoUIData {
	private MyJNResponse jn;
	private JianniangSJ sj;
	private Integer num;
	private Integer zblNum;
	private Integer jblNum;
	
	public MyJnInfoUIData() {}
	
	public MyJnInfoUIData(MyJianniang jn, JianniangSJ sj) {
		this.jn = new MyJNResponse(jn);
		this.sj = sj;
	}
	
	public MyJNResponse getJn() {
		return jn;
	}
	public void setJn(MyJNResponse jn) {
		this.jn = jn;
	}
	public JianniangSJ getSj() {
		return sj;
	}
	public void setSj(JianniangSJ sj) {
		this.sj = sj;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getZblNum() {
		return zblNum;
	}

	public void setZblNum(Integer zblNum) {
		this.zblNum = zblNum;
	}

	public Integer getJblNum() {
		return jblNum;
	}

	public void setJblNum(Integer jblNum) {
		this.jblNum = jblNum;
	}
}
