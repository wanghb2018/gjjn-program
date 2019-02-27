package com.mochen.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mochen.dao.RoleMapper;
import com.mochen.dao.UserMapper;
import com.mochen.model.Role;
import com.mochen.model.User;

@Service
public class AccountService {
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	UserMapper userMapper;
	
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
}
