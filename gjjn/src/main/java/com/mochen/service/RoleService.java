package com.mochen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mochen.dao.RoleSJMapper;
import com.mochen.model.RoleSJ;
import com.mochen.utils.Constant;

@Service
public class RoleService {
	@Autowired
	RoleSJMapper roleSJMapper;
	
	@Cacheable(value=Constant.CACHE_YEAR, key="'rolesj_'+#id")
	public RoleSJ getRoleSJById(Integer id) {
		return roleSJMapper.selectByPrimaryKey(id);
	}
	
	@Cacheable(value=Constant.CACHE_YEAR, key=Constant.CACHE_ALL_ROLESJ)
	public List<RoleSJ> getAllRoleSJ(){
		return roleSJMapper.getAll();
	}
}
