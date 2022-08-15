package com.yice.edu.cn.osp.controller.jw.stuEvaluate;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateSendObject;
import com.yice.edu.cn.osp.service.jw.stuEvaluate.StuEvaluateSendObjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/stuEvaluateSendObject")
@Api(value = "/stuEvaluateSendObject",description = "发送对象模块")
public class StuEvaluateSendObjectController {
    @Autowired
    private StuEvaluateSendObjectService stuEvaluateSendObjectService;

    @PostMapping("/saveStuEvaluateSendObject")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveStuEvaluateSendObject(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        StuEvaluateSendObject s=stuEvaluateSendObjectService.saveStuEvaluateSendObject(stuEvaluateSendObject);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findStuEvaluateSendObjectById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findStuEvaluateSendObjectById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        StuEvaluateSendObject stuEvaluateSendObject=stuEvaluateSendObjectService.findStuEvaluateSendObjectById(id);
        return new ResponseJson(stuEvaluateSendObject);
    }

    @PostMapping("/update/updateStuEvaluateSendObject")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateStuEvaluateSendObject(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        stuEvaluateSendObjectService.updateStuEvaluateSendObject(stuEvaluateSendObject);
        return new ResponseJson();
    }

    @GetMapping("/look/lookStuEvaluateSendObjectById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookStuEvaluateSendObjectById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        StuEvaluateSendObject stuEvaluateSendObject=stuEvaluateSendObjectService.findStuEvaluateSendObjectById(id);
        return new ResponseJson(stuEvaluateSendObject);
    }

    @PostMapping("/findStuEvaluateSendObjectsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findStuEvaluateSendObjectsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        List<StuEvaluateSendObject> data=stuEvaluateSendObjectService.findStuEvaluateSendObjectListByCondition(stuEvaluateSendObject);
        long count=stuEvaluateSendObjectService.findStuEvaluateSendObjectCountByCondition(stuEvaluateSendObject);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneStuEvaluateSendObjectByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneStuEvaluateSendObjectByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        StuEvaluateSendObject one=stuEvaluateSendObjectService.findOneStuEvaluateSendObjectByCondition(stuEvaluateSendObject);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteStuEvaluateSendObject/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStuEvaluateSendObject(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        stuEvaluateSendObjectService.deleteStuEvaluateSendObject(id);
        return new ResponseJson();
    }


    @PostMapping("/findStuEvaluateSendObjectListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findStuEvaluateSendObjectListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        List<StuEvaluateSendObject> data=stuEvaluateSendObjectService.findStuEvaluateSendObjectListByCondition(stuEvaluateSendObject);
        return new ResponseJson(data);
    }



}
