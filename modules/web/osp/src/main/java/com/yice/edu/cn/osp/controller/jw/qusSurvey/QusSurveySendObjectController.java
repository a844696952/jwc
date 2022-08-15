package com.yice.edu.cn.osp.controller.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveySendObject;
import com.yice.edu.cn.osp.service.jw.qusSurvey.QusSurveySendObjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/qusSurveySendObject")
@Api(value = "/qusSurveySendObject",description = "选择发送对象模块")
public class QusSurveySendObjectController {
    @Autowired
    private QusSurveySendObjectService qusSurveySendObjectService;

    @PostMapping("/saveQusSurveySendObject")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveQusSurveySendObject(
            @ApiParam(value = "对象", required = true)
            @RequestBody QusSurveySendObject qusSurveySendObject){
        QusSurveySendObject s=qusSurveySendObjectService.saveQusSurveySendObject(qusSurveySendObject);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findQusSurveySendObjectById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findQusSurveySendObjectById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        QusSurveySendObject qusSurveySendObject=qusSurveySendObjectService.findQusSurveySendObjectById(id);
        return new ResponseJson(qusSurveySendObject);
    }

    @PostMapping("/update/updateQusSurveySendObject")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateQusSurveySendObject(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurveySendObject qusSurveySendObject){
        qusSurveySendObjectService.updateQusSurveySendObject(qusSurveySendObject);
        return new ResponseJson();
    }

    @GetMapping("/look/lookQusSurveySendObjectById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookQusSurveySendObjectById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        QusSurveySendObject qusSurveySendObject=qusSurveySendObjectService.findQusSurveySendObjectById(id);
        return new ResponseJson(qusSurveySendObject);
    }

    @PostMapping("/findQusSurveySendObjectsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findQusSurveySendObjectsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QusSurveySendObject qusSurveySendObject){
        List<QusSurveySendObject> data=qusSurveySendObjectService.findQusSurveySendObjectListByCondition(qusSurveySendObject);
        long count=qusSurveySendObjectService.findQusSurveySendObjectCountByCondition(qusSurveySendObject);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneQusSurveySendObjectByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneQusSurveySendObjectByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody QusSurveySendObject qusSurveySendObject){
        QusSurveySendObject one=qusSurveySendObjectService.findOneQusSurveySendObjectByCondition(qusSurveySendObject);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteQusSurveySendObject/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteQusSurveySendObject(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        qusSurveySendObjectService.deleteQusSurveySendObject(id);
        return new ResponseJson();
    }


    @PostMapping("/findQusSurveySendObjectListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findQusSurveySendObjectListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QusSurveySendObject qusSurveySendObject){
        List<QusSurveySendObject> data=qusSurveySendObjectService.findQusSurveySendObjectListByCondition(qusSurveySendObject);
        return new ResponseJson(data);
    }



}
