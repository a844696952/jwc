package com.yice.edu.cn.recording.commandLineRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.yice.edu.cn.common.CloudCourseCommon;
import com.yice.edu.cn.recording.service.CloudCoursedResourceService;

@Component
@Order(value = 1)
public class UploadCloudCourseVideoRunner implements CommandLineRunner {
	
	@Autowired
	private CloudCoursedResourceService cloudCoursedResourceService;
	
	@Override
	public void run(String... arg0) throws Exception {
		//继续上传未成功的云课堂录屏文件
		cloudCoursedResourceService.recordingUpload(CloudCourseCommon.recordFileRootDir);
	}
}
