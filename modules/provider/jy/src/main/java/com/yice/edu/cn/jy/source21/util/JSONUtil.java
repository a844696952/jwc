package com.yice.edu.cn.jy.source21.util;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON转换辅助类
 * 
 * @author zhoushubing
 *
 */
public class JSONUtil {
	public static JsonElement loadJsonObjectByStream(InputStream inputStream, String encoding) {
		try {
			StringBuffer jsonData = new StringBuffer();
			InputStreamReader reader = new InputStreamReader(inputStream, encoding);
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				jsonData.append(line);
			}
			br.close();
			reader.close();
			Log.debug(jsonData.toString());
			return new JsonParser().parse(jsonData.toString());
		} catch (Exception e) {
			Log.error(e.getMessage());
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	public static <T> T parseJsonToBean(String jsonData, Class<T> clazz) {
		JsonElement jsonObject = new JsonParser().parse(jsonData);
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(jsonObject, clazz);
	}

	public static JsonElement loadJsonObjectByUrl(String url, String encoding) {
		try {
			return loadJsonObjectByStream(new URL(url).openStream(), encoding);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T loadBeanByJSONUrl(String jsonUrl, Class<T> clazz, String encoding) {
		JsonElement jsonObject = loadJsonObjectByUrl(jsonUrl, encoding);
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(jsonObject, clazz);
	}

	public static <T> T loadBeanByJSONUrl(InputStream inStream, Class<T> clazz, String encoding) {
		JsonElement jsonObject = loadJsonObjectByStream(inStream, encoding);
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(jsonObject, clazz);
	}

	public static <T> T loadBeanByJSONUrl(String jsonUrl, Class<T> clazz) {
		JsonElement jsonObject = loadJsonObjectByUrl(jsonUrl, "UTF-8");
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(jsonObject, clazz);
	}

	public static <T> T loadBeanByJSONUrl(String jsonUrl, Type type) {
		JsonElement jsonObject = loadJsonObjectByUrl(jsonUrl, "UTF-8");
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(jsonObject, type);
	}

	public static <T> List<T> loadListBeanByJSONUrl(String jsonUrl, Class<T> clazz) {
		JsonElement jsonObject = loadJsonObjectByUrl(jsonUrl, "UTF-8");
		JsonArray jsonArray = jsonObject.getAsJsonArray();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < jsonArray.size(); i++) {
			list.add(gson.fromJson(jsonArray.get(i), clazz));
		}
		return list;
	}

}
