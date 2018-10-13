package com.gjjn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gjjn.dao.UserMapper;
import com.gjjn.dao.entity.User;

@Service
public class UserService {
	@Autowired
	UserMapper mapper;
	
	public void createUser(User user) {
		mapper.insertSelective(user);
	}
	
	public User getByUserName(String userName) {
		return mapper.getByUserName(userName);
	}
	
	public void update(User user) {
		mapper.updateByPrimaryKeySelective(user);
	}
}
