package com.mochen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.mochen.model.GameMap;
import com.mochen.model.JianniangWithBLOBs;
import com.mochen.model.Role;
import com.mochen.model.RoleSJ;
import com.mochen.service.AccountService;
import com.mochen.service.GameMapService;
import com.mochen.service.JianniangService;
import com.mochen.service.RoleService;
import com.mochen.utils.Constant;
import com.mochen.utils.Constant.InitialJN;

@RestController
public class BusinessController {
	@Autowired
	RoleService roleService;
	@Autowired
	GameMapService gameMapService;
	@Autowired
	AccountService accountService;
	@Autowired
	JianniangService jianniangService;
	
	@GetMapping("/allRoleSJ")
	public List<RoleSJ> getAllRoleSj(){
		return roleService.getAllRoleSJ();
	}
	
	@GetMapping("/allGameMap")
	public List<GameMap> getAllGameMap(){
		return gameMapService.getAllGameMap();
	}
	
	@PostMapping("/createRole")
	public Role createRole(@SessionAttribute(Constant.SESSION_USER_ID)String userId,String jnImgId, String roleName) {
		JianniangWithBLOBs jn = jianniangService.getById(InitialJN.getIdByImg(jnImgId));
		Role role = new Role();
		role.setUserId(Integer.parseInt(userId));
		role.setRolename(roleName);
		role.setTouxiang(jn.getTouxiang());
		accountService.createRole(role);
		return role;
	}
	
}
