package com.yice.edu.cn.jy.source21.util;

import com.google.gson.reflect.TypeToken;
import com.yice.edu.cn.jy.source21.constant.APIConstant;
import com.yice.edu.cn.common.pojo.jy.source21.model.result.APIResult;
import com.yice.edu.cn.jy.source21.service.APIRequestParams;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIRequest {

	private static APIRequest _instance = new APIRequest();

	public static APIRequest getInstance() {
		return _instance;
	}

	private APIRequest() {
	}

	public <T> List<T> requestList(String url, Class<T> clazz, Object... params) {
		if (params.length % 2 != 0) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < params.length; i++) {
			map.put(String.valueOf(params[i]), params[++i]);
		}
		return requestList(url, clazz, map);
	}

	public <T> List<T> requestList(String url, Class<T> clazz, APIRequestParams params) {
		return requestList(url, clazz, params.parseMap());
	}

	public <T> List<T> requestList(String url, Class<T> clazz, Map<String, Object> params) {
		APIResult<T> result = request(url, clazz, params);
		if (result.getData() == null) {
			System.out.println(result);
		}
		return result != null ? result.getData() : Collections.emptyList();
	}

	public APIResult<?> request(String url, Map<String, Object> params) {
		return request(url, Object.class, params);
	}

	public <T> APIResult<T> request(String url, Class<T> clazz, Map<String, Object> params) {
		setSignParams(params);
		String urlNameString;
		try {
			urlNameString = url + "?" + getGetParams(params);
			Log.debug(urlNameString);
			// TypeToken.getParameterized(List.class, clazz)
			Type typeOfT = TypeToken.getParameterized(APIResult.class, TypeToken.get(clazz).getType()).getType();
			return JSONUtil.loadBeanByJSONUrl(urlNameString, typeOfT);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> APIResult<T> request(String url, Class<T> clazz, APIRequestParams params) {
		return request(url, clazz, params.parseMap());
	}

	/**
	 * 设置签名参数
	 * 
	 * @param params
	 */
	private void setSignParams(Map<String, Object> params) {
		params.put(APIConstant.PARAM_ACCESS_KEY, APIConstant.ACCESS_KEY);
		params.put(APIConstant.PARAM_SALT, Math.random());
		params.put(APIConstant.PARAM_TIMESTAMP, System.currentTimeMillis() / 1000);
		params.put(APIConstant.PARAM_SIGN, SignatureHelper.generator(params, APIConstant.ACCESS_SECRET));
//		params.put("XDEBUG_SESSION_START", "PHPSTORM");// xdebug
	}

	/**
	 * 拼装Get参数
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getGetParams(Map<String, Object> params) throws UnsupportedEncodingException {
		StringBuffer sbuffer = new StringBuffer();
		Object value;
		for (String key : params.keySet()) {
			value = params.get(key);
			if (value != null) {
				sbuffer.append(key).append("=").append(URLEncoder.encode(String.valueOf(value), "UTF-8")).append("&");
			}
		}
		return sbuffer.toString();
	}
}
