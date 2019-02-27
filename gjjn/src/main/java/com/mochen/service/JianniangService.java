package com.mochen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mochen.dao.JianniangMapper;
import com.mochen.dao.MyJianniangMapper;
import com.mochen.model.JianniangWithBLOBs;
import com.mochen.model.MyJianniang;

@Service
public class JianniangService {
	@Autowired
	JianniangMapper jianniangMapper;
	@Autowired
	MyJianniangMapper myJianniangMapper;

	public JianniangWithBLOBs getById(Integer id) {
		return jianniangMapper.selectByPrimaryKey(id);
	}

	public MyJianniang addMyJN(Integer roleId, JianniangWithBLOBs jn, Integer isWar) {
		MyJianniang myJN = new MyJianniang(roleId, jn, isWar);
		myJN.calJNZdl();
		myJianniangMapper.insert(myJN);
		return myJN;
	}

	public void addMyJN(Integer roleId, JianniangWithBLOBs jn) {
		addMyJN(roleId, jn, 0);
	}
}
