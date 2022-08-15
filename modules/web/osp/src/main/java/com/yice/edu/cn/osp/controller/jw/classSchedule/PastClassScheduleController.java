package com.yice.edu.cn.osp.controller.jw.classSchedule;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.PastClassSchedule;
import com.yice.edu.cn.osp.service.jw.classSchedule.PastClassScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/pastClassSchedule")
@Api(value = "/pastClassSchedule",description = "往期课表记录表模块")
public class PastClassScheduleController {
    @Autowired
    private PastClassScheduleService pastClassScheduleService;
    @PostMapping("/savePastClassSchedule")
    @ApiOperation(value = "保存往期课表记录表对象", notes = "返回保存好的往期课表记录表对象", response=PastClassSchedule.class)
    public ResponseJson savePastClassSchedule(
            @ApiParam(value = "往期课表记录表对象", required = true)
            @RequestBody PastClassSchedule pastClassSchedule){
       pastClassSchedule.setSchoolId(mySchoolId());
        pastClassScheduleService.savePastClassSchedule(pastClassSchedule);
        return new ResponseJson();
    }

    @GetMapping("/findPastClassScheduleById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找往期课表记录表", notes = "返回响应对象", response=PastClassSchedule.class)
    public ResponseJson findPastClassScheduleById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PastClassSchedule pastClassSchedule=pastClassScheduleService.findPastClassScheduleById(id);
        return new ResponseJson(pastClassSchedule);
    }

    @PostMapping("/updatePastClassSchedule")
    @ApiOperation(value = "修改往期课表记录表对象非空字段", notes = "返回响应对象")
    public ResponseJson updatePastClassSchedule(
            @ApiParam(value = "被修改的往期课表记录表对象,对象属性不为空则修改", required = true)
            @RequestBody PastClassSchedule pastClassSchedule){
        pastClassScheduleService.updatePastClassSchedule(pastClassSchedule);
        return new ResponseJson();
    }

    @PostMapping("/updatePastClassScheduleForAll")
    @ApiOperation(value = "修改往期课表记录表对象所有字段", notes = "返回响应对象")
    public ResponseJson updatePastClassScheduleForAll(
            @ApiParam(value = "被修改的往期课表记录表对象", required = true)
            @RequestBody PastClassSchedule pastClassSchedule){
        pastClassScheduleService.updatePastClassScheduleForAll(pastClassSchedule);
        return new ResponseJson();
    }


    @PostMapping("/findPastClassSchedulesByCondition")
    @ApiOperation(value = "根据条件查找往期课表记录表", notes = "返回响应对象", response=PastClassSchedule.class)
    public ResponseJson findPastClassSchedulesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PastClassSchedule pastClassSchedule){
       pastClassSchedule.setSchoolId(mySchoolId());
        List<PastClassSchedule> data=pastClassScheduleService.findPastClassScheduleListByCondition(pastClassSchedule);
        long count=pastClassScheduleService.findPastClassScheduleCountByCondition(pastClassSchedule);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePastClassScheduleByCondition")
    @ApiOperation(value = "根据条件查找单个往期课表记录表,结果必须为单条数据", notes = "没有时返回空", response=PastClassSchedule.class)
    public ResponseJson findOnePastClassScheduleByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PastClassSchedule pastClassSchedule){
        PastClassSchedule one=pastClassScheduleService.findOnePastClassScheduleByCondition(pastClassSchedule);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePastClassSchedule/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePastClassSchedule(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        pastClassScheduleService.deletePastClassSchedule(id);
        return new ResponseJson();
    }


    @PostMapping("/findPastClassScheduleListByCondition")
    @ApiOperation(value = "根据条件查找往期课表记录表列表", notes = "返回响应对象,不包含总条数", response=PastClassSchedule.class)
    public ResponseJson findPastClassScheduleListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PastClassSchedule pastClassSchedule){
       pastClassSchedule.setSchoolId(mySchoolId());
        List<PastClassSchedule> data=pastClassScheduleService.findPastClassScheduleListByCondition(pastClassSchedule);
        return new ResponseJson(data);
    }



}
