package com.mochen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mochen.dao.JianniangMapper;
import com.mochen.dao.MyJianniangMapper;
import com.mochen.model.JianniangWithBLOBs;
import com.mochen.model.MyJianniang;
import com.mochen.utils.Constant;

@Service
public class JianniangService {
	@Autowired
	JianniangMapper jianniangMapper;
	@Autowired
	MyJianniangMapper myJianniangMapper;

	@Cacheable(value = Constant.CACHE_YEAR, key = "'jianniang_'+#id")
	public JianniangWithBLOBs getById(Integer id) {
		return jianniangMapper.selectByPrimaryKey(id);
	}
	
	public void addMyJN(Integer roleId, JianniangWithBLOBs jn) {
		MyJianniang myJN = new MyJianniang();
	}
}
