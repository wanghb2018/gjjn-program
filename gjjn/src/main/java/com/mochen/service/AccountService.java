package com.mochen.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mochen.dao.DuiwuMapper;
import com.mochen.dao.KeyanMapper;
import com.mochen.dao.RoleMapper;
import com.mochen.dao.RoleSJMapper;
import com.mochen.dao.UserMapper;
import com.mochen.model.Duiwu;
import com.mochen.model.Keyan;
import com.mochen.model.PhbInfo;
import com.mochen.model.Role;
import com.mochen.model.RoleSJ;
import com.mochen.model.User;
import com.mochen.utils.Constant;

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
	@Autowired
	DuiwuMapper duiwuMapper;

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
		int exp = role.getExp() + jy;
		boolean flag2 = false;
		int needJy = 0;
		if (role.getLevel() < role.getDjsx()) {
			RoleSJ roleSj = roleSJMapper.selectByPrimaryKey(role.getLevel());
			if (exp >= roleSj.getNeedjy()) {
				flag2 = true;
				needJy = roleSj.getNeedjy();
			}
		}
		roleMapper.mapBossUpdate(role.getId(), flag2, jy, count, wz, flag, needJy);
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
	
	public Keyan getKeyanByRoleId(Integer roleId) {
		return keyanMapper.getByRoleId(roleId);
	}
	
	public void updateKeyan(Keyan keyan) {
		keyanMapper.updateByPrimaryKey(keyan);
	}
	
	public Role getRoleById(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}
	
	public int updateRoleShiyouByBoss(Integer roleId, Integer count) {
		return roleMapper.updateRoleShiyouByBoss(roleId, count);
	}
	
	@Cacheable(value = Constant.CACHE_YEAR, key = "'duiwu_'+#roleId")
	public Duiwu getDuiwuByRoleId(Integer roleId) {
		return duiwuMapper.getByRoleId(roleId);
	}
	
	@CachePut(value = Constant.CACHE_YEAR, key = "'duiwu_'+#duiwu.roleId")
	public Duiwu updateDuiwu(Duiwu duiwu) {
		duiwuMapper.updateByPrimaryKey(duiwu);
		return duiwu;
	}
}
