package com.yice.edu.cn.osp.controller.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutStartTime;
import com.yice.edu.cn.osp.service.xw.stuInAndOut.StuInAndOutStartTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/stuInAndOutStartTime")
@Api(value = "/stuInAndOutStartTime", description = "模块")
public class StuInAndOutStartTimeController {
    @Autowired
    private StuInAndOutStartTimeService stuInAndOutStartTimeService;

    @PostMapping("/saveStuInAndOutStartTime")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = StuInAndOutStartTime.class)
    public ResponseJson saveStuInAndOutStartTime(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime) {
        stuInAndOutStartTime.setSchoolId(mySchoolId());
        ResponseJson responseJson = stuInAndOutStartTimeService.saveStuInAndOutStartTime(stuInAndOutStartTime);
        return responseJson;
    }

    @GetMapping("/findStuInAndOutStartTimeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = StuInAndOutStartTime.class)
    public ResponseJson findStuInAndOutStartTimeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        StuInAndOutStartTime stuInAndOutStartTime = stuInAndOutStartTimeService.findStuInAndOutStartTimeById(id);
        return new ResponseJson(stuInAndOutStartTime);
    }

    @PostMapping("/updateStuInAndOutStartTime")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateStuInAndOutStartTime(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime) {
        ResponseJson responseJson = stuInAndOutStartTimeService.updateStuInAndOutStartTime(stuInAndOutStartTime);
        return responseJson;
    }

    @PostMapping("/updateStuInAndOutStartTimeForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateStuInAndOutStartTimeForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime) {
        stuInAndOutStartTimeService.updateStuInAndOutStartTimeForAll(stuInAndOutStartTime);
        return new ResponseJson();
    }


    @PostMapping("/findStuInAndOutStartTimesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = StuInAndOutStartTime.class)
    public ResponseJson findStuInAndOutStartTimesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime) {
        stuInAndOutStartTime.setSchoolId(mySchoolId());
        List<StuInAndOutStartTime> data = stuInAndOutStartTimeService.findStuInAndOutStartTimeListByCondition(stuInAndOutStartTime);
        long count = stuInAndOutStartTimeService.findStuInAndOutStartTimeCountByCondition(stuInAndOutStartTime);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneStuInAndOutStartTimeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = StuInAndOutStartTime.class)
    public ResponseJson findOneStuInAndOutStartTimeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime) {
        StuInAndOutStartTime one = stuInAndOutStartTimeService.findOneStuInAndOutStartTimeByCondition(stuInAndOutStartTime);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteStuInAndOutStartTime/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStuInAndOutStartTime(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        stuInAndOutStartTimeService.deleteStuInAndOutStartTime(id);
        return new ResponseJson();
    }


    @PostMapping("/findStuInAndOutStartTimeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = StuInAndOutStartTime.class)
    public ResponseJson findStuInAndOutStartTimeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime) {
        stuInAndOutStartTime.setSchoolId(mySchoolId());
        List<StuInAndOutStartTime> data = stuInAndOutStartTimeService.findStuInAndOutStartTimeListByCondition(stuInAndOutStartTime);
        return new ResponseJson(data);
    }


}
