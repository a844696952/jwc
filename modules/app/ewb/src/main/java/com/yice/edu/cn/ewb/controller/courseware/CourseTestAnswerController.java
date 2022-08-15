package com.yice.edu.cn.ewb.controller.courseware;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseTestAnswer;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.ewb.service.courseware.CourseTestAnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseTestAnswer")
@Api(value = "/courseTestAnswer",description = "课堂检测资源对应的答案模块")
public class CourseTestAnswerController {
    @Autowired
    private CourseTestAnswerService courseTestAnswerService;

    @PostMapping("/saveCourseTestAnswer")
    @ApiOperation(value = "保存课堂检测资源对应的答案对象", notes = "返回保存好的课堂检测资源对应的答案对象", response= CourseTestAnswer.class)
    public ResponseJson saveCourseTestAnswer(
            @ApiParam(value = "课堂检测资源对应的答案对象", required = true)
            @RequestBody CourseTestAnswer courseTestAnswer){
        CourseTestAnswer s=courseTestAnswerService.saveCourseTestAnswer(courseTestAnswer);
        return new ResponseJson(s);
    }


    @PostMapping("/updateCourseTestAnswer")
    @ApiOperation(value = "修改课堂检测资源对应的答案对象", notes = "返回响应对象")
    public ResponseJson updateCourseTestAnswer(
            @ApiParam(value = "被修改的课堂检测资源对应的答案对象,对象属性不为空则修改", required = true)
            @RequestBody CourseTestAnswer courseTestAnswer){
        courseTestAnswerService.updateCourseTestAnswer(courseTestAnswer);
        return new ResponseJson();
    }

    @GetMapping("/findCourseTestAnswerById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找课堂检测资源对应的答案", notes = "返回响应对象", response=CourseTestAnswer.class)
    public ResponseJson findCourseTestAnswerById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CourseTestAnswer courseTestAnswer=courseTestAnswerService.findCourseTestAnswerById(id);
        return new ResponseJson(courseTestAnswer);
    }

    @GetMapping("/deleteCourseTestAnswer/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCourseTestAnswer(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        courseTestAnswerService.deleteCourseTestAnswer(id);
        return new ResponseJson();
    }


    @PostMapping("/findCourseTestAnswerListByCondition")
    @ApiOperation(value = "根据条件查找课堂检测资源对应的答案列表", notes = "返回响应对象,不包含总条数", response=CourseTestAnswer.class)
    public ResponseJson findCourseTestAnswerListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated(GroupOne.class)
            @RequestBody CourseTestAnswer courseTestAnswer){
        List<CourseTestAnswer> data=courseTestAnswerService.findCourseTestAnswerListByCondition(courseTestAnswer);
        return new ResponseJson(data);
    }

}
