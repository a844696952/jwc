package com.yice.edu.cn.tap.controller.courseware;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseRes;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseWare;
import com.yice.edu.cn.tap.service.courseware.CourseWareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/courseWare")
@Api(value = "/courseWare",description = "课件表模块")
public class CourseWareController {
    @Autowired
    private CourseWareService courseWareService;

    @PostMapping("/saveCourseWare")
    @ApiOperation(value = "保存课件表对象", notes = "返回保存好的课件表对象", response=CourseWare.class)
    public ResponseJson saveCourseWare(
            @ApiParam(value = "课件表对象", required = true)
            @RequestBody CourseWare courseWare){
        courseWare.setSchoolId(mySchoolId());
        CourseWare s=courseWareService.saveCourseWare(courseWare);
        return new ResponseJson(s);
    }


    @PostMapping("/updateCourseWare")
    @ApiOperation(value = "修改课件表对象", notes = "返回响应对象")
    public ResponseJson updateCourseWare(
            @ApiParam(value = "被修改的课件表对象,对象属性不为空则修改", required = true)
            @RequestBody CourseWare courseWare){
        courseWareService.updateCourseWare(courseWare);
        return new ResponseJson();
    }

    @GetMapping("/findCourseWareById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找课件表", notes = "返回响应对象", response=CourseWare.class)
    public ResponseJson findCourseWareById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CourseWare courseWare=courseWareService.findCourseWareById(id);
        return new ResponseJson(courseWare);
    }

    @GetMapping("/deleteCourseWare/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCourseWare(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        courseWareService.deleteCourseWare(id);
        return new ResponseJson();
    }


    @PostMapping("/findCourseWareListByCondition")
    @ApiOperation(value = "根据条件查找课件表列表", notes = "返回响应对象,不包含总条数", response=CourseWare.class)
    public ResponseJson findCourseWareListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CourseWare courseWare){
       courseWare.setSchoolId(mySchoolId());
        List<CourseWare> data=courseWareService.findCourseWareListByCondition(courseWare);
        long count = courseWareService.findCourseWareCountByCondition(courseWare);
        return new ResponseJson(data,count);
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

    @PostMapping("/saveCourseWareAndRes")
    @ApiOperation(value = "完成插入课件后，将课件PPT的URL地址当成新资源存入资源表", notes = "课件表对象必传")
    public ResponseJson saveCourseWareAndRes(
            @ApiParam(value = "课件表对象,对象属性不为空则修改", required = true)
            @RequestBody CourseWare courseWare){
        CourseWare  ware = courseWareService.findCourseWareById(courseWare.getId());
         if(ware==null)
             return new ResponseJson(false,"课件不存在");
        ware.setCoursewareUrl(courseWare.getCoursewareUrl());
        ware.setCoursewareSize(courseWare.getCoursewareSize());
        CourseRes res =courseWareService.saveCourseWareAndRes(ware);
        ware.setResoucesId(res.getId());
        courseWareService.updateCourseWare(ware);
        return new ResponseJson(true);
    }

}
