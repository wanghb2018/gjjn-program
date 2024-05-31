package com.mochen.service.data;

import java.util.ArrayList;
import java.util.List;

import com.mochen.model.Duiwu;
import com.mochen.model.MyJianniang;

public class DuiwuInfo {
	private Integer id;
	private Integer roleId;
	private List<MyJianniang> myJns = new ArrayList<>();
	private Integer zdl = 0;

	public DuiwuInfo() {
	}

	public DuiwuInfo(Duiwu duiwu) {
		this.id = duiwu.getId();
		this.roleId = duiwu.getRoleId();
	}

	public DuiwuInfo(Duiwu duiwu, List<MyJianniang> myJNs) {
		this.id = duiwu.getId();
		this.roleId = duiwu.getRoleId();
		this.zdl = duiwu.getTotalzdl();
		this.myJns = myJNs;
	}

	public DuiwuInfo(Duiwu duiwu, List<MyJianniang> myJNs, double jxRate) {
		this.id = duiwu.getId();
		this.roleId = duiwu.getRoleId();
		this.myJns = myJNs;
		calDwZdl(jxRate);
	}

	public void calDwZdl(double jxRate) {
		this.zdl = (int)(myJns.stream().map(MyJianniang::getZdl).reduce(0, Integer::sum) * jxRate);
	}

	public Duiwu toDuiwu() {
		Duiwu dw = new Duiwu();
		dw.setId(id);
		dw.setRoleId(roleId);
		dw.setTotalzdl(zdl);
		dw.setCount(myJns.size());
		switch (myJns.size()) {
			case 6:
				dw.setSixId(myJns.get(5).getId());
			case 5:
				dw.setFiveId(myJns.get(4).getId());
			case 4:
				dw.setFourId(myJns.get(3).getId());
			case 3:
				dw.setThreeId(myJns.get(2).getId());
			case 2:
				dw.setTwoId(myJns.get(1).getId());
			case 1:
				dw.setOneId(myJns.get(0).getId());
				break;
		}
		return dw;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
