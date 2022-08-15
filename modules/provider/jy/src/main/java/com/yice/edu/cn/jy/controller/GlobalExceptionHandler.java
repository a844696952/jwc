package com.yice.edu.cn.jy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yice.edu.cn.common.pojo.ResponseJson;

/**
 * 
* @ClassName: GlobalExceptionHandler  
* @Description: 全局异常拦截 
* @author xuchang  
* @date 2018年11月6日
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseJson illegalArgumentException(IllegalArgumentException e) {
		log.error("参数非法异常={}", e.getMessage(), e);
		return new ResponseJson(false, e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseJson exception(Exception e) {
		log.error("捕获全局异常信息 exception={}", e.getMessage(), e);
		return new ResponseJson(false, e.getMessage());
	}
	
}
