package com.mochen.controller.uidata;

import com.mochen.model.JianniangSJ;
import com.mochen.model.MyJianniang;

public class MyJnInfoUIData {
	private MyJianniang jn;
	private JianniangSJ sj;
	
	public MyJnInfoUIData() {}
	
	public MyJnInfoUIData(MyJianniang jn, JianniangSJ sj) {
		this.jn = jn;
		this.sj = sj;
	}
	
	public MyJianniang getJn() {
		return jn;
	}
	public void setJn(MyJianniang jn) {
		this.jn = jn;
	}
	public JianniangSJ getSj() {
		return sj;
	}
	public void setSj(JianniangSJ sj) {
		this.sj = sj;
	}
	
}
