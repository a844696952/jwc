package com.yice.edu.cn.ewb.controller.prepareLessons;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;
import com.yice.edu.cn.ewb.service.prepareLessons.TeachingPlanService;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.currentTeacher;


@RestController
@RequestMapping("/teachingPlan")
@Api(value = "/teachingPlan",description = "模块")
public class TeachingPlanController {
	
	 	@Autowired
	    private TeachingPlanService teachingPlanService;

	    
	    @PostMapping("/findTeachingPlanListByCondition")
	    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
	    public ResponseJson findTeachingPlanListByCondition(
	            @ApiParam(value = "对象")
	            @RequestBody TeachingPlan teachingPlan){
	    	teachingPlan.setTeacherId(currentTeacher().getId());
	        return teachingPlanService.findTeachingPlanListByCondition(teachingPlan);
	    }

	    @GetMapping("/lookTeachingPlanById/{id}")
	    @ApiOperation(value = "查找教案详情(基本信息,包含课件和资源名称地址等基本信息)", notes = "返回对象")
	    public TeachingPlan lookTeachingPlanById(
	            @ApiParam(value = "教案id", required = true)
	            @PathVariable String id){
	        return teachingPlanService.lookTeachingPlanById(id);
	    }

	    @GetMapping("/downloadTeachingPlan/{id}")
	    public ResponseEntity<byte[]> downloadTeachingPlan(@PathVariable("id") String id,HttpServletResponse response) throws IOException {
	    	response.setCharacterEncoding("utf-8");
	        response.setContentType("application/octet-stream");
	        ResponseEntity<byte[]> responseEntity=teachingPlanService.downloadTeachingPlan(id);
	        return responseEntity;
	    }


		@PostMapping("/findMaterialInformation")
		@ApiOperation(value = "智慧课堂选择教材接口", notes = "返回列表")
		public ResponseJson findMaterialInformation(
				@ApiParam(value = "对象")
				@RequestBody TeachingPlan teachingPlan){
			teachingPlan.setTeacherId(currentTeacher().getId());
			teachingPlan.setSchoolId(currentTeacher().getSchoolId());
			return teachingPlanService.findMaterialInformation(teachingPlan);
		}
    
}
