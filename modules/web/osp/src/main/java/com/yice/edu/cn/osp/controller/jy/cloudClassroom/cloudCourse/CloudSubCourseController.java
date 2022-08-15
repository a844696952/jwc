package com.yice.edu.cn.osp.controller.jy.cloudClassroom.cloudCourse;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourseLessonsFile;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse.CloudSubCourseLessonsFileService;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse.CloudSubCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/cloudSubCourse")
@Api(value = "/cloudSubCourse",description = "云课堂子课程表模块")
public class CloudSubCourseController {
    @Autowired
    private CloudSubCourseService cloudSubCourseService;
    @Autowired
    private CloudSubCourseLessonsFileService cloudSubCourseLessonsFileService;
    @PostMapping("/saveCloudSubCourse")
    @ApiOperation(value = "保存云课堂子课程表对象", notes = "返回保存好的云课堂子课程表对象", response=CloudSubCourse.class)
    public ResponseJson saveCloudSubCourse(
            @ApiParam(value = "云课堂子课程表对象", required = true)
            @RequestBody CloudSubCourse cloudSubCourse){
       cloudSubCourse.setSchoolId(mySchoolId());
        CloudSubCourse s=cloudSubCourseService.saveCloudSubCourse(cloudSubCourse);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findCloudSubCourseById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找云课堂子课程表", notes = "返回响应对象", response=CloudSubCourse.class)
    public ResponseJson findCloudSubCourseById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CloudSubCourse cloudSubCourse=cloudSubCourseService.findCloudSubCourseById(id);
        return new ResponseJson(cloudSubCourse);
    }

    @PostMapping("/update/updateCloudSubCourse")
    @ApiOperation(value = "修改云课堂子课程表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateCloudSubCourse(
            @ApiParam(value = "被修改的云课堂子课程表对象,对象属性不为空则修改", required = true)
            @RequestBody CloudSubCourse cloudSubCourse){
        cloudSubCourseService.updateCloudSubCourse(cloudSubCourse);
        return new ResponseJson();
    }

    @PostMapping("/update/updateCloudSubCourseForAll")
    @ApiOperation(value = "修改云课堂子课程表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateCloudSubCourseForAll(
            @ApiParam(value = "被修改的云课堂子课程表对象", required = true)
            @RequestBody CloudSubCourse cloudSubCourse){
        cloudSubCourseService.updateCloudSubCourseForAll(cloudSubCourse);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCloudSubCourseById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找云课堂子课程表", notes = "返回响应对象", response=CloudSubCourse.class)
    public ResponseJson lookCloudSubCourseById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CloudSubCourse cloudSubCourse=cloudSubCourseService.findCloudSubCourseById(id);
        return new ResponseJson(cloudSubCourse);
    }

    @PostMapping("/findCloudSubCoursesByCondition")
    @ApiOperation(value = "根据条件查找云课堂子课程表", notes = "返回响应对象", response=CloudSubCourse.class)
    public ResponseJson findCloudSubCoursesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudSubCourse cloudSubCourse){
        cloudSubCourse.setSchoolId(mySchoolId());
        List<CloudSubCourse> data=cloudSubCourseService.findCloudSubCourseListByCondition(cloudSubCourse);
        long count=cloudSubCourseService.findCloudSubCourseCountByCondition(cloudSubCourse);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findOneCloudSubCourseByCondition")
    @ApiOperation(value = "根据条件查找单个云课堂子课程表,结果必须为单条数据", notes = "没有时返回空", response=CloudSubCourse.class)
    public ResponseJson findOneCloudSubCourseByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CloudSubCourse cloudSubCourse){
        CloudSubCourse one=cloudSubCourseService.findOneCloudSubCourseByCondition(cloudSubCourse);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCloudSubCourse/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCloudSubCourse(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        cloudSubCourseService.deleteCloudSubCourse(id);
        return new ResponseJson();
    }


    @PostMapping("/findCloudSubCourseListByCondition")
    @ApiOperation(value = "根据条件查找云课堂子课程表列表", notes = "返回响应对象,不包含总条数", response=CloudSubCourse.class)
    public ResponseJson findCloudSubCourseListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudSubCourse cloudSubCourse){
       cloudSubCourse.setSchoolId(mySchoolId());
        List<CloudSubCourse> data=cloudSubCourseService.findCloudSubCourseListByCondition(cloudSubCourse);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/genQrCode")
    @ApiOperation(value = "生成课堂二维码", notes = "返回响应对象", response= CloudCourse.class)
    private ResponseJson genQrCode(
            @ApiParam(value = "{broadcastCode:直播码(必填)}", required = true)
            @RequestBody CloudSubCourse cloudSubCourse) {
        return new ResponseJson(cloudSubCourseService.genQrCode(cloudSubCourse));
    }

    @PostMapping("/findLessonPreparationListByCondition")
    @ApiOperation(value = "根据条件查找备课列表", notes = "返回响应对象,不包含总条数", response=CloudSubCourse.class)
    public ResponseJson findLessonPreparationListByCondition(@RequestBody CloudSubCourse cloudSubCourse){
        List<CloudSubCourse> cloudSubCourseList = cloudSubCourseService.findCloudSubCourseListByCondition(cloudSubCourse);
        CloudSubCourseLessonsFile cloudSubCourseLessonsFile=new CloudSubCourseLessonsFile();
        cloudSubCourseList.forEach(s->{
             cloudSubCourseLessonsFile.setCloudSubCourseId(s.getId());
            long num = cloudSubCourseLessonsFileService.findCloudSubCourseLessonsFileCountByCondition(cloudSubCourseLessonsFile);
            s.setCoursewareNum(num);
        });
        return new ResponseJson(cloudSubCourseList);
    }


}
