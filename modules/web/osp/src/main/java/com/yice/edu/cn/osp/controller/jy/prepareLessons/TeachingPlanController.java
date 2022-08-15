package com.yice.edu.cn.osp.controller.jy.prepareLessons;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;
import com.yice.edu.cn.osp.service.jy.prepLessonResource.LessonResMediaFileService;
import com.yice.edu.cn.osp.service.jy.prepLessonResource.PrepLessonResourceFileService;
import com.yice.edu.cn.osp.service.jy.prepareLessons.TeachingPlanService;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/teachingPlan")
@Api(value = "/teachingPlan",description = "模块")
public class TeachingPlanController {
	
	 	@Autowired
	    private TeachingPlanService teachingPlanService;
	    
	    
	    @Autowired
	    private PrepLessonResourceFileService prepLessonResourceFileService;
	    
	    @Autowired
	    private LessonResMediaFileService lessonResMediaFileService;
	    
	    @PostMapping("/findTeachingPlanListByCondition")
	    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
	    public ResponseJson findTeachingPlanListByCondition(
	            @ApiParam(value = "对象")
	            @RequestBody TeachingPlan teachingPlan){
	    	teachingPlan.setTeacherId(currentTeacher().getId());
	        return teachingPlanService.findTeachingPlanListByCondition(teachingPlan);
	    }
	    
	    
	    @PostMapping("/find/findMatFileListByMatItemid")
	    @ApiOperation(value = "根据条件查找平台教案文件", notes = "返回响应对象")
	    public ResponseJson findMatFileByMatItemid(
	            @ApiParam(value = "用到的教材章节id", required = true)
	            @RequestBody PrepLessonResourceFile prepLessonResourceFile) {
	    	prepLessonResourceFile.setFileType("1");
	        List<PrepLessonResourceFile> data = prepLessonResourceFileService.findMatFileListByMatItemid(prepLessonResourceFile);
	        long count = prepLessonResourceFileService.findMatFilesCountByMatItemid(prepLessonResourceFile);
	        return new ResponseJson(data, count);
	    }
	    
	    @GetMapping("/update/downloadCountChangeofDoc/{id}")
	    @ApiOperation(value = "通过id增加文档下载次数")
	    public ResponseJson downloadCountChangeof(
	            @ApiParam(value = "被删除记录的id", required = true)
	            @PathVariable String id){
	        prepLessonResourceFileService.downloadCountChange(id);
	        return new ResponseJson();
	    }
	    
	    @PostMapping("/findSharingTeachingPlan")
	    @ApiOperation(value = "查询已分享的教案列表", notes = "教案列表")
	    public ResponseJson findTeachingPlanListByCondition(
	            @ApiParam(value = "对象")
	            @RequestBody Pager pager){
	    	TeachingPlan tea=new TeachingPlan();
	    	tea.setSchoolId(currentTeacher().getSchoolId());
	    	tea.setShareStatus(1);
	    	tea.setPager(pager);
	        return teachingPlanService.findTeachingPlanListByCondition(tea);
	    }
	    
	    @GetMapping("/share/{id}")
	    @ApiOperation(value = "通过教案id分享教案")
	    public ResponseJson shareTeachingPlan(
	            @ApiParam(value = "教案id", required = true)
	            @PathVariable String id){
	    	TeachingPlan tea=new TeachingPlan();
	    	tea.setId(id);
	    	tea.setShareStatus(1);
	        return teachingPlanService.updateTeachingPlan(tea);
	    }
	    
	    @GetMapping("/cancelShare/{id}")
	    @ApiOperation(value = "通过教案id取消分享教案")
	    public ResponseJson cancelShareTeachingPlan(
	            @ApiParam(value = "教案id", required = true)
	            @PathVariable String id){
	    	TeachingPlan tea=new TeachingPlan();
	    	tea.setId(id);
	    	tea.setShareStatus(0);
	        return teachingPlanService.updateTeachingPlan(tea);
	    }
	    
	    @GetMapping("/update/downloadCountChangeOfMedia/{id}")
	    @ApiOperation(value = "通过id增加音频下载次数", notes = "返回响应对象")
	    public ResponseJson downloadCountChangeOfMedia(
	            @ApiParam(value = "被删除记录的id", required = true)
	            @PathVariable String id){
	    	lessonResMediaFileService.downloadCountChange(id);
	        return new ResponseJson();
	    }
	    
	    @GetMapping("/editTeachingPlanById/{id}")
	    @ApiOperation(value = "查找编辑页面详情(基本信息,包含课件和资源Id,用','隔开)", notes = "返回对象")
	    public TeachingPlan editTeachingPlanById(
	            @ApiParam(value = "教案id", required = true)
	            @PathVariable("id") String id){
	    	TeachingPlan teachingPlan = teachingPlanService.editTeachingPlanById(id);
	    	return teachingPlan;
	    }
	    
	    @GetMapping("/lookTeachingPlanById/{id}")
	    @ApiOperation(value = "查找教案详情(基本信息,包含课件和资源名称地址等基本信息)", notes = "返回对象")
	    public TeachingPlan lookTeachingPlanById(
	            @ApiParam(value = "教案id", required = true)
	            @PathVariable String id){
	        return teachingPlanService.lookTeachingPlanById(id);
	    }

	    @PostMapping("/saveTeachingPlan")
	    @ApiOperation(value = "保存", notes = "返回对象")
	    public ResponseJson saveTeachingPlan(
	            @ApiParam(value = "对象", required = true)
	            @RequestBody TeachingPlan teachingPlan){
	    	teachingPlan.setSchoolId(currentTeacher().getSchoolId());
	    	teachingPlan.setTeacherId(currentTeacher().getId());
	        ResponseJson result = teachingPlanService.saveTeachingPlan(teachingPlan);
	        return result;
	    }

	    @PostMapping("/updateTeachingPlan")
	    @ApiOperation(value = "更新", notes = "对象必传")
	    public ResponseJson updateTeachingPlan(
	            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
	            @RequestBody TeachingPlan teachingPlan){
	        return teachingPlanService.updateTeachingPlan(teachingPlan);
	    }

	    @GetMapping("/deleteTeachingPlan/{id}")
	    @ApiOperation(value = "通过id删除")
	    public ResponseJson deleteTeachingPlan(
	            @ApiParam(value = "对象", required = true)
	            @PathVariable String id){
	        return teachingPlanService.deleteTeachingPlan(id);
	    }
	    
	    @GetMapping("/downloadTeachingPlan/{id}")
	    public ResponseEntity<byte[]> downloadTeachingPlan(@PathVariable("id") String id,HttpServletResponse response) throws IOException {
	    	response.setCharacterEncoding("utf-8");
	        response.setContentType("application/octet-stream");
	        ResponseEntity<byte[]> responseEntity=teachingPlanService.downloadTeachingPlan(id);
	        return responseEntity;
	    }
	    
	    @GetMapping("/delete/lessonsFile/{id}")
	    @ApiOperation(value = "删除资源文件")
	    public ResponseJson deleteLessonsFile(
	            @ApiParam(value = "对象", required = true)
	            @PathVariable String id){
	    	return teachingPlanService.deleteLessonsFile(id);
	    }
    
}
