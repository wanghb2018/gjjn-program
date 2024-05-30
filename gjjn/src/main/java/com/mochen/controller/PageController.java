package com.mochen.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mochen.model.Jianniang;
import com.mochen.model.Role;
import com.mochen.service.AccountService;
import com.mochen.service.JianniangService;
import com.mochen.utils.Constant;

@Controller
public class PageController {
	private Random random = new Random();
	@Autowired
	JianniangService jianniangService;
	@Autowired
	AccountService accountService;

	@GetMapping("/")
	public String index() {
		return "webgame";
	}

	@GetMapping("/game")
	public String game() {
		return "game";
	}
	
	@GetMapping("/jns")
	public String jns(ModelMap map) {
		map.addAttribute("jns", jianniangService.getAllJn());
		return "jns";
	}
	
	@GetMapping("/admin")
	public String adminPage(Integer pwd) {
		if (pwd == null || pwd != 1314520) {
			return "webgame";
		}
		return "admin";
	}
	
	@PostMapping(value = "/addJn")
	@ResponseBody
	public Integer addJn(Jianniang jn) {
		if (jianniangService.getById(jn.getId()) != null) {
			return Constant.FAILED;
		}
		int[] arr = random.ints(4, 0, 16).toArray();
		jn.setGongji(90 + arr[0] + jn.getPinji() * 5);
		jn.setFangyu(30 + arr[1] + jn.getPinji() * 5 );
		jn.setXueliang(100 + + arr[2] + jn.getPinji() * 5);
		jn.setSudu(15 + + arr[3] + jn.getPinji() * 5);
		int baojiBase = (jn.getPinji() > 2 ? jn.getPinji() - 2 : 0) * 10;
		if (baojiBase > 0) {
			jn.setBaoji(baojiBase - random.nextInt(6));
			jn.setDuobi(baojiBase / 2 - random.nextInt(6));
		} else {
			jn.setBaoji(0);
			jn.setDuobi(0);
		}
		jn.setStar((jn.getPinji() + 1) / 2 + 1);
		int spnum = 25 + jn.getPinji() * 25;
		if (jn.getPinji() == 6) {
			spnum = 200;
		}
		jn.setSpnum(spnum);
		jianniangService.addNewJn(jn);
		return Constant.SUCCESS;
	}
	
	@PostMapping(value = "/addData")
	@ResponseBody
	public Integer addData(Integer id, Integer zuanshi, Integer shiyou, Integer wuzi, Integer mofang, Integer keyan) {
		Role role = accountService.getRoleById(id);
		if (role == null) {
			return Constant.FAILED;
		}
		accountService.updateRoleByChangeDetail(id, zuanshi, shiyou, mofang, wuzi, keyan);
		return Constant.SUCCESS;
	}
}
