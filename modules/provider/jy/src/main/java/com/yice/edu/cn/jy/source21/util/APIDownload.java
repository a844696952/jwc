package com.yice.edu.cn.jy.source21.util;

import com.yice.edu.cn.common.pojo.jy.source21.model.document.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class APIDownload {

	private static APIDownload _instance = new APIDownload();

	public static APIDownload getInstance() {
		return _instance;
	}

	private APIDownload() {
		threadPool = Executors.newFixedThreadPool(8);
	}

	private ExecutorService threadPool;

	/**
	 * 下载资料
	 * 
	 * @param document
	 */
	public void download(Document document) {
		if (document == null || document.getDownloadUrl() == null) {
			Log.error("Downlod param error.");
			return;
		}
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				String fileName = "D:/tmp/document/" + document.getSubjectId() + "/" + document.getSubject()
						+ getSuffix(document.getDownloadUrl());
				saveUrlAs(document.getDownloadUrl(), fileName);
				Log.debug("Finish " + document.getDownloadUrl());
				document.setDownloadUrl(null);
			}
		});
	}

	/**
	 * 获取后缀
	 * 
	 * @param url
	 * @return
	 */
	private String getSuffix(String url) {
		int endIndex = url.indexOf("?");
		if (endIndex == -1) {
			endIndex = url.length();
		}
		int startIndex = url.lastIndexOf(".", endIndex);
		return url.substring(startIndex, endIndex);
	}

	/**
	 * 保存文件
	 * 
	 * @param netUrl
	 *            下载地址
	 * @param fileName
	 *            本地保存地址
	 * @return
	 */
	public boolean saveUrlAs(String netUrl, String fileName) {
		Log.debug(netUrl + " => " + fileName);
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			File file = new File(fileName);
			file.getParentFile().mkdirs();
			URL url = new URL(netUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			in = new DataInputStream(connection.getInputStream());
			out = new DataOutputStream(new FileOutputStream(file));
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
