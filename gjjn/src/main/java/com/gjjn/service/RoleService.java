package com.gjjn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gjjn.dao.RoleMapper;
import com.gjjn.dao.entity.Role;

@Service
public class RoleService {
	@Autowired
	RoleMapper mapper;
	
	public Role getByPrimaryKey(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	public Role getByUserId (Integer id) {
		return mapper.selectByUserId(id);
	}
	
	public void create(Role role) {
		mapper.insertSelective(role);
	}
}
