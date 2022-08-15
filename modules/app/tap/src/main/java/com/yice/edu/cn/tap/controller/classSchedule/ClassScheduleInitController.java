package com.yice.edu.cn.tap.controller.classSchedule;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.classSchedule.ClassScheduleInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classScheduleInit")
@Api(value = "/classScheduleInit",description = "模块")
public class ClassScheduleInitController {
    @Autowired
    private ClassScheduleInitService classScheduleInitService;

    @PostMapping("/saveClassScheduleInit")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=ClassScheduleInit.class)
    public ResponseJson saveClassScheduleInit(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClassScheduleInit classScheduleInit){
        ClassScheduleInit s=classScheduleInitService.saveClassScheduleInit(classScheduleInit);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findClassScheduleInitById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=ClassScheduleInit.class)
    public ResponseJson findClassScheduleInitById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ClassScheduleInit classScheduleInit=classScheduleInitService.findClassScheduleInitById(id);
        return new ResponseJson(classScheduleInit);
    }

    @PostMapping("/update/updateClassScheduleInit")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateClassScheduleInit(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody ClassScheduleInit classScheduleInit){
        classScheduleInitService.updateClassScheduleInit(classScheduleInit);
        return new ResponseJson();
    }

    @PostMapping("/update/updateClassScheduleInitForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateClassScheduleInitForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody ClassScheduleInit classScheduleInit){
        classScheduleInitService.updateClassScheduleInitForAll(classScheduleInit);
        return new ResponseJson();
    }

    @GetMapping("/look/lookClassScheduleInitById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=ClassScheduleInit.class)
    public ResponseJson lookClassScheduleInitById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ClassScheduleInit classScheduleInit=classScheduleInitService.findClassScheduleInitById(id);
        return new ResponseJson(classScheduleInit);
    }

    @PostMapping("/findClassScheduleInitsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=ClassScheduleInit.class)
    public ResponseJson findClassScheduleInitsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClassScheduleInit classScheduleInit){
        List<ClassScheduleInit> data=classScheduleInitService.findClassScheduleInitListByCondition(classScheduleInit);
        long count=classScheduleInitService.findClassScheduleInitCountByCondition(classScheduleInit);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneClassScheduleInitByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=ClassScheduleInit.class)
    public ResponseJson findOneClassScheduleInitByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ClassScheduleInit classScheduleInit){
        ClassScheduleInit one=classScheduleInitService.findOneClassScheduleInitByCondition(classScheduleInit);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteClassScheduleInit/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteClassScheduleInit(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        classScheduleInitService.deleteClassScheduleInit(id);
        return new ResponseJson();
    }


    @PostMapping("/findClassScheduleInitListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=ClassScheduleInit.class)
    public ResponseJson findClassScheduleInitListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClassScheduleInit classScheduleInit){
        List<ClassScheduleInit> data=classScheduleInitService.findClassScheduleInitListByCondition(classScheduleInit);
        return new ResponseJson(data);
    }


}
