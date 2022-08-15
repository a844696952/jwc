package com.yice.edu.cn.osp.controller.xw.routineDutyFeedback;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.routineDutyFeedback.RoutineDutyFeedback;
import com.yice.edu.cn.osp.service.xw.routineDutyFeedback.RoutineDutyFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/routineDutyFeedback")
@Api(value = "/routineDutyFeedback",description = "常规值班反馈表模块")
public class RoutineDutyFeedbackController {
    @Autowired
    private RoutineDutyFeedbackService routineDutyFeedbackService;

    @PostMapping("/saveRoutineDutyFeedback")
    @ApiOperation(value = "保存常规值班反馈表对象", notes = "返回保存好的常规值班反馈表对象", response=RoutineDutyFeedback.class)
    public ResponseJson saveRoutineDutyFeedback(
            @ApiParam(value = "常规值班反馈表对象", required = true)
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
       routineDutyFeedback.setSchoolId(mySchoolId());
        RoutineDutyFeedback s=routineDutyFeedbackService.saveRoutineDutyFeedback(routineDutyFeedback);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findRoutineDutyFeedbackById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找常规值班反馈表", notes = "返回响应对象", response=RoutineDutyFeedback.class)
    public ResponseJson findRoutineDutyFeedbackById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        RoutineDutyFeedback routineDutyFeedback=routineDutyFeedbackService.findRoutineDutyFeedbackById(id);
        return new ResponseJson(routineDutyFeedback);
    }

    @PostMapping("/update/updateRoutineDutyFeedback")
    @ApiOperation(value = "修改常规值班反馈表对象", notes = "返回响应对象")
    public ResponseJson updateRoutineDutyFeedback(
            @ApiParam(value = "被修改的常规值班反馈表对象,对象属性不为空则修改", required = true)
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
        routineDutyFeedbackService.updateRoutineDutyFeedback(routineDutyFeedback);
        return new ResponseJson();
    }

    @GetMapping("/look/lookRoutineDutyFeedbackById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找常规值班反馈表", notes = "返回响应对象", response=RoutineDutyFeedback.class)
    public ResponseJson lookRoutineDutyFeedbackById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        RoutineDutyFeedback routineDutyFeedback=routineDutyFeedbackService.findRoutineDutyFeedbackById(id);
        return new ResponseJson(routineDutyFeedback);
    }

    @PostMapping("/findRoutineDutyFeedbacksByCondition")
    @ApiOperation(value = "根据条件查找常规值班反馈表", notes = "返回响应对象", response=RoutineDutyFeedback.class)
    public ResponseJson findRoutineDutyFeedbacksByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
       routineDutyFeedback.setSchoolId(mySchoolId());
        List<RoutineDutyFeedback> data=routineDutyFeedbackService.findRoutineDutyFeedbackListByCondition(routineDutyFeedback);
        long count=routineDutyFeedbackService.findRoutineDutyFeedbackCountByCondition(routineDutyFeedback);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneRoutineDutyFeedbackByCondition")
    @ApiOperation(value = "根据条件查找单个常规值班反馈表,结果必须为单条数据", notes = "没有时返回空", response=RoutineDutyFeedback.class)
    public ResponseJson findOneRoutineDutyFeedbackByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
        RoutineDutyFeedback one=routineDutyFeedbackService.findOneRoutineDutyFeedbackByCondition(routineDutyFeedback);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteRoutineDutyFeedback/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteRoutineDutyFeedback(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        routineDutyFeedbackService.deleteRoutineDutyFeedback(id);
        return new ResponseJson();
    }


    @PostMapping("/findRoutineDutyFeedbackListByCondition")
    @ApiOperation(value = "根据条件查找常规值班反馈表列表", notes = "返回响应对象,不包含总条数", response=RoutineDutyFeedback.class)
    public ResponseJson findRoutineDutyFeedbackListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
       routineDutyFeedback.setSchoolId(mySchoolId());
        List<RoutineDutyFeedback> data=routineDutyFeedbackService.findRoutineDutyFeedbackListByCondition(routineDutyFeedback);
        return new ResponseJson(data);
    }



}
