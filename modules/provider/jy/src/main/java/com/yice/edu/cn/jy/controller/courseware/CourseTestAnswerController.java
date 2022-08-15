package com.yice.edu.cn.jy.controller.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseTestAnswer;
import com.yice.edu.cn.jy.service.courseware.CourseTestAnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseTestAnswer")
@Api(value = "/courseTestAnswer",description = "课堂检测资源对应的答案模块")
public class CourseTestAnswerController {
    @Autowired
    private CourseTestAnswerService courseTestAnswerService;

    @GetMapping("/findCourseTestAnswerById/{id}")
    @ApiOperation(value = "通过id查找课堂检测资源对应的答案", notes = "返回课堂检测资源对应的答案对象")
    public CourseTestAnswer findCourseTestAnswerById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return courseTestAnswerService.findCourseTestAnswerById(id);
    }

    @PostMapping("/saveCourseTestAnswer")
    @ApiOperation(value = "保存课堂检测资源对应的答案", notes = "返回课堂检测资源对应的答案对象")
    public CourseTestAnswer saveCourseTestAnswer(
            @ApiParam(value = "课堂检测资源对应的答案对象", required = true)
            @RequestBody CourseTestAnswer courseTestAnswer){
        courseTestAnswerService.saveCourseTestAnswer(courseTestAnswer);
        return courseTestAnswer;
    }

    @PostMapping("/findCourseTestAnswerListByCondition")
    @ApiOperation(value = "根据条件查找课堂检测资源对应的答案列表", notes = "返回课堂检测资源对应的答案列表")
    public List<CourseTestAnswer> findCourseTestAnswerListByCondition(
            @ApiParam(value = "课堂检测资源对应的答案对象")
            @RequestBody CourseTestAnswer courseTestAnswer){
        return courseTestAnswerService.findCourseTestAnswerListByCondition(courseTestAnswer);
    }
    @PostMapping("/findCourseTestAnswerCountByCondition")
    @ApiOperation(value = "根据条件查找课堂检测资源对应的答案列表个数", notes = "返回课堂检测资源对应的答案总个数")
    public long findCourseTestAnswerCountByCondition(
            @ApiParam(value = "课堂检测资源对应的答案对象")
            @RequestBody CourseTestAnswer courseTestAnswer){
        return courseTestAnswerService.findCourseTestAnswerCountByCondition(courseTestAnswer);
    }

    @PostMapping("/updateCourseTestAnswer")
    @ApiOperation(value = "修改课堂检测资源对应的答案", notes = "课堂检测资源对应的答案对象必传")
    public void updateCourseTestAnswer(
            @ApiParam(value = "课堂检测资源对应的答案对象,对象属性不为空则修改", required = true)
            @RequestBody CourseTestAnswer courseTestAnswer){
        courseTestAnswerService.updateCourseTestAnswer(courseTestAnswer);
    }

    @GetMapping("/deleteCourseTestAnswer/{id}")
    @ApiOperation(value = "通过id删除课堂检测资源对应的答案")
    public void deleteCourseTestAnswer(
            @ApiParam(value = "课堂检测资源对应的答案对象", required = true)
            @PathVariable String id){
        courseTestAnswerService.deleteCourseTestAnswer(id);
    }
    @PostMapping("/deleteCourseTestAnswerByCondition")
    @ApiOperation(value = "根据条件删除课堂检测资源对应的答案")
    public void deleteCourseTestAnswerByCondition(
            @ApiParam(value = "课堂检测资源对应的答案对象")
            @RequestBody CourseTestAnswer courseTestAnswer){
        courseTestAnswerService.deleteCourseTestAnswerByCondition(courseTestAnswer);
    }
    @PostMapping("/findOneCourseTestAnswerByCondition")
    @ApiOperation(value = "根据条件查找单个课堂检测资源对应的答案,结果必须为单条数据", notes = "返回单个课堂检测资源对应的答案,没有时为空")
    public CourseTestAnswer findOneCourseTestAnswerByCondition(
            @ApiParam(value = "课堂检测资源对应的答案对象")
            @RequestBody CourseTestAnswer courseTestAnswer){
        return courseTestAnswerService.findOneCourseTestAnswerByCondition(courseTestAnswer);
    }
}
