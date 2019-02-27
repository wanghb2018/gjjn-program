package com.mochen.service.data;

import java.util.List;

import com.mochen.model.JianniangWithBLOBs;
import com.mochen.model.MyJianniang;

public class DuiwuData {
	private List<MyJianniang> myJNs;
	private List<JianniangWithBLOBs> jns;
	private Integer zdl;

	public DuiwuData() {
	}

	public DuiwuData(List<MyJianniang> myJNs, List<JianniangWithBLOBs> jns) {
		this.myJNs = myJNs;
		this.jns = jns;
		this.zdl = myJNs.stream().map(MyJianniang::getZdl).reduce(0, Integer::sum);
	}

	public List<MyJianniang> getMyJNs() {
		return myJNs;
	}

	public void setMyJNs(List<MyJianniang> myJNs) {
		this.myJNs = myJNs;
	}

	public Integer getZdl() {
		return zdl;
	}

	public void setZdl(Integer zdl) {
		this.zdl = zdl;
	}

	public List<JianniangWithBLOBs> getJns() {
		return jns;
	}

	public void setJns(List<JianniangWithBLOBs> jns) {
		this.jns = jns;
	}
}
