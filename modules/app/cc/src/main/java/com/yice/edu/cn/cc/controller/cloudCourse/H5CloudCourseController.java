package com.yice.edu.cn.cc.controller.cloudCourse;

import com.yice.edu.cn.cc.service.cloudCourse.CloudCourseCacheService;
import com.yice.edu.cn.cc.service.cloudCourse.CloudCourseService;
import com.yice.edu.cn.cc.service.cloudCourse.CloudSubCourseService;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.SrsQrCodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/h5nl/cloudCourse")
@Api(value = "/h5nl/cloudCourse", description = "H5云课堂")
public class H5CloudCourseController {
	@Autowired
	private CloudCourseService cloudCourseService;

	@Autowired
	private CloudSubCourseService cloudSubCourseService;

	@Autowired
	private CloudCourseCacheService cloudCourseCacheService;

	@PostMapping("/getHlsUrl")
	@ApiOperation(value = "获取HLS协议的直播地址", notes = "返回响应对象", response = SrsQrCodeVo.class)
	private ResponseJson getHlsUrl(
			@ApiParam(value = "{broadcastCode:直播码(必填)}", required = true) @RequestBody CloudSubCourse cloudSubCourse) {
		return new ResponseJson(cloudSubCourseService.getHlsPath(cloudSubCourse));
	}

//	@PostMapping("/getCCNum")
//	@ApiOperation(value = "获取该直播间的观看人数", notes = "返回响应对象", response = Integer.class)
//	private ResponseJson getCCNum(
//			@ApiParam(value = "{broadcastCode:直播码(必填)}", required = true) @RequestBody CloudCourse cloudCourse) {
//		return new ResponseJson(cloudCourseCacheService.getBroadcastCodeNum(cloudCourse.getBroadcastCode()));
//	}

	@PostMapping("/getCCourseInfo")
	@ApiOperation(value = "获取云课堂的信息", notes = "返回响应对象", response = CloudCourse.class)
	private ResponseJson getCCourseInfo(
			@ApiParam(value = "{broadcastCode:直播码(必填)}", required = true) @RequestBody CloudCourse cloudCourse) {
		Pager pager = new Pager();
		pager.addExcludes("listenTeachers", "otherSchoolAccounts");
		cloudCourse.setPager(pager);
		List<CloudCourse> data = cloudCourseService.findCloudCourseListByCondition(cloudCourse);
		if (data.isEmpty()) {
			return new ResponseJson(false, "找不到该课堂!");
		}
		if (data.size() > 1) {
			return new ResponseJson(false, "存在多个课堂信息!");
		}
		return new ResponseJson(data.get(0));
	}

//	@PostMapping("/addWatchPeople")
//	@ApiOperation(value = "添加观看直播人数", notes = "返回观看人数", response = Integer.class)
//	private ResponseJson addwatchPeople(
//			@ApiParam(value = "{broadcastCode:直播码(必填)}", required = true) @RequestBody CloudCourse cloudCourse) {
//		long num = cloudCourseService.findCloudCourseCountByCondition(cloudCourse);
//		if (num<=0) {
//			return new ResponseJson(false, "找不到该课堂!");
//		}
//		int count = cloudCourseCacheService.addWatchNum(cloudCourse.getBroadcastCode());
//		return new ResponseJson(count);
//	}
//
//	@PostMapping("/reduceWatchPeople")
//	@ApiOperation(value = "减少观看直播人数", notes = "返回观看人数", response = Integer.class)
//	private ResponseJson reduceWatchPeople(
//			@ApiParam(value = "{broadcastCode:直播码(必填)}", required = true) @RequestBody CloudCourse cloudCourse) {
//		long num = cloudCourseService.findCloudCourseCountByCondition(cloudCourse);
//		if (num<=0) {
//			return new ResponseJson(false, "找不到该课堂!");
//		}
//		int count = cloudCourseCacheService.reduceWatchNum(cloudCourse.getBroadcastCode());
//		return new ResponseJson(count);
//	}
	
}
