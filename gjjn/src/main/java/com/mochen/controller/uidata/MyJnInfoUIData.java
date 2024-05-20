package com.mochen.controller.uidata;

import com.mochen.model.JianniangSJ;
import com.mochen.model.MyJianniang;

public class MyJnInfoUIData {
	private MyJNResponse jn;
	private JianniangSJ sj;
	
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
	
}
