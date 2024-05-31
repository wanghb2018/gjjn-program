package com.mochen.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mochen.dao.KeyanMapper;
import com.mochen.dao.RoleMapper;
import com.mochen.dao.UserMapper;
import com.mochen.model.Keyan;
import com.mochen.model.PhbInfo;
import com.mochen.model.Role;
import com.mochen.model.User;

@Service
public class AccountService {
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	KeyanMapper keyanMapper;

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

	public void createKeyan(Keyan keyan) {
		keyanMapper.insert(keyan);
	}
	
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKeyWithBLOBs(role);
	}
	
	public void roleAddJy(Integer roleId, Integer jy, Integer wz, Integer count, Boolean flag) {
		roleMapper.mapBossUpdate(roleId, jy, count, wz, flag);
	}
	
	public int jianzaoUpdate(Integer id, Integer wuzi, Integer mofang) {
		return roleMapper.jianzaoUpdate(id, wuzi, mofang);
	}
	
	public List<PhbInfo> djPhb() {
		return roleMapper.djPhb();
	}
	
	public List<PhbInfo> zdlPhb() {
		return roleMapper.zdlPhb();
	}
	
	public List<PhbInfo> tjPhb() {
		return roleMapper.tjPhb();
	}
	
	public List<PhbInfo> jnPhb() {
		return roleMapper.jnPhb();
	}
	
	public void updateRoleByChangeDetail(Integer id, Integer zuanshi, Integer shiyou, Integer mofang, Integer wuzi, Integer keyandian) {
		roleMapper.updateRoleByChangeDetail(id, zuanshi, shiyou, mofang, wuzi, keyandian);
	}
	
	public Keyan getKeyanByRoleId(Integer roleId) {
		return keyanMapper.getByRoleId(roleId);
	}

	public Role getRoleById(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}
	
	public int updateRoleShiyouByBoss(Integer roleId, Integer count) {
		return roleMapper.updateRoleShiyouByBoss(roleId, count);
	}

}
