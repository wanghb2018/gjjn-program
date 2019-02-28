package com.mochen.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.mochen.model.Duiwu;
import com.mochen.model.GameMap;
import com.mochen.model.Jianniang;
import com.mochen.model.Keyan;
import com.mochen.model.MyJianniang;
import com.mochen.model.Role;
import com.mochen.model.RoleSJ;
import com.mochen.service.AccountService;
import com.mochen.service.DuiwuService;
import com.mochen.service.GameMapService;
import com.mochen.service.JianniangService;
import com.mochen.service.data.DuiwuData;
import com.mochen.utils.Constant;
import com.mochen.utils.Constant.InitialJN;

@RestController
public class BusinessController {
	@Autowired
	GameMapService gameMapService;
	@Autowired
	AccountService accountService;
	@Autowired
	JianniangService jianniangService;
	@Autowired
	DuiwuService duiwuService;

	@GetMapping("/allRoleSJ")
	public List<RoleSJ> getAllRoleSj() {
		return accountService.getAllRoleSJ();
	}

	@GetMapping("/allGameMap")
	public List<GameMap> getAllGameMap() {
		return gameMapService.getAllGameMap();
	}

	@PostMapping("/createRole")
	public Role createRole(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId, String jnImgId,
			String roleName, HttpSession session) {
		Jianniang jn = jianniangService.getById(InitialJN.getIdByImg(jnImgId));
		Role role = new Role();
		role.setUserId(userId);
		role.setRolename(roleName);
		role.setTouxiang(jn.getTouxiang());
		accountService.createRole(role);
		MyJianniang myjn = jianniangService.addMyJN(role.getId(), jn, 1);
		Jianniang afu = jianniangService.getById(262); // 登录送阿芙
		jianniangService.addMyJN(role.getId(), afu);
		Duiwu duiwu = new Duiwu(role.getId(), myjn);
		duiwuService.create(duiwu);
		Keyan keyan = new Keyan(role.getId());
		accountService.createKeyan(keyan);
		session.setAttribute(Constant.SESSION_ROLE_ID, role.getId());
		return role;
	}
	
	@GetMapping("/getRoleInfo")
	public Role getRoleInfo(@SessionAttribute(Constant.SESSION_USER_ID)Integer userId) {
		return accountService.getByUserId(userId);
	}

	@GetMapping("/getDuiwuInfo")
	public DuiwuData getDuiwuInfo(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId) {
		Role role = accountService.getByUserId(userId);
		return duiwuService.reCalJNZdl(role);
	}
	
	@GetMapping("/showJnList")
	public List<MyJianniang> showJnList(@SessionAttribute(Constant.SESSION_ROLE_ID)Integer roleId){
		return jianniangService.getUserJns(roleId);
	}
}
