package com.yice.edu.cn.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EccJpush {

	/**
	 * 类型
	 *
	 * @return {int}
	 */
	int type();

	/**
	 * 描述
	 * @return
	 */
	String content();

}
