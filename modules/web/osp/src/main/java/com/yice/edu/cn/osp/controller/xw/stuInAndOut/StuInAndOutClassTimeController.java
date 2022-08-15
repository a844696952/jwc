package com.yice.edu.cn.osp.controller.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutClassTime;
import com.yice.edu.cn.osp.service.xw.stuInAndOut.StuInAndOutClassTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/stuInAndOutClassTime")
@Api(value = "/stuInAndOutClassTime", description = "模块")
public class StuInAndOutClassTimeController {
    @Autowired
    private StuInAndOutClassTimeService stuInAndOutClassTimeService;

    @PostMapping("/saveStuInAndOutClassTime")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = StuInAndOutClassTime.class)
    public ResponseJson saveStuInAndOutClassTime(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime) {
        stuInAndOutClassTime.setSchoolId(mySchoolId());
        ResponseJson responseJson = stuInAndOutClassTimeService.saveStuInAndOutClassTime(stuInAndOutClassTime);
        return responseJson;
    }

    @GetMapping("/findStuInAndOutClassTimeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = StuInAndOutClassTime.class)
    public ResponseJson findStuInAndOutClassTimeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        StuInAndOutClassTime stuInAndOutClassTime = stuInAndOutClassTimeService.findStuInAndOutClassTimeById(id);
        return new ResponseJson(stuInAndOutClassTime);
    }

    @PostMapping("/updateStuInAndOutClassTime")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateStuInAndOutClassTime(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime) {
        stuInAndOutClassTimeService.deleteStuInAndOutClassTime(stuInAndOutClassTime.getId());
        stuInAndOutClassTime.setSchoolId(mySchoolId());
        ResponseJson responseJson = stuInAndOutClassTimeService.saveStuInAndOutClassTime(stuInAndOutClassTime);
        return responseJson;
    }

    @PostMapping("/updateStuInAndOutClassTimeForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateStuInAndOutClassTimeForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime) {
        stuInAndOutClassTimeService.updateStuInAndOutClassTimeForAll(stuInAndOutClassTime);
        return new ResponseJson();
    }


    @PostMapping("/findStuInAndOutClassTimesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = StuInAndOutClassTime.class)
    public ResponseJson findStuInAndOutClassTimesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime) {
        stuInAndOutClassTime.setSchoolId(mySchoolId());
        List<StuInAndOutClassTime> data = stuInAndOutClassTimeService.findStuInAndOutClassTimeListByCondition(stuInAndOutClassTime);
        long count = stuInAndOutClassTimeService.findStuInAndOutClassTimeCountByCondition(stuInAndOutClassTime);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneStuInAndOutClassTimeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = StuInAndOutClassTime.class)
    public ResponseJson findOneStuInAndOutClassTimeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime) {
        StuInAndOutClassTime one = stuInAndOutClassTimeService.findOneStuInAndOutClassTimeByCondition(stuInAndOutClassTime);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteStuInAndOutClassTime/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStuInAndOutClassTime(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        stuInAndOutClassTimeService.deleteStuInAndOutClassTime(id);
        return new ResponseJson();
    }


    @PostMapping("/findStuInAndOutClassTimeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = StuInAndOutClassTime.class)
    public ResponseJson findStuInAndOutClassTimeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime) {
        stuInAndOutClassTime.setSchoolId(mySchoolId());
        List<StuInAndOutClassTime> data = stuInAndOutClassTimeService.findStuInAndOutClassTimeListByCondition(stuInAndOutClassTime);
        return new ResponseJson(data);
    }


}
