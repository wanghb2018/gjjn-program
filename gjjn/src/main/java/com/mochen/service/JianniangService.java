package com.mochen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mochen.dao.JianniangMapper;
import com.mochen.dao.MyJianniangMapper;
import com.mochen.model.Jianniang;
import com.mochen.model.MyJianniang;
import com.mochen.utils.Constant;

@Service
public class JianniangService {
	@Autowired
	JianniangMapper jianniangMapper;
	@Autowired
	MyJianniangMapper myJianniangMapper;

	public Jianniang getById(Integer id) {
		return jianniangMapper.selectByPrimaryKey(id);
	}

	public MyJianniang addMyJN(Integer roleId, Jianniang jn, Integer isWar) {
		MyJianniang myJN = new MyJianniang(roleId, jn, isWar);
		myJN.calJNZdl(0);
		myJianniangMapper.insert(myJN);
		return myJN;
	}

	public void addMyJN(Integer roleId, Jianniang jn) {
		addMyJN(roleId, jn, 0);
	}
	
	@Cacheable(value=Constant.CACHE_YEAR, key="'userJns_'+#p0")
	public List<MyJianniang> getUserJns(Integer roleId){
		return myJianniangMapper.getUserJns(roleId);
	}

}
