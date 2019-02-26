package com.mochen.utils;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor()).addPathPatterns("/*");
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns(Arrays.asList("/login", "/register"));
	}
}
