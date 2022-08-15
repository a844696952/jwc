package com.yice.edu.cn.recording.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.UploadCloudCourseParam;


@FeignClient(value = "jy", contextId = "cloudCourseResourceFeign", path = "/cloudCourseResource")
public interface CloudCourseResourceFeign {
	@PostMapping("/saveUploadCloudCourseResource")
	CloudCourseResource saveUploadCloudCourseResource(UploadCloudCourseParam param);
}
