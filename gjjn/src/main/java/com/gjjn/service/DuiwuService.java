package com.gjjn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gjjn.dao.DuiwuMapper;
import com.gjjn.dao.entity.Duiwu;

@Service
public class DuiwuService {
	@Autowired
	DuiwuMapper mapper;
	
	public void create(int roleId) {
		Duiwu dw = new Duiwu();
		dw.setRoleId(roleId);
		dw.setCount(0);
		dw.setTotalzdl(0);
		mapper.insertSelective(dw);
	}
	
	public Duiwu getByRoleId(int roleId) {
		return mapper.getByRoleId(roleId);
	}
}
