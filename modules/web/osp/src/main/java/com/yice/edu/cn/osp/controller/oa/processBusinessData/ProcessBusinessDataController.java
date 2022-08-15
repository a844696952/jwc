package com.yice.edu.cn.osp.controller.oa.processBusinessData;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.osp.service.oa.processBusinessData.ProcessBusinessDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/processBusinessData")
@Api(value = "/processBusinessData",description = "流程业务数据模块")
public class ProcessBusinessDataController {
    @Autowired
    private ProcessBusinessDataService processBusinessDataService;

    @PostMapping("/saveProcessBusinessData")
    @ApiOperation(value = "保存流程业务数据对象", notes = "返回响应对象")
    public ResponseJson saveProcessBusinessData(
            @ApiParam(value = "流程业务数据对象", required = true)
            @RequestBody ProcessBusinessData processBusinessData){
       processBusinessData.setSchoolId(mySchoolId());
        ProcessBusinessData s=processBusinessDataService.saveProcessBusinessData(processBusinessData);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findProcessBusinessDataById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找流程业务数据", notes = "返回响应对象")
    public ResponseJson findProcessBusinessDataById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ProcessBusinessData processBusinessData=processBusinessDataService.findProcessBusinessDataById(id);
        return new ResponseJson(processBusinessData);
    }

    @PostMapping("/update/updateProcessBusinessData")
    @ApiOperation(value = "修改流程业务数据对象", notes = "返回响应对象")
    public ResponseJson updateProcessBusinessData(
            @ApiParam(value = "被修改的流程业务数据对象,对象属性不为空则修改", required = true)
            @RequestBody ProcessBusinessData processBusinessData){
        processBusinessDataService.updateProcessBusinessData(processBusinessData);
        return new ResponseJson();
    }

    @GetMapping("/look/lookProcessBusinessDataById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找流程业务数据", notes = "返回响应对象")
    public ResponseJson lookProcessBusinessDataById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ProcessBusinessData processBusinessData=processBusinessDataService.findProcessBusinessDataById(id);
        return new ResponseJson(processBusinessData);
    }

    @PostMapping("/findProcessBusinessDatasByCondition")
    @ApiOperation(value = "根据条件查找流程业务数据", notes = "返回响应对象")
    public ResponseJson findProcessBusinessDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ProcessBusinessData processBusinessData){
       processBusinessData.setSchoolId(mySchoolId());
        List<ProcessBusinessData> data=processBusinessDataService.findProcessBusinessDataListByCondition(processBusinessData);
        long count=processBusinessDataService.findProcessBusinessDataCountByCondition(processBusinessData);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneProcessBusinessDataByCondition")
    @ApiOperation(value = "根据条件查找单个流程业务数据,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneProcessBusinessDataByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ProcessBusinessData processBusinessData){
        ProcessBusinessData one=processBusinessDataService.findOneProcessBusinessDataByCondition(processBusinessData);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteProcessBusinessData/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteProcessBusinessData(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        processBusinessDataService.deleteProcessBusinessData(id);
        return new ResponseJson();
    }


    @PostMapping("/findProcessBusinessDataListByCondition")
    @ApiOperation(value = "根据条件查找流程业务数据列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findProcessBusinessDataListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ProcessBusinessData processBusinessData){
       processBusinessData.setSchoolId(mySchoolId());
        List<ProcessBusinessData> data=processBusinessDataService.findProcessBusinessDataListByCondition(processBusinessData);
        return new ResponseJson(data);
    }


}
