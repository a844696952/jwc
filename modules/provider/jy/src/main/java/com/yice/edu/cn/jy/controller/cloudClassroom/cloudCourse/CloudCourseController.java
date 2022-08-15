package com.yice.edu.cn.jy.controller.cloudClassroom.cloudCourse;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudCourseFileResourceService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudCourseService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudSubCourseService;
import com.yice.edu.cn.jy.service.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cloudCourse")
@Api(value = "/cloudCourse",description = "云课程模块")
public class CloudCourseController {
    @Autowired
    private CloudCourseService cloudCourseService;
    @Autowired
    private CloudSubCourseService cloudSubCourseService;
    @Autowired
    private TeacherService teacherService;
	@Autowired
    private CloudCourseFileResourceService cloudCourseFileResourceService;

    private static final Logger logger = LoggerFactory.getLogger(CloudCourseController.class);

    @GetMapping("/findCloudCourseById/{id}")
    @ApiOperation(value = "通过id查找云课程", notes = "返回云课程对象")
    public CloudCourse findCloudCourseById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return cloudCourseService.findCloudCourseById(id);
    }

    @PostMapping("/saveCloudCourse")
    @ApiOperation(value = "保存云课程", notes = "返回云课程对象")
    public CloudCourse saveCloudCourse(
            @ApiParam(value = "云课程对象", required = true)
            @RequestBody CloudCourse cloudCourse) throws Exception{
        cloudCourseService.saveCloudCourse(cloudCourse);
        List<CloudSubCourse> cloudSubCourseList = cloudCourse.getCloudSubCourseList();
        //避免重复引用栈溢出
        cloudCourse.setCloudSubCourseList(null);
        assembleCloudSubCourse(cloudCourse, cloudSubCourseList);
        cloudSubCourseService.batchSaveCloudSubCourse(cloudSubCourseList);
        return cloudCourse;
    }

    private void assembleCloudSubCourse(CloudCourse cloudCourse, List<CloudSubCourse> cloudSubCourseList) {
        cloudSubCourseList.forEach(cloudSubCourse -> {
            cloudSubCourse.setCloudCourse(cloudCourse);
            Teacher teacher = teacherService.findTeacherById(cloudSubCourse.getTeacher().getId());
            Teacher teacherInfo = new Teacher();
            teacherInfo.setId(teacher.getId());
            teacherInfo.setImgUrl(teacher.getImgUrl());
            teacherInfo.setName(teacher.getName());
            teacherInfo.setTel(teacher.getTel());
            teacherInfo.setStatus(teacher.getStatus());
            teacherInfo.setSchoolId(teacher.getSchoolId());
            teacherInfo.setSchoolName(teacher.getSchoolName());
            cloudSubCourse.setTeacher(teacherInfo);
        });
    }

    @PostMapping("/findCloudCourseListByCondition")
    @ApiOperation(value = "根据条件查找云课程列表", notes = "返回云课程列表")
    public List<CloudCourse> findCloudCourseListByCondition(
            @ApiParam(value = "云课程对象")
            @RequestBody CloudCourse cloudCourse){
        return cloudCourseService.findCloudCourseListByCondition(cloudCourse);
    }
    @PostMapping("/findCloudCourseCountByCondition")
    @ApiOperation(value = "根据条件查找云课程列表个数", notes = "返回云课程总个数")
    public long findCloudCourseCountByCondition(
            @ApiParam(value = "云课程对象")
            @RequestBody CloudCourse cloudCourse){
        return cloudCourseService.findCloudCourseCountByCondition(cloudCourse);
    }

    @PostMapping("/updateCloudCourse")
    @ApiOperation(value = "修改云课程", notes = "云课程对象必传")
    public void updateCloudCourse(
            @ApiParam(value = "云课程对象,对象属性不为空则修改", required = true)
            @RequestBody CloudCourse cloudCourse) throws Exception{
			cloudCourseService.updateCloudCourse(cloudCourse);
            List<CloudSubCourse> cloudSubCourseList = cloudCourse.getCloudSubCourseList();
            //避免重复引用栈溢出
            cloudCourse.setCloudSubCourseList(null);
            assembleCloudSubCourse(cloudCourse, cloudSubCourseList);
            cloudSubCourseService.batchUpsertCloudSubCourse(cloudSubCourseList);
    }

    @GetMapping("/deleteCloudCourse/{id}")
    @ApiOperation(value = "通过id删除云课程")
    public void deleteCloudCourse(
            @ApiParam(value = "云课程对象", required = true)
            @PathVariable String id){
        cloudCourseService.deleteCloudCourse(id);
    }
    @PostMapping("/deleteCloudCourseByCondition")
    @ApiOperation(value = "根据条件删除云课程")
    public void deleteCloudCourseByCondition(
            @ApiParam(value = "云课程对象")
            @RequestBody CloudCourse cloudCourse){
        cloudCourseService.deleteCloudCourseByCondition(cloudCourse);
    }
    @PostMapping("/findOneCloudCourseByCondition")
    @ApiOperation(value = "根据条件查找单个云课程,结果必须为单条数据", notes = "返回单个云课程,没有时为空")
    public CloudCourse findOneCloudCourseByCondition(
            @ApiParam(value = "云课程对象")
            @RequestBody CloudCourse cloudCourse){
        return cloudCourseService.findOneCloudCourseByCondition(cloudCourse);
    }

    @PostMapping("/findCloudCoursesByConditionOther")
    @ApiOperation(value = "根据条件查找云课程列表", notes = "返回云课程列表")
    public List<CloudCourse> findCloudCoursesByConditionOther(
            @ApiParam(value = "云课程对象")
            @RequestBody CloudCourse cloudCourse){
        return cloudCourseService.findCloudCoursesByConditionOther(cloudCourse);
    }
    @PostMapping("/findCloudCourseCountByConditionOther")
    @ApiOperation(value = "根据条件查找云课程列表个数", notes = "返回云课程总个数")
    public long findCloudCourseCountByConditionOther(
            @ApiParam(value = "云课程对象")
            @RequestBody CloudCourse cloudCourse){
        return cloudCourseService.findCloudCourseCountByConditionOther(cloudCourse);
    }

    @PostMapping("/findCloudCoursesByConditionMine")
    @ApiOperation(value = "根据条件查找云课程列表", notes = "返回云课程列表")
    public List<CloudCourse> findCloudCoursesByConditionMine(
            @ApiParam(value = "云课程对象")
            @RequestBody CloudCourse cloudCourse){
        return cloudCourseService.findCloudCoursesByConditionMine(cloudCourse);
    }
    @PostMapping("/findCloudCourseCountByConditionMine")
    @ApiOperation(value = "根据条件查找云课程列表个数", notes = "返回云课程总个数")
    public long findCloudCourseCountByConditionMine(
            @ApiParam(value = "云课程对象")
            @RequestBody CloudCourse cloudCourse){
        return cloudCourseService.findCloudCourseCountByConditionMine(cloudCourse);
    }



//    @PostMapping("/findCloudCourseListForTeacher")
//    public List<CloudCourse> findCloudCourseListForTeacher(@RequestBody CloudCourse cloudCourse){
//        return cloudCourseService.findCloudCourseListForTeacher(cloudCourse);
//    }
//    @PostMapping("/findCloudCourseCountForTeacher")
//    public long findCloudCourseCountForTeacher(@RequestBody CloudCourse cloudCourse){
//        return cloudCourseService.findCloudCourseCountForTeacher(cloudCourse);
//    }
//
//    @PostMapping("/findCloudCourseListForOther")
//    public List<CloudCourse> findCloudCourseListForOther(@RequestBody CloudCourse cloudCourse){
//        return cloudCourseService.findCloudCourseListForOther(cloudCourse);
//    }
//    @PostMapping("/findCloudCourseCountForOther")
//    public long findCloudCourseCountForOther(@RequestBody CloudCourse cloudCourse){
//        return cloudCourseService.findCloudCourseCountForOther(cloudCourse);
//    }
//
//    @PostMapping("/findCloudCourseListForAll")
//    public List<CloudCourse> findCloudCourseListForAll(@RequestBody CloudCourse cloudCourse){
//    	List<CloudCourse> data = cloudCourseService.findCloudCourseListForAll(cloudCourse);
//        return data;
//    }
//
//    @PostMapping("/findCloudCourseCountForAll")
//    public long findCloudCourseCountForAll(@RequestBody CloudCourse cloudCourse){
//        return cloudCourseService.findCloudCourseCountForAll(cloudCourse);
//    }

}
