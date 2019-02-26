package com.mochen.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class LogInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	private static final String LOG_FORMAT = "%s__%s__%s__%s";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info(String.format(LOG_FORMAT, request.getSession().getAttribute(Constant.SESSION_USER_ID),
				request.getServletPath(), request.getRemoteAddr(), request.getHeader("User-Agent")));
		return true;
	}
}
