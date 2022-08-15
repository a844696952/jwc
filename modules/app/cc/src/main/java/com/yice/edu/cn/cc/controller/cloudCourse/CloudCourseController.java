package com.yice.edu.cn.cc.controller.cloudCourse;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.cc.service.cloudCourse.*;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.*;
import com.yice.edu.cn.common.util.VideoThumbnailUtils;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cloudCourse")
@Api(value = "/cloudCourse", description = "云课堂服务")
public class CloudCourseController {
	@Autowired
	private CloudCourseService cloudCourseService;

	@Autowired
	private CloudSubCourseService cloudSubCourseService;
	@Autowired
	private CloudCourseResourceService cloudCourseResourceService;
	@Autowired
	private CloudCourseCacheService cloudCourseCacheService;
	@Autowired
	private CCFileResourceService cCFileResourceService;

//	@PostMapping("/findCloudCourses")
//	@ApiOperation(value = "根据登陆用户查找可加入的云课程列表", notes = "返回响应对象", response = CloudCourse.class)
//	public ResponseJson findCloudCourses() {
//		CloudCourse cloudCourse = new CloudCourse();
//		cloudCourse.setLoginId(myId());
//		Pager pager = new Pager();
//		pager.setPaging(false);
//		pager.addExcludes("listenTeachers", "otherSchoolAccounts");
//		pager.setSortField("inTime");
//		pager.setSortOrder("ASC");
//		cloudCourse.setPager(pager);
//		List<CloudCourse> data = cloudCourseService.findCloudCourseListForAll(cloudCourse);
//		long count = cloudCourseService.findCloudCourseCountForAll(cloudCourse);
//		return new ResponseJson(data, count);
//	}

	@GetMapping("/findCloudCourseById/{id}")
	@ApiOperation(value = "通过id查找云课程详情", notes = "返回响应对象", response = CloudCourse.class)
	public ResponseJson findCloudCourseById(@ApiParam(value = "云课堂id", required = true) @PathVariable String id) {
		CloudCourse cloudCourse = cloudCourseService.findCloudCourseById(id);
		return new ResponseJson(cloudCourse);
	}
	@PostMapping("/addCloudCourseResourceInfo")
	@ApiOperation(value = "添加上课的信息", notes = "返回响应对象")
	public ResponseJson addResourceFile(
			@ApiParam(value = "{cloudCourseId:课堂id(必填),courseTime:上课时长(非必填),beginTime:开始上课时间(非必填)}", required = true) 
			@RequestBody CloudCourseResource cloudCourseResource) {
		if (cloudCourseResource.getCloudCourseId() == null) {
			return new ResponseJson(false, "课程id不能为null!");
		}
		CloudCourseResource queryCloudCourseResource = new CloudCourseResource();
		queryCloudCourseResource.setCloudCourseId(cloudCourseResource.getCloudCourseId());
		long num = cloudCourseResourceService.findCloudCourseResourceCountByCondition(queryCloudCourseResource);
		if(num>0) {
			return new ResponseJson();
		}
		CloudCourse queryCloudCourse = cloudCourseService.findCloudCourseById(cloudCourseResource.getCloudCourseId());
		if (queryCloudCourse == null) {
			return new ResponseJson(false, "找不到该课程!");
		}

//		cloudCourseResource.setTeacher(myId());
		cloudCourseResource.setSchoolId(queryCloudCourse.getSchoolId());
		cloudCourseResource.setCloudCourseName(queryCloudCourse.getName());
		cloudCourseResource.setCloudCourseId(cloudCourseResource.getCloudCourseId());
		cloudCourseResource.setBeginTime(cloudCourseResource.getBeginTime()==null?"":cloudCourseResource.getBeginTime());
		cloudCourseResource.setCourseTime(cloudCourseResource.getCourseTime()==null?0:cloudCourseResource.getCourseTime());
		cloudCourseResourceService.saveCloudCourseResource(cloudCourseResource);

		return new ResponseJson();
	}

//	@PostMapping("/updateCoursePerStatus")
//	@ApiOperation(value = "修改课堂人员的状态(在线、离线）", notes = "返回响应对象")
//	public ResponseJson updateCoursePerStatusOnline(
//			@ApiParam(value = "{userId:用户id,userType:用户类型(0校内  1校外),broadcastCode:房间码,status:状态 1.在线 2.离线,uuId:当前房间内的客户端唯一标识,mac:客户端的mac地址(在线状态必填)}", required = true) @RequestBody CoursePerVo coursePerVo) {
//
//		CloudCourse queryCloudCourse = new CloudCourse();
//		//queryCloudCourse.setBroadcastCode(coursePerVo.getBroadcastCode());
//		CloudCourse cloudCourse = cloudCourseService.findOneCloudCourseByCondition(queryCloudCourse);
//		if (cloudCourse == null) {
//			return new ResponseJson(false, "没有该课程!");
//		}
//		boolean resultReturn = cloudCourseCacheService.updateCoursePerStatus(cloudCourse, coursePerVo);
//		if (!resultReturn) {
//			return new ResponseJson(false, "修改失败!");
//		}
//		CoursePerVo returnVo = cloudCourseCacheService.findCoursePerStatus(coursePerVo);
//		return new ResponseJson(returnVo);
//	}

//	@PostMapping("/updateCoursePerLecturer")
//	@ApiOperation(value = "修改某个课堂人员主讲人", notes = "返回响应对象", response = CoursePerVo.class)
//	public ResponseJson updateCoursePerLecturer(
//			@ApiParam(value = "{userId:用户id,userType:用户类型(0校内  1校外),broadcastCode:房间码,lecturer:是否是主讲人(true,false)}", required = true) @RequestBody CoursePerVo coursePerVo) {
//
//		CloudCourse queryCloudCourse = new CloudCourse();
//		//queryCloudCourse.setBroadcastCode(coursePerVo.getBroadcastCode());
//		CloudCourse cloudCourse = cloudCourseService.findOneCloudCourseByCondition(queryCloudCourse);
//		if (cloudCourse == null) {
//			return new ResponseJson(false, "没有该课程!");
//		}
//		boolean resultReturn = cloudCourseCacheService.updateCoursePerStatus(cloudCourse, coursePerVo);
//		if (!resultReturn) {
//			return new ResponseJson(false, "修改失败!");
//		}
//		CoursePerVo returnVo = cloudCourseCacheService.findCoursePerLecturer(coursePerVo.getBroadcastCode());
//		return new ResponseJson(returnVo);
//	}

	@PostMapping("/getCourseLecturer")
	@ApiOperation(value = "查询某个课堂的当前主讲人", notes = "返回响应对象", response = CoursePerVo.class)
	public ResponseJson getCourseLecturer(
			@ApiParam(value = "{broadcastCode:房间码)}", required = true) @RequestBody CoursePerVo coursePerVo) {
		CoursePerVo vo = cloudCourseCacheService.findCoursePerLecturer(coursePerVo.getBroadcastCode());
		if (vo == null) {
			return new ResponseJson(new CoursePerVo());
		}
		return new ResponseJson(vo);
	}

	@PostMapping("/getCourseOnlineStatus")
	@ApiOperation(value = "查询某个课堂的某一个人员在线状态", notes = "返回响应对象", response = CoursePerVo.class)
	public ResponseJson getCourseOnlineStatus(
			@ApiParam(value = "{userId:用户id,userType:用户类型(0校内  1校外),broadcastCode:房间码)}", required = true) @RequestBody CoursePerVo coursePerVo) {
		CoursePerVo vo = cloudCourseCacheService.findCoursePerStatus(coursePerVo);
		if (vo == null) {
			return new ResponseJson(new CoursePerVo());
		}
		return new ResponseJson(vo);
	}

	@PostMapping("/getHlsPageUrl")
	@ApiOperation(value = "获取观看直播地址", notes = "返回响应对象", response = SrsQrCodeVo.class)
	public ResponseJson getHlsPageUrl(
			@ApiParam(value = "{broadcastCode:直播码(必填)}", required = true) @RequestBody CloudSubCourse cloudSubCourse) {
		if (cloudSubCourse.getBroadcastCode() == null) {
			return new ResponseJson(false, "直播码为null");
		}
		return new ResponseJson(cloudSubCourseService.genQrCode(cloudSubCourse));
	}

	@PostMapping("/getLivePushUrl")
	@ApiOperation(value = "获取直播的推流地址", notes = "返回响应对象", response=String.class)
	public ResponseJson getLivePushUrl(
			@ApiParam(value = "{broadcastCode:直播码(必填)}", required = true)
			@RequestBody CloudSubCourse cloudSubCourse) {
		if (cloudSubCourse.getBroadcastCode() == null) {
			return new ResponseJson(false, "直播码为null");
		}
		CloudCourse queryCloudCourse = new CloudCourse();
		queryCloudCourse.setAllowListen(true);
		CloudSubCourse queryCloudSubCourse = new CloudSubCourse();
		queryCloudSubCourse.setCloudCourse(queryCloudCourse);
		long count = cloudSubCourseService.findCloudSubCourseCountByCondition(queryCloudSubCourse);
		if(count==0) {
			return new ResponseJson(false,"该房间不能推流!");
		}
		return new ResponseJson(cloudSubCourseService.getLivePushUrl(queryCloudSubCourse));
	}


	@PostMapping("/endCC")
	@ApiOperation(value = "结束课堂", notes = "返回响应对象", response = SrsQrCodeVo.class)
	public ResponseJson endCC(
			@ApiParam(value = "{broadcastCode:房间码(必填),userId:用户id,userType:用户类型(0校内  1校外)}", required = true) @RequestBody CoursePerVo coursePerVo) {
		if (coursePerVo.getBroadcastCode() == null) {
			return new ResponseJson(false, "房间码为null");
		}
		CoursePerVo vo = cloudCourseCacheService.findCoursePerLecturer(coursePerVo.getBroadcastCode());
		// 判断是否是主讲人
		if (vo == null) {
			return new ResponseJson(false, "找不到主讲人!");
		}
		if (!vo.getUserId().equals(coursePerVo.getUserId())
				|| vo.getUserType().intValue() != coursePerVo.getUserType().intValue()) {
			return new ResponseJson(false, "不是主讲人不能结束课程!");
		}
		boolean returnResult = cloudCourseCacheService.clearCoursePerStatusCache(coursePerVo.getBroadcastCode());
		if (!returnResult) {
			return new ResponseJson(false, "结束失败");
		}
		return new ResponseJson();
	}

    @PostMapping("/getUploadAuth")
    @ApiOperation(value = "获取七牛上传凭证", notes = "返回响应对象", response=CcUploadViewVo.class)
    public ResponseJson getUploadAuth(
    		@RequestBody
    		@ApiParam(value = "{name:文件名称,type:1.共享文件 2.录播文件}", required = true)
    		CcUploadParams ccUploadParams) {
      String newName =  QiniuUtil.newName() + ccUploadParams.getName().substring(ccUploadParams.getName().lastIndexOf("."));
      String key = QiniuUtil.getDatePath() + "/"+ newName;
      if(ccUploadParams.getType().intValue()==2) {
    	  key=Constant.Upload.UPLOAD_CLASS_VIDEO+key;
      }
      if(ccUploadParams.getType().intValue()==1) {
    	  key=Constant.Upload.UPLOAD_CLOUDCOURSE_RESOURSE+key;
      }
      String token = QiniuUtil.getSimpleToken();
      CcUploadViewVo viewVo = new CcUploadViewVo();
      viewVo.setPath(key);
      viewVo.setToken(token);
	  return new ResponseJson(viewVo);
    }
    
	@PostMapping("/addVideoFile")
	@ApiOperation(value = "添加录屏地址")
	public ResponseJson addVideoFile(
			@ApiParam(value = "{name:文件名称,cloudCourseId:课堂id,url:文件上传路径}", required = true)
			@RequestBody
					CloudCourseFileResource cloudCourseFileResourceParam) {
		String subCourseId = cloudCourseFileResourceParam.getCloudSubCourseId();
//		CloudCourse cloudCourse = cloudCourseService
//				.findCloudCourseById();
//		if (cloudCourse == null) {
//			return new ResponseJson(false, "找不到该课程的信息资源!");
//		}
		CloudCourseFileResource cloudCourseFileResource = new CloudCourseFileResource();
		cloudCourseFileResource.setCloudSubCourseId(subCourseId);
		cloudCourseFileResource.setName(cloudCourseFileResourceParam.getName());
		cloudCourseFileResource.setUrl(cloudCourseFileResourceParam.getUrl());
		cloudCourseFileResource.setType(Constant.CCourse.VIDEO_RESOURCES);
		cloudCourseFileResource.setThumbnail(cloudCourseFileResourceParam.getUrl()+VideoThumbnailUtils.getVideoThumbnailUtils(Constant.FILE_SERVICE.TYPE_QINIU));
//		cloudCourseFileResource.setCloudCourseResourceId(cloudCourse.getId());
		cloudCourseFileResource.setCreateTime(DateUtil.now());
//		cloudCourseFileResource.setSchoolId(cloudCourse.getSchoolId());
		cCFileResourceService.saveCCFileResource(cloudCourseFileResource);

		return new ResponseJson();
	}
}
