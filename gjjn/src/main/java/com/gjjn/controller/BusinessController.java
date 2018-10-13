package com.gjjn.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gjjn.controller.data.AccountData;
import com.gjjn.controller.data.DuiwuData;
import com.gjjn.controller.data.GenericJsonResult;
import com.gjjn.dao.entity.Duiwu;
import com.gjjn.dao.entity.GameMap;
import com.gjjn.dao.entity.Jianniang;
import com.gjjn.dao.entity.Myjianniang;
import com.gjjn.dao.entity.Role;
import com.gjjn.dao.entity.Rolesj;
import com.gjjn.dao.entity.Suipian;
import com.gjjn.service.DuiwuService;
import com.gjjn.service.GameMapService;
import com.gjjn.service.JianniangService;
import com.gjjn.service.KeyanService;
import com.gjjn.service.MyjianniangService;
import com.gjjn.service.RoleService;
import com.gjjn.service.RolesjService;
import com.gjjn.service.SuipianService;

@RestController
public class BusinessController {
	@Autowired
	RoleService roleService;
	@Autowired
	JianniangService jianniangService;
	@Autowired
	MyjianniangService myjianniangService;
	@Autowired
	SuipianService suipianService;
	@Autowired
	DuiwuService duiwuService;
	@Autowired
	KeyanService keyanService;
	@Autowired
	RolesjService rolesjService;
	@Autowired
	GameMapService gameMapService;
	
	@RequestMapping(value="/createRole", method = RequestMethod.POST)
	public GenericJsonResult<Role> createRole(HttpServletRequest request, @RequestBody AccountData data) {
		GenericJsonResult<Role> result = new GenericJsonResult<>(0);
		int userId = (int) request.getSession().getAttribute("userId");
		Role role = new Role();
		role.setRolename(data.getRoleName());
		Integer jnid = null;
		if (data.getJnId().equals("jnimg1")) {
			jnid = 19;
		} else if (data.getJnId().equals("jnimg2")) {
			jnid = 101;
		} else {
			jnid = 236;
		}
		Jianniang jianniang = jianniangService.getById(jnid);
		role.setTouxiang(jianniang.getTouxiang());
		role.setLevel(1);
		role.setExp(0);
		role.setZuanshi(0);
		role.setMofang(1000);
		role.setShiyou(500);
		role.setQdts(0);
		role.setDjsx(10);
		role.setJunxianId(1);
		role.setUserId(userId);
		role.setKeyandian(0);
		role.setWuzi(10000);
		role.setOpenmapId(1);
		roleService.create(role);
		addJn(role.getId(), null, jianniang);
		addJn(role.getId(), 262, null);			//送阿芙
		addSuipian(role.getId(), 1, 0);			//创建紫布里
		addSuipian(role.getId(), 2, 0);			//创建金布里
		duiwuService.create(role.getId()); 		//创建队伍
		keyanService.create(role.getId()); 		//创建科研
		HttpSession session = request.getSession();
		session.setAttribute("roleId", role.getId());
		return result;
	}
	
	@RequestMapping(value="/getDuiwu", method = RequestMethod.GET)
	public GenericJsonResult<DuiwuData> getDuiwu(HttpServletRequest request) {
		GenericJsonResult<DuiwuData> result = new GenericJsonResult<>(0);
		int roleId = (int) request.getSession().getAttribute("roleId");
		Duiwu dw = duiwuService.getByRoleId(roleId);
		result.setData(new DuiwuData(dw, getJnListByDw(dw)));
		return result;
	}
	
	@RequestMapping(value="/getRole", method = RequestMethod.GET)
	public GenericJsonResult<Role> getRole(HttpServletRequest request) {
		GenericJsonResult<Role> result = new GenericJsonResult<>(0);
		int roleId = (int) request.getSession().getAttribute("roleId");
		result.setData(roleService.getByPrimaryKey(roleId));
		return result;
	}
	
	@RequestMapping(value="/getRolesjs", method = RequestMethod.GET)
	public GenericJsonResult<List<Rolesj>> getRolesj() {
		GenericJsonResult<List<Rolesj>> result = new GenericJsonResult<>(0);
		result.setData(rolesjService.getAll());
		return result;
	}
	
	@RequestMapping(value="/getMaps", method = RequestMethod.GET)
	public GenericJsonResult<List<GameMap>> getMaps() {
		GenericJsonResult<List<GameMap>> result = new GenericJsonResult<>(0);
		result.setData(gameMapService.getAll());
		return result;
	}
	
	@RequestMapping(value="/getMyjns", method = RequestMethod.GET)
	public GenericJsonResult<List<Myjianniang>> getMyjns(HttpServletRequest request) {
		GenericJsonResult<List<Myjianniang>> result = new GenericJsonResult<>(0);
		int roleId = (int) request.getSession().getAttribute("roleId");
		result.setData(myjianniangService.getRoleJns(roleId));
		return result;
	}
	
	@RequestMapping(value="/getSps", method = RequestMethod.GET)
	public GenericJsonResult<List<Suipian>> getSps(HttpServletRequest request) {
		GenericJsonResult<List<Suipian>> result = new GenericJsonResult<>(0);
		int roleId = (int) request.getSession().getAttribute("roleId");
		result.setData(suipianService.getRoleSps(roleId));
		return result;
	}
	
	private List<Integer> getJnIdListByDw(Duiwu dw) {
		List<Integer> jnIds = new ArrayList<>();
		jnIds.add(dw.getOneId());
		jnIds.add(dw.getTwoId());
		jnIds.add(dw.getThreeId());
		jnIds.add(dw.getFourId());
		jnIds.add(dw.getFiveId());
		jnIds.add(dw.getSixId());
		return jnIds;
	}
	
	private List<Myjianniang> getJnListByDw(Duiwu dw){
		List<Integer> jnIds = getJnIdListByDw(dw);
		if (jnIds != null && jnIds.size() > 0) {
			return myjianniangService.getByIdList(jnIds);
		}
		return null;
	}
	
	private void addJn(int roleId, Integer jnId, Jianniang jn) {
		if (jn == null) {
			jn = jianniangService.getById(jnId);
		}
		Myjianniang myjianniang = new Myjianniang();
		myjianniang.setMygongji(jn.getGongji());
		myjianniang.setMyfangyu(jn.getFangyu());
		myjianniang.setMyxueliang(jn.getXueliang());
		myjianniang.setMysudu(jn.getSudu());
		myjianniang.setMybaoji(jn.getBaoji());
		myjianniang.setMyduobi(jn.getDuobi());
		myjianniang.setMystar(jn.getStar());
		myjianniang.setLevel(1);
		myjianniang.setIswar(0);
		myjianniang.setZdl(100);
		myjianniang.setJingyan(0);
		myjianniang.setJianniangId(jn.getId());
		myjianniang.setRoleId(roleId);
		myjianniangService.create(myjianniang);
	}
	
	private void addSuipian(int roleId, int jnId, int num) {
		Suipian suipian = new Suipian(roleId, jnId, num);
		suipianService.insertOrUpdate(suipian);
	}
}
