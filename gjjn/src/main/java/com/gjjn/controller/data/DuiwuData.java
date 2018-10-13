package com.gjjn.controller.data;

import java.util.List;

import com.gjjn.dao.entity.Duiwu;
import com.gjjn.dao.entity.Myjianniang;

public class DuiwuData {
	private Duiwu duiwu;
	private List<Myjianniang> myjns;

	public DuiwuData() {

	}

	public DuiwuData(Duiwu dw, List<Myjianniang> jns) {
		this.duiwu = dw;
		this.myjns = jns;
	}

	public Duiwu getDuiwu() {
		return duiwu;
	}

	public void setDuiwu(Duiwu duiwu) {
		this.duiwu = duiwu;
	}

	public List<Myjianniang> getMyjns() {
		return myjns;
	}

	public void setMyjns(List<Myjianniang> myjns) {
		this.myjns = myjns;
	}

}
