package com.yice.edu.cn.osp.controller.xw.workerKq;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.workerKq.SpecialData;
import com.yice.edu.cn.osp.service.xw.workerKq.SpecialDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/specialData")
@Api(value = "/specialData",description = "考勤管理基础规则表模块")
public class SpecialDataController {
    @Autowired
    private SpecialDataService specialDataService;

    @PostMapping("/saveSpecialData")
    @ApiOperation(value = "保存考勤管理基础规则表对象", notes = "返回保存好的考勤管理基础规则表对象", response=SpecialData.class)
    public ResponseJson saveSpecialData(
            @ApiParam(value = "考勤管理基础规则表对象", required = true)
            @RequestBody SpecialData specialData){
       specialData.setSchoolId(mySchoolId());
        SpecialData s=specialDataService.saveSpecialData(specialData);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSpecialDataById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考勤管理基础规则表", notes = "返回响应对象", response=SpecialData.class)
    public ResponseJson findSpecialDataById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SpecialData specialData=specialDataService.findSpecialDataById(id);
        return new ResponseJson(specialData);
    }

    @PostMapping("/update/updateSpecialData")
    @ApiOperation(value = "修改考勤管理基础规则表对象", notes = "返回响应对象")
    public ResponseJson updateSpecialData(
            @ApiParam(value = "被修改的考勤管理基础规则表对象,对象属性不为空则修改", required = true)
            @RequestBody SpecialData specialData){
        specialDataService.updateSpecialData(specialData);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSpecialDataById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考勤管理基础规则表", notes = "返回响应对象", response=SpecialData.class)
    public ResponseJson lookSpecialDataById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SpecialData specialData=specialDataService.findSpecialDataById(id);
        return new ResponseJson(specialData);
    }

    @PostMapping("/findSpecialDatasByCondition")
    @ApiOperation(value = "根据条件查找考勤管理基础规则表", notes = "返回响应对象", response=SpecialData.class)
    public ResponseJson findSpecialDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SpecialData specialData){
       specialData.setSchoolId(mySchoolId());
        List<SpecialData> data=specialDataService.findSpecialDataListByCondition(specialData);
        long count=specialDataService.findSpecialDataCountByCondition(specialData);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSpecialDataByCondition")
    @ApiOperation(value = "根据条件查找单个考勤管理基础规则表,结果必须为单条数据", notes = "没有时返回空", response=SpecialData.class)
    public ResponseJson findOneSpecialDataByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SpecialData specialData){
        SpecialData one=specialDataService.findOneSpecialDataByCondition(specialData);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteSpecialData/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSpecialData(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        specialDataService.deleteSpecialData(id);
        return new ResponseJson();
    }


    @PostMapping("/findSpecialDataListByCondition")
    @ApiOperation(value = "根据条件查找考勤管理基础规则表列表", notes = "返回响应对象,不包含总条数", response=SpecialData.class)
    public ResponseJson findSpecialDataListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SpecialData specialData){
       specialData.setSchoolId(mySchoolId());
        List<SpecialData> data=specialDataService.findSpecialDataListByCondition(specialData);
        return new ResponseJson(data);
    }



}
