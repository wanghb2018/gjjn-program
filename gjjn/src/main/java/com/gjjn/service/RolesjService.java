package com.gjjn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gjjn.dao.RolesjMapper;
import com.gjjn.dao.entity.Rolesj;

@Service
public class RolesjService {
	@Autowired
	RolesjMapper mapper;
	
	public Rolesj getById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	public List<Rolesj> getAll(){
		return mapper.getAll();
	}
}
