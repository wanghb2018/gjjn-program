package com.gjjn.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gjjn.controller.data.AccountData;
import com.gjjn.controller.data.GenericJsonResult;
import com.gjjn.dao.entity.Role;
import com.gjjn.dao.entity.User;
import com.gjjn.service.RoleService;
import com.gjjn.service.UserService;

@RestController
public class AccountController {
	private static final Integer COOKIE_EXPIRED_TIME = 31_536_000;

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public GenericJsonResult<String> register(@RequestBody AccountData request) {
		GenericJsonResult<String> result = new GenericJsonResult<String>(0);
		if (userService.getByUserName(request.getUsername()) != null) {
			result.setHr(-1);
			return result;
		}
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		user.setEmail(request.getEmail());
		userService.createUser(user);
		return result;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public GenericJsonResult<Role> login(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AccountData data) {
		GenericJsonResult<Role> result = new GenericJsonResult<Role>(0);
		User user = userService.getByUserName(data.getUsername());
		if (user == null || !user.getPassword().equals(data.getPassword())) {
			result.setHr(-1);
			return result;
		}
		Role role = roleService.getByUserId(user.getId());
		HttpSession session = request.getSession();
		session.setAttribute("userId", user.getId());
		if (role == null) {
			result.setHr(1);
		} else {
			session.setAttribute("roleId", role.getId());
			result.setData(role);
			result.setHr(0);
		}
		Cookie cookie = new Cookie("JSESSIONID", session.getId());
		cookie.setPath("/");
		cookie.setMaxAge(COOKIE_EXPIRED_TIME);
		response.addCookie(cookie);
		return result;
	}

	@RequestMapping(value = "/autoLogin", method = RequestMethod.GET)
	public GenericJsonResult<Role> autoLogin(HttpServletRequest request) {
		GenericJsonResult<Role> result = new GenericJsonResult<>(0);
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			result.setHr(-1);
			return result;
		}
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("JSESSIONID")) {
				if (!cookie.getValue().equals(sessionId)) {
					result.setHr(-1);
					return result;
				}
				break;
			}
		}
		Integer roleId = null;
		try {
			roleId = (int) session.getAttribute("roleId");
		} catch (Exception e) {
			result.setHr(1);
			return result;
		}
		Role role = roleService.getByPrimaryKey(roleId);
		if (role != null) {
			result.setData(role);
			return result;
		} else {
			result.setHr(1);
		}
		return result;
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public GenericJsonResult<Role> resetPassword(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AccountData data) {
		GenericJsonResult<Role> result = new GenericJsonResult<>(0);
		User user = userService.getByUserName(data.getUsername());
		if (user == null) {
			result.setHr(-1);
			return result;
		}
		if (user.getEmail().equals(data.getEmail())) {
			user.setPassword(data.getPassword());
			userService.update(user);
		} else {
			result.setHr(-2);
		}
		return result;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public GenericJsonResult<Role> logout(HttpServletRequest request, HttpServletResponse response) {
		GenericJsonResult<Role> result = new GenericJsonResult<>(0);
		Cookie cookie = new Cookie("JSESSIONID", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		request.getSession().removeAttribute("roleId");
		request.getSession().removeAttribute("userId");
		return result;
	}
}
