package com.yice.edu.cn.common.exception;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 自定义业务异常类
 * @author dengfengfeng
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5920748298934569463L;
	
	private HttpStatus status;
	
	private String msg;
	
	private String location;

	public ServiceException(@NotNull String msg) {
		this.msg = msg;
	}
	
	
	
	
	
}
