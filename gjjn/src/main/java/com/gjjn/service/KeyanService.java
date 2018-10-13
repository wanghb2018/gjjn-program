package com.gjjn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gjjn.dao.KeyanMapper;
import com.gjjn.dao.entity.Keyan;

@Service
public class KeyanService {
	@Autowired
	KeyanMapper mapper;
	
	public void create(int roleId) {
		Keyan keyan = new Keyan();
		keyan.setRoleId(roleId);
		mapper.insertSelective(keyan);
	}
}
