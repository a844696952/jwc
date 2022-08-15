package com.yice.edu.cn.recording.utils;

import java.io.File;
import java.util.List;

public class VideoFileUtils {
	/**
	 * 获取该目录和子目录下所有的.mp4文件
	 * 
	 * @param path
	 * @return
	 */
	public static List<File> getVideoName(String path, List<File> fileListNameList) {
		File file = new File(path);
		File[] fileNameArr = file.listFiles();
		for (File tempFile : fileNameArr) {
			if (!tempFile.isDirectory()) {
				if (tempFile.getName().endsWith(".mp4") || tempFile.getName().endsWith(".MP4")) {
					fileListNameList.add(tempFile);
				}
			} else {
				getVideoName(tempFile.getPath(), fileListNameList);
			}
		}
		return fileListNameList;
	}

}
