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
import com.mochen.model.PhbInfo;
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

	public void roleAddJy(Role role, Integer jy, Integer wz, Integer count, Boolean flag) {
		Integer exp = role.getExp() + jy;
		Integer level = role.getLevel();
		if (role.getLevel() < role.getDjsx()) {
			RoleSJ roleSj = roleSJMapper.selectByPrimaryKey(role.getLevel());
			if (exp >= roleSj.getNeedjy()) {
				exp -= roleSj.getNeedjy();
				level++;
			}
		}
		roleMapper.mapBossUpdate(role.getId(), level, exp, count, wz, flag);
	}
	
	public void jianzaoUpdate(Integer id, Integer wuzi, Integer mofang) {
		roleMapper.jianzaoUpdate(id, wuzi, mofang);
	}
	
	public List<PhbInfo> djPhb(){
		return roleMapper.djPhb();
	}
	
	public List<PhbInfo> zdlPhb(){
		return roleMapper.zdlPhb();
	}
	
	public List<PhbInfo> tjPhb(){
		return roleMapper.tjPhb();
	}
	
	public List<PhbInfo> jnPhb(){
		return roleMapper.jnPhb();
	}
	
	public void updateRoleByChangeDetail(Integer id, Integer zuanshi, Integer shiyou, Integer mofang, Integer wuzi, Integer keyandian) {
		roleMapper.updateRoleByChangeDetail(id, zuanshi, shiyou, mofang, wuzi, keyandian);
	}
}
