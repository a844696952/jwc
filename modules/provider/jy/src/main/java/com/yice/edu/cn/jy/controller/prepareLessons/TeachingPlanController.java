package com.yice.edu.cn.jy.controller.prepareLessons;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.jy.service.prepareLessons.TeachingPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/teachingPlan")
@Api(value = "/teachingPlan", description = "模块")
public class TeachingPlanController {

	@Autowired
	private TeachingPlanService teachingPlanService;

	@PostMapping("/findTeachingPlanListByCondition")
	@ApiOperation(value = "根据条件查找列表", notes = "返回列表")
	public ResponseJson findTeachingPlanListByCondition(
			@ApiParam(value = "对象") @RequestBody TeachingPlan teachingPlan) {
		return teachingPlanService.findTeachingPlanListByCondition(teachingPlan);
	}

	@GetMapping("/editTeachingPlanById/{id}")
	@ApiOperation(value = "查找编辑页面详情(基本信息,包含课件和资源Id,用','隔开)", notes = "返回对象")
	public TeachingPlan editTeachingPlanById(@ApiParam(value = "教案id", required = true) @PathVariable String id) {
		return teachingPlanService.editTeachingPlanById(id);
	}

	@GetMapping("/lookTeachingPlanById/{id}")
	@ApiOperation(value = "查找教案详情(基本信息,包含课件和资源名称地址等基本信息)", notes = "返回对象")
	public TeachingPlan lookTeachingPlanById(@ApiParam(value = "教案id", required = true) @PathVariable String id) {
		return teachingPlanService.lookTeachingPlanById(id);
	}

	@PostMapping("/saveTeachingPlan")
	@ApiOperation(value = "保存", notes = "返回对象")
	public ResponseJson saveTeachingPlan(
			@ApiParam(value = "对象", required = true) @RequestBody @Validated(value = GroupOne.class) TeachingPlan teachingPlan) {
		ResponseJson result = teachingPlanService.saveTeachingPlan(teachingPlan);
		return result;
	}

	@PostMapping("/updateTeachingPlan")
	@ApiOperation(value = "更新教案", notes = "对象必传")
	public ResponseJson updateTeachingPlan(
			@ApiParam(value = "对象,对象属性不为空则修改", required = true) @RequestBody @Validated(value = GroupTwo.class) TeachingPlan teachingPlan) {
		return teachingPlanService.updateTeachingPlan(teachingPlan);
	}

	@GetMapping("/deleteTeachingPlan/{id}")
	@ApiOperation(value = "通过id删除教案")
	public ResponseJson deleteTeachingPlan(@ApiParam(value = "对象", required = true) @PathVariable String id) {
		return teachingPlanService.deleteTeachingPlan(id);
	}

	@GetMapping("/download/{id}")
	@ApiOperation(value = "下载教案")
	public void downloadTeachingPlan(@ApiParam(value = "对象", required = true) @PathVariable String id) {
		teachingPlanService.downloadTeachingPlan(id);
	}

	@GetMapping("/delete/lessonsFile/{id}")
	@ApiOperation(value = "删除备课资源")
	public ResponseJson deleteLessonsFile(@ApiParam(value = "对象", required = true) @PathVariable String id) {
		return teachingPlanService.deleteLessonsFile(id);
	}


	@GetMapping("/downloadTeachingPlan/{id}")
	public ResponseEntity<byte[]> downloadTeachingPlan(@PathVariable("id") String id, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/octet-stream");
		ResponseEntity<byte[]> responseEntity = teachingPlanService.downloadTeachingPlan(id);
		return responseEntity;
	}



	@PostMapping("/findMaterialInformation")
	@ApiOperation(value = "智慧课堂选择教材", notes = "返回列表")
	public ResponseJson findMaterialInformation(
			@ApiParam(value = "对象") @RequestBody TeachingPlan teachingPlan) {
		return teachingPlanService.findMaterialInformation(teachingPlan);
	}

}
