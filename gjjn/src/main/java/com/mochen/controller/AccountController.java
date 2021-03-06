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
import com.mochen.utils.Constant;
import com.mochen.utils.Hasher;

@RestController
public class AccountController {
	@Autowired
	AccountService accountService;

	@PostMapping("/login")
	public GenericJsonResult<Role> login(String userName, String password, HttpSession session) {
		GenericJsonResult<Role> result = new GenericJsonResult<>();
		User user = accountService.getByUserName(userName);
		if (user == null) {
			result.setHr(Constant.FAILED);
			return result;
		}
		if (!Hasher.instance().checkPassword(password, user.getPassword())) {
			result.setHr(Constant.FAILED);
			return result;
		}
		session.setAttribute(Constant.SESSION_USER_ID, user.getId());
		Role role = accountService.login(user);
		result.setData(role);
		if (role != null) {
			session.setAttribute(Constant.SESSION_ROLE_ID, role.getId());
		}
		return result;
	}

	@GetMapping("/login")
	public GenericJsonResult<Role> loginByCookie(
			@SessionAttribute(value = Constant.SESSION_USER_ID, required = false) Integer userId, HttpSession session) {
		GenericJsonResult<Role> result = new GenericJsonResult<>();
		if (userId == null) {
			result.setHr(Constant.FAILED);
			return result;
		}
		User user = accountService.getById(userId);
		if (user == null) {
			result.setHr(Constant.FAILED);
			return result;
		}
		Role role = accountService.login(user);
		result.setData(role);
		if (role != null) {
			session.setAttribute(Constant.SESSION_ROLE_ID, role.getId());
		}
		return result;
	}
	
	@GetMapping("/logout")
	public Integer logout(HttpSession session) {
		session.removeAttribute(Constant.SESSION_USER_ID);
		session.removeAttribute(Constant.SESSION_ROLE_ID);
		return Constant.SUCCESS;
	}
	
	@PostMapping("/register")
	public Integer register(String userName, String password, String email, HttpSession session){
		User user = accountService.getByUserName(userName);
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
		user.setUsername(userName);
		user.setPassword(Hasher.instance().encode(password));
		accountService.createUser(user);
		session.setAttribute(Constant.SESSION_USER_ID, user.getId());
		return Constant.SUCCESS;
	}
	
	@PostMapping("/changePassword")
	public Integer changePassword(String userName, String password, String email) {
		User user = accountService.getByUserName(userName);
		if (user == null) {
			return Constant.OTHER;
		}
		if (!user.getEmail().equals(email)){
			return Constant.FAILED;
		}
		user.setPassword(Hasher.instance().encode(password));
		accountService.updateUser(user);
		return Constant.SUCCESS;
	}
}
