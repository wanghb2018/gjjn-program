package com.mochen.service.data;

import java.util.List;

import com.mochen.model.MyJianniang;

public class DuiwuData {
	private List<MyJianniang> myJns;
	private Integer zdl;

	public DuiwuData() {
	}

	public DuiwuData(List<MyJianniang> myJNs) {
		this.myJns = myJNs;
		this.zdl = myJNs.stream().map(MyJianniang::getZdl).reduce(0, Integer::sum);
	}

	public List<MyJianniang> getMyJns() {
		return myJns;
	}

	public void setMyJns(List<MyJianniang> myJns) {
		this.myJns = myJns;
	}

	public Integer getZdl() {
		return zdl;
	}

	public void setZdl(Integer zdl) {
		this.zdl = zdl;
	}
}
