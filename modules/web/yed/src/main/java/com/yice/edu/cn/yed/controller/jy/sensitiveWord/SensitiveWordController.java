package com.yice.edu.cn.yed.controller.jy.sensitiveWord;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.sensitiveWord.SensitiveWord;
import com.yice.edu.cn.yed.service.jy.SensitiveWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensitiveWord")
@Api(value = "/sensitiveWord",description = "模块")
public class SensitiveWordController {
    @Autowired
    private SensitiveWordService sensitiveWordService;

    @PostMapping("/saveSensitiveWord")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveSensitiveWord(
            @ApiParam(value = "对象", required = true)
            @RequestBody SensitiveWord sensitiveWord){
        SensitiveWord s=sensitiveWordService.saveSensitiveWord(sensitiveWord);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSensitiveWordById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findSensitiveWordById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SensitiveWord sensitiveWord=sensitiveWordService.findSensitiveWordById(id);
        return new ResponseJson(sensitiveWord);
    }

    @PostMapping("/update/updateSensitiveWord")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateSensitiveWord(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody SensitiveWord sensitiveWord){
        sensitiveWordService.updateSensitiveWord(sensitiveWord);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSensitiveWordById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookSensitiveWordById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SensitiveWord sensitiveWord=sensitiveWordService.findSensitiveWordById(id);
        return new ResponseJson(sensitiveWord);
    }

    @PostMapping("/findSensitiveWordsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findSensitiveWordsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SensitiveWord sensitiveWord){
        List<SensitiveWord> data=sensitiveWordService.findSensitiveWordListByCondition(sensitiveWord);
        long count=sensitiveWordService.findSensitiveWordCountByCondition(sensitiveWord);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSensitiveWordByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneSensitiveWordByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SensitiveWord sensitiveWord){
        SensitiveWord one=sensitiveWordService.findOneSensitiveWordByCondition(sensitiveWord);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteSensitiveWord/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSensitiveWord(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        sensitiveWordService.deleteSensitiveWord(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteSensitiveWordByCondition")
    @ApiOperation(value = "根据条件删除", notes = "返回响应对象")
    public ResponseJson deleteSensitiveWordByCondition(
            @ApiParam(value = "被删除的对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody SensitiveWord sensitiveWord){
        sensitiveWordService.deleteSensitiveWordByCondition(sensitiveWord);
        return new ResponseJson();
    }

}
