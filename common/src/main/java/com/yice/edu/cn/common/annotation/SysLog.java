package com.yice.edu.cn.common.annotation;

import java.lang.annotation.*;

/**
 * @author zzx
 * @date 2019/26/25
 * 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	/**
	 * 描述
	 *
	 * @return {String}
	 */
	String value();
}
