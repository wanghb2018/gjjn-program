package com.mochen.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.mochen.model.GenericJsonResult;
import com.mochen.model.Role;
import com.mochen.model.User;
import com.mochen.service.AccountService;
import com.mochen.service.RoleService;
import com.mochen.utils.Constant;
import com.mochen.utils.Hasher;

@RestController
public class AccountController {
	@Autowired
	AccountService accountService;
	@Autowired
	RoleService roleService;

	@PostMapping("/login")
	public GenericJsonResult<Role> login(String username, String password, HttpSession session) {
		GenericJsonResult<Role> result = new GenericJsonResult<>();
		User user = accountService.getByUserName(username);
		if (user == null) {
			result.setHr(Constant.FAILED);
			return result;
		}
		if (Hasher.instance().checkPassword(password, user.getPassword()) == false) {
			result.setHr(Constant.FAILED);
			return result;
		}
		session.setAttribute(Constant.SESSION_USER_ID, user.getId());
		Role role = accountService.login(user);
		result.setData(role);
		return result;
	}

	@GetMapping("/login")
	public GenericJsonResult<Role> loginByCookie(
			@SessionAttribute(value = Constant.SESSION_USER_ID, required = false) String sessionUserId) {
		GenericJsonResult<Role> result = new GenericJsonResult<>();
		if (sessionUserId == null) {
			result.setHr(Constant.FAILED);
			return result;
		}
		User user = accountService.getById(Integer.valueOf(sessionUserId));
		if (user == null) {
			result.setHr(Constant.FAILED);
			return result;
		}
		Role role = accountService.login(user);
		result.setData(role);
		return result;
	}
	
	@GetMapping("/logout")
	public Integer logout(HttpSession session) {
		session.removeAttribute(Constant.SESSION_USER_ID);
		return Constant.SUCCESS;
	}
	
	@PostMapping("register")
	public Integer register(String username, String password, String email, HttpSession session){
		User user = accountService.getByUserName(username);
		if (user != null) {
			return Constant.FAILED;
		}
		user = new User();
		Date now = new Date();
		user.setDateJoined(now);
		user.setEmail(email);
		user.setIsActive(true);
		user.setIsStaff(false);
		user.setLastLogin(now);
		user.setUsername(username);
		user.setPassword(Hasher.instance().encode(password));
		accountService.createUser(user);
		session.setAttribute(Constant.SESSION_USER_ID, user.getId());
		return Constant.SUCCESS;
	}
}
