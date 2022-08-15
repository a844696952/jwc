package com.yice.edu.cn.jy.controller.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseRes;
import com.yice.edu.cn.jy.service.courseware.CourseResService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseRes")
@Api(value = "/courseRes",description = "课件资源表模块")
public class CourseResController {
    @Autowired
    private CourseResService courseResService;

    @GetMapping("/findCourseResById/{id}")
    @ApiOperation(value = "通过id查找课件资源表", notes = "返回课件资源表对象")
    public CourseRes findCourseResById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return courseResService.findCourseResById(id);
    }

    @PostMapping("/saveCourseRes")
    @ApiOperation(value = "保存课件资源表", notes = "返回课件资源表对象")
    public CourseRes saveCourseRes(
            @ApiParam(value = "课件资源表对象", required = true)
            @RequestBody CourseRes courseRes){
        courseResService.saveCourseRes(courseRes);
        return courseRes;
    }

    @PostMapping("/findCourseResListByCondition")
    @ApiOperation(value = "根据条件查找课件资源表列表", notes = "返回课件资源表列表")
    public List<CourseRes> findCourseResListByCondition(
            @ApiParam(value = "课件资源表对象")
            @RequestBody CourseRes courseRes){
        return courseResService.findCourseResListByCondition(courseRes);
    }
    @PostMapping("/findCourseResCountByCondition")
    @ApiOperation(value = "根据条件查找课件资源表列表个数", notes = "返回课件资源表总个数")
    public long findCourseResCountByCondition(
            @ApiParam(value = "课件资源表对象")
            @RequestBody CourseRes courseRes){
        return courseResService.findCourseResCountByCondition(courseRes);
    }

    @PostMapping("/updateCourseRes")
    @ApiOperation(value = "修改课件资源表", notes = "课件资源表对象必传")
    public void updateCourseRes(
            @ApiParam(value = "课件资源表对象,对象属性不为空则修改", required = true)
            @RequestBody CourseRes courseRes){
        courseResService.updateCourseRes(courseRes);
    }

    @GetMapping("/deleteCourseRes/{id}")
    @ApiOperation(value = "通过id删除课件资源表")
    public void deleteCourseRes(
            @ApiParam(value = "课件资源表对象", required = true)
            @PathVariable String id){
        courseResService.deleteCourseRes(id);
    }
    @PostMapping("/deleteCourseResByCondition")
    @ApiOperation(value = "根据条件删除课件资源表")
    public void deleteCourseResByCondition(
            @ApiParam(value = "课件资源表对象")
            @RequestBody CourseRes courseRes){
        courseResService.deleteCourseResByCondition(courseRes);
    }
    @PostMapping("/findOneCourseResByCondition")
    @ApiOperation(value = "根据条件查找单个课件资源表,结果必须为单条数据", notes = "返回单个课件资源表,没有时为空")
    public CourseRes findOneCourseResByCondition(
            @ApiParam(value = "课件资源表对象")
            @RequestBody CourseRes courseRes){
        return courseResService.findOneCourseResByCondition(courseRes);
    }

    @ApiOperation(value = "单个资源重命名", notes = "返回单个课件资源表,没有时为空")
    @PostMapping("/rename")
    public CourseRes rename(
            @ApiParam(value = "资源对象")
            @RequestBody CourseRes courseRes
    ){
        return courseResService.rename(courseRes);
    }

    @ApiOperation(value = "单个资源改备注", notes = "返回单个课件资源表,没有时为空")
    @PostMapping("/remark")
    public void remark(
            @ApiParam(value = "资源对象")
            @RequestBody CourseRes courseRes
    ){
        courseResService.remark(courseRes);
    }

    @ApiOperation(value = "单个资源移动", notes = "返回单个课件资源表,没有时为空")
    @PostMapping("/mv")
    public CourseRes mv(
            @ApiParam(value = "移动对象")
            @RequestBody CourseRes courseRes
    ){
        return courseResService.mv(courseRes);
    }

    @ApiOperation(value = "批量资源移动")
    @PostMapping("/mvs")
    public void mvs(
            @ApiParam(value = "移动对象")
            @RequestBody CourseRes courseRes
    ){
        courseResService.mvs(courseRes);
    }

    @ApiOperation(value = "批量资源删除")
    @PostMapping("/deletes")
    public void deletes(
            @ApiParam(value = "批量资源删除")
            @RequestBody List<String> ids
    ){
        courseResService.deletes(ids);
    }

    @ApiOperation(value = "新增课堂检测")
    @PostMapping("/saveTestCourseRes")
    public CourseRes saveTestCourseRes(
            @ApiParam(value = "课堂检测对象")
            @RequestBody CourseRes courseRes
    ){
        courseResService.saveTestCourseRes(courseRes);
        return courseRes;
    }


    @ApiOperation(value = "查询最新的备课资源")
    @PostMapping("/findLastRes")
    public CourseRes findLastRes(
            @ApiParam(value = "课堂检测对象")
            @RequestBody CourseRes courseRes
    ){
        CourseRes result = courseResService.findLastRes(courseRes);
        return result;
    }



}
