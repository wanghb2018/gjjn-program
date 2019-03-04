package com.mochen.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mochen.dao.KeyanMapper;
import com.mochen.dao.RoleMapper;
import com.mochen.dao.RoleSJMapper;
import com.mochen.dao.UserMapper;
import com.mochen.model.Keyan;
import com.mochen.model.Role;
import com.mochen.model.RoleSJ;
import com.mochen.model.User;

@Service
public class AccountService {
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	KeyanMapper keyanMapper;
	@Autowired
	RoleSJMapper roleSJMapper;
	
	public User getById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}
	
	public Role login(User user) {
		user.setLastLogin(new Date());
		userMapper.updateByPrimaryKey(user);
		return roleMapper.getByUserId(user.getId());
	}
	
	public Role getByUserId(Integer userId) {
		return roleMapper.getByUserId(userId);
	}
	
	public User getByUserName(String userName) {
		return userMapper.getByUserName(userName);
	}
	
	public void createUser(User user) {
		userMapper.insert(user);
	}
	
	public void updateUser(User user) {
		userMapper.updateByPrimaryKey(user);
	}
	
	public void createRole(Role role) {
		roleMapper.insertSelective(role);
	}
	
	public RoleSJ getRoleSJById(Integer id) {
		return roleSJMapper.selectByPrimaryKey(id);
	}

	public List<RoleSJ> getAllRoleSJ() {
		return roleSJMapper.getAll();
	}

	public void createKeyan(Keyan keyan) {
		keyanMapper.insert(keyan);
	}
	
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
	}
}
