package com.mochen.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(Integer.MAX_VALUE);
		Cookie cookie = new Cookie("JSESSIONID", session.getId());
		cookie.setHttpOnly(true);
		cookie.setMaxAge(Integer.MAX_VALUE);
		cookie.setPath("/");
		response.addCookie(cookie);
		return true;
	}
}
