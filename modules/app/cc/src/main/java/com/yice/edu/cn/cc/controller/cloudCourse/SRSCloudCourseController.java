package com.yice.edu.cn.cc.controller.cloudCourse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.cc.interceptor.LoginInterceptor;
import com.yice.edu.cn.cc.service.cloudCourse.CloudCourseCacheService;
import com.yice.edu.cn.cc.service.cloudCourse.CloudCourseService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.VideoStream;
import com.yice.edu.cn.common.util.jmessage.api.JMessageUserApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/srsEvent")
@Api(value = "/srsEvent",description = "直播服务")
public class SRSCloudCourseController {
	
	private static final Logger logger = LoggerFactory.getLogger(SRSCloudCourseController.class);

	@Autowired
	private CloudCourseService cloudCourseService;
	@Autowired
	private CloudCourseCacheService cloudCourseCacheService;

	@PostMapping("/onConnect")
	@ApiOperation(value = "验证是否能连接")
	public String onConnect(@RequestBody VideoStream videoStream) {
		
		return "0";
	}
	
//	@PostMapping("/onPublish")
//	@ApiOperation(value = "验证是否有推流权限")
//	public String onPublish(@RequestBody VideoStream videoStream) {
//		String broadcastCode =  videoStream.getStream();
//		if ( broadcastCode == null) {
//			logger.error("broadcastCode is null");
//			return "-1";
//		}
//		CloudCourse cloudCourse = new CloudCourse();
//		cloudCourse.setBroadcastCode(broadcastCode);
//		cloudCourse.setAllowListen(true);
//		long count = cloudCourseService.findCloudCourseCountByCondition(cloudCourse);
//		if (count == 0) {
//			logger.error("broadcastCode="+broadcastCode+"allowListen=true"+" is not exist");
//			return "-1";
//		}
//		return "0";
//	}
	
	@PostMapping("/onStop")
	@ApiOperation(value = "客户端停止播放时减少听课人数")
	public String onStop(@RequestBody VideoStream videoStream) {
		String broadcastCode =  videoStream.getStream();
		if ( broadcastCode == null) {
			return "0";
		}
		cloudCourseCacheService.reduceWatchNum(broadcastCode);
		return "0";
	}

//	@PostMapping("/onPlay")
//	@ApiOperation(value = "客户端开始播放视频增加听课人数")
//	public String onPlay(@RequestBody VideoStream videoStream) {
//		String broadcastCode =  videoStream.getStream();
//		if (broadcastCode == null) {
//			return "-1";
//		}
//		//判断播放码是否存在、是否允许旁听、课程时间是否有效
//		CloudCourse cloudCourse = new CloudCourse();
//		cloudCourse.setBroadcastCode(broadcastCode);
//		cloudCourse.setAllowListen(true);
//		int broadcastCodeCount = cloudCourseService.findCloudCourseValid(cloudCourse);
//		if(broadcastCodeCount==0) {
//			return "-1";
//		}
//		cloudCourseCacheService.addWatchNum(broadcastCode);
//		return "0";
//	}
	
	@GetMapping("/getBroadcastCodeNum/{broadcastCode}")
	@ApiOperation(value = "获取旁听该直播课堂的客户端数")
	public ResponseJson getBroadcastCodeNum(@ApiParam(value = "broadcastCode:直播码(必填)", required = true) @PathVariable String broadcastCode) {
		if (broadcastCode == null) {
			return new ResponseJson(false,"直播码为空!");
		}
		return new ResponseJson(cloudCourseCacheService.getBroadcastCodeNum(broadcastCode));
	}

}
