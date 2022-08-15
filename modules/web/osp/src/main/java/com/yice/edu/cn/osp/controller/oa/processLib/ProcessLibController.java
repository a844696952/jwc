package com.yice.edu.cn.osp.controller.oa.processLib;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.ProcessLib;
import com.yice.edu.cn.osp.service.oa.processLib.ProcessLibService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/processLib")
@Api(value = "/processLib",description = "流程库模块")
public class ProcessLibController {
    @Autowired
    private ProcessLibService processLibService;

    @PostMapping("/saveProcessLib")
    @ApiOperation(value = "保存流程库对象", notes = "返回响应对象")
    public ResponseJson saveProcessLib(
            @ApiParam(value = "流程库对象", required = true)
            @RequestBody ProcessLib processLib){
        ProcessLib s=processLibService.saveProcessLib(processLib);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findProcessLibById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找流程库", notes = "返回响应对象")
    public ResponseJson findProcessLibById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ProcessLib processLib=processLibService.findProcessLibById(id);
        return new ResponseJson(processLib);
    }

    @PostMapping("/update/updateProcessLib")
    @ApiOperation(value = "修改流程库对象", notes = "返回响应对象")
    public ResponseJson updateProcessLib(
            @ApiParam(value = "被修改的流程库对象,对象属性不为空则修改", required = true)
            @RequestBody ProcessLib processLib){
        processLibService.updateProcessLib(processLib);
        return new ResponseJson();
    }

    @GetMapping("/look/lookProcessLibById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找流程库", notes = "返回响应对象")
    public ResponseJson lookProcessLibById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ProcessLib processLib=processLibService.findProcessLibById(id);
        return new ResponseJson(processLib);
    }

    @PostMapping("/findProcessLibsByCondition")
    @ApiOperation(value = "根据条件查找流程库", notes = "返回响应对象")
    public ResponseJson findProcessLibsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ProcessLib processLib){
        List<ProcessLib> data=processLibService.findProcessLibListByCondition(processLib);
        long count=processLibService.findProcessLibCountByCondition(processLib);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneProcessLibByCondition")
    @ApiOperation(value = "根据条件查找单个流程库,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneProcessLibByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ProcessLib processLib){
        ProcessLib one=processLibService.findOneProcessLibByCondition(processLib);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteProcessLib/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteProcessLib(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        processLibService.deleteProcessLib(id);
        return new ResponseJson();
    }


    @PostMapping("/findProcessLibListByCondition")
    @ApiOperation(value = "根据条件查找流程库列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findProcessLibListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ProcessLib processLib){
        List<ProcessLib> data=processLibService.findProcessLibListByCondition(processLib);
        return new ResponseJson(data);
    }



}
