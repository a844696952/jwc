package com.yice.edu.cn.cc.utils;

import java.lang.reflect.Field;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.PublishStreamParamVo;

import cn.hutool.core.date.DateUtil;

public class CCUtils {

	private static final Logger logger = LoggerFactory.getLogger(CCUtils.class);
	

	/**
	 * 获取参数
	 * 
	 * @param param
	 * @return
	 */
	public static PublishStreamParamVo getParams(String param) {
		PublishStreamParamVo publishStreamParamVo = new PublishStreamParamVo();
		if (param == null) {
			return publishStreamParamVo;
		}
		String[] streamParamArr = param.replaceFirst("\\?", "").split("&");
		try {
			for (String streamParam : streamParamArr) {
				Class<PublishStreamParamVo> cls = PublishStreamParamVo.class;
				Field field = cls.getDeclaredField(streamParam.substring(0, streamParam.indexOf("=")));
				if (field == null) {
					continue;
				}
				if (streamParam.indexOf("=") < streamParam.length()) {
					String value = streamParam.substring(streamParam.indexOf("=") + 1, streamParam.length());
					String fieldType = field.getType().getSimpleName();
					field.setAccessible(true);
					if ("String".equals(fieldType)) {
						field.set(publishStreamParamVo, value);
					} else if ("Date".equals(fieldType)) {
						Date tempDate = DateUtil.parseDateTime(value);
						field.set(publishStreamParamVo, tempDate);
					} else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
						Integer tempInteger = Integer.parseInt(value);
						field.set(publishStreamParamVo, tempInteger);
					} else if ("Long".equalsIgnoreCase(fieldType)) {
						Long tempLong = Long.parseLong(value);
						field.set(publishStreamParamVo, tempLong);
					} else if ("Double".equalsIgnoreCase(fieldType)) {
						Double tempDouble = Double.parseDouble(value);
						field.set(publishStreamParamVo, tempDouble);
					} else if ("Boolean".equalsIgnoreCase(fieldType)) {
						Boolean tempBoolean = Boolean.parseBoolean(value);
						field.set(publishStreamParamVo, tempBoolean);
					} else {
						logger.error("not supper type" + fieldType);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return publishStreamParamVo;
		}
		return publishStreamParamVo;
	}

}
