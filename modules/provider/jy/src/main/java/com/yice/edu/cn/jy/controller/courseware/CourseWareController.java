package com.yice.edu.cn.jy.controller.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseWare;
import com.yice.edu.cn.jy.service.courseware.CourseWareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseWare")
@Api(value = "/courseWare",description = "课件表模块")
public class CourseWareController {
    @Autowired
    private CourseWareService courseWareService;

    @GetMapping("/findCourseWareById/{id}")
    @ApiOperation(value = "通过id查找课件表", notes = "返回课件表对象")
    public CourseWare findCourseWareById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return courseWareService.findCourseWareById(id);
    }

    @PostMapping("/saveCourseWare")
    @ApiOperation(value = "保存课件表", notes = "返回课件表对象")
    public CourseWare saveCourseWare(
            @ApiParam(value = "课件表对象", required = true)
            @RequestBody CourseWare courseWare){
        courseWareService.saveCourseWare(courseWare);
        return courseWare;
    }

    @PostMapping("/findCourseWareListByCondition")
    @ApiOperation(value = "根据条件查找课件表列表", notes = "返回课件表列表")
    public List<CourseWare> findCourseWareListByCondition(
            @ApiParam(value = "课件表对象")
            @RequestBody CourseWare courseWare){
        return courseWareService.findCourseWareListByCondition(courseWare);
    }
    @PostMapping("/findCourseWareCountByCondition")
    @ApiOperation(value = "根据条件查找课件表列表个数", notes = "返回课件表总个数")
    public long findCourseWareCountByCondition(
            @ApiParam(value = "课件表对象")
            @RequestBody CourseWare courseWare){
        return courseWareService.findCourseWareCountByCondition(courseWare);
    }

    @PostMapping("/updateCourseWare")
    @ApiOperation(value = "修改课件表", notes = "课件表对象必传")
    public void updateCourseWare(
            @ApiParam(value = "课件表对象,对象属性不为空则修改", required = true)
            @RequestBody CourseWare courseWare){
        courseWareService.updateCourseWare(courseWare);
    }

    @GetMapping("/deleteCourseWare/{id}")
    @ApiOperation(value = "通过id删除课件表")
    public void deleteCourseWare(
            @ApiParam(value = "课件表对象", required = true)
            @PathVariable String id){
        courseWareService.deleteCourseWare(id);
    }
    @PostMapping("/deleteCourseWareByCondition")
    @ApiOperation(value = "根据条件删除课件表")
    public void deleteCourseWareByCondition(
            @ApiParam(value = "课件表对象")
            @RequestBody CourseWare courseWare){
        courseWareService.deleteCourseWareByCondition(courseWare);
    }
    @PostMapping("/findOneCourseWareByCondition")
    @ApiOperation(value = "根据条件查找单个课件表,结果必须为单条数据", notes = "返回单个课件表,没有时为空")
    public CourseWare findOneCourseWareByCondition(
            @ApiParam(value = "课件表对象")
            @RequestBody CourseWare courseWare){
        return courseWareService.findOneCourseWareByCondition(courseWare);
    }

    @PostMapping("/deleteCourseWareByIds")
    @ApiOperation(value = "通过ids批量删除课件表")
    public void deleteCourseWareByIds(
            @ApiParam(value = "课件表对象", required = true)
            @RequestBody List<String> ids){
        courseWareService.deleteCourseWareByIds(ids);
    }

    @PostMapping("/batchUpdateCourseWare")
    @ApiOperation(value = "批量移动课件", notes = "课件表对象必传")
    public void batchUpdateCourseWare(
            @ApiParam(value = "课件表对象,对象属性不为空则修改", required = true)
            @RequestBody CourseWare courseWare){
        courseWareService.batchUpdateCourseWare(courseWare);
    }

    @PostMapping("/findRecentlyCourseWare")
    @ApiOperation(value = "查询最近的一个课件", notes = "返回单个课件表,没有时为空")
    public CourseWare findRecentlyCourseWare(@RequestBody CourseWare courseWare){
        return courseWareService.findRecentlyCourseWare(courseWare);
    }
}
