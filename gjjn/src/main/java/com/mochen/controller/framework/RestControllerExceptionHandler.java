package com.mochen.controller.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public String handle(HttpServletRequest req, HandlerMethod method, Exception ex) {
        logger.error(String.format("unknown error: %s -> %s ！", req.getRequestURI(), method.toString()), ex);
        return "{\"hr\":-1, \"message\":\"未知错误\"}";
    }

}
