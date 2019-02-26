package com.mochen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mochen.model.GameMap;
import com.mochen.model.RoleSJ;
import com.mochen.service.GameMapService;
import com.mochen.service.RoleService;

@RestController
public class BusinessController {
	@Autowired
	RoleService roleService;
	@Autowired
	GameMapService gameMapService;
	
	@GetMapping("/allRoleSJ")
	public List<RoleSJ> getAllRoleSj(){
		return roleService.getAllRoleSJ();
	}
	
	@GetMapping("/allGameMap")
	public List<GameMap> getAllGameMap(){
		return gameMapService.getAllGameMap();
	}
	
}
