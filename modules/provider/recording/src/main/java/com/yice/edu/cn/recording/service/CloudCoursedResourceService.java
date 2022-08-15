package com.yice.edu.cn.recording.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.CloudCourseCommon;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.UploadCloudCourseParam;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.recording.executor.RecodingExecutorService;
import com.yice.edu.cn.recording.feignClient.CloudCourseResourceFeign;
import com.yice.edu.cn.recording.utils.VideoFileUtils;

@Service
public class CloudCoursedResourceService {

	private static final Logger logger = LoggerFactory.getLogger(CloudCoursedResourceService.class);

	@Autowired
	private CloudCourseResourceFeign cloudCourseResourceFeign;

	/**
	 * 录屏上传
	 * 
	 * @param path
	 * @param cloudCourseId
	 */
	public void recordingUpload(String storageDir) {
		List<File> fileList = new ArrayList<File>();
		VideoFileUtils.getVideoName(storageDir, fileList);
		if (fileList.isEmpty()) {
			logger.debug(" storageDir:" + storageDir + " is empty");
			return;
		}
		RecodingExecutorService.executorService.execute(() -> {
			fileList.forEach(file -> {
				String cloudCoursePath = file.getPath().replace(CloudCourseCommon.recordFileRootDir + "/", "");
				String cloudCourseId = cloudCoursePath.substring(0, cloudCoursePath.indexOf("/"));
				String filePath = CloudCourseCommon.videoFilePre + cloudCourseId + "/" + file.getName();
				logger.debug(" file:" + file + " is uploading" + " file-size:" + file.length());
				try {
					Thread.sleep(2000);
					String path = QiniuUtil.uploadQiniu(file.getPath(), filePath, CloudCourseCommon.localTempDir);

					UploadCloudCourseParam param = new UploadCloudCourseParam();
					param.setCloudSubCourseId(cloudCourseId);
					param.setPath(path);
					param.setStatus(2);
//					param.setFlag(flag);
					cloudCourseResourceFeign.saveUploadCloudCourseResource(param);
					file.delete();
				} catch (InterruptedException e) {
					e.printStackTrace();
					logger.error(e.getLocalizedMessage());
				}   
			});
		});

	}

	public static void main(String[] args) {
		int a = 371537578;
		System.out.println(a);
	}
}
