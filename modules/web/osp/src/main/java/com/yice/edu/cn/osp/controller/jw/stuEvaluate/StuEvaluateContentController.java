package com.yice.edu.cn.osp.controller.jw.stuEvaluate;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContent;
import com.yice.edu.cn.osp.service.jw.stuEvaluate.StuEvaluateContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/stuEvaluateContent")
@Api(value = "/stuEvaluateContent",description = "评价内容模块")
public class StuEvaluateContentController {
    @Autowired
    private StuEvaluateContentService stuEvaluateContentService;

    @PostMapping("/saveStuEvaluateContent")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveStuEvaluateContent(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuEvaluateContent stuEvaluateContent){
        StuEvaluateContent s=stuEvaluateContentService.saveStuEvaluateContent(stuEvaluateContent);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findStuEvaluateContentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findStuEvaluateContentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        StuEvaluateContent stuEvaluateContent=stuEvaluateContentService.findStuEvaluateContentById(id);
        return new ResponseJson(stuEvaluateContent);
    }

    @PostMapping("/update/updateStuEvaluateContent")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateStuEvaluateContent(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody StuEvaluateContent stuEvaluateContent){
        stuEvaluateContentService.updateStuEvaluateContent(stuEvaluateContent);
        return new ResponseJson();
    }

    @GetMapping("/look/lookStuEvaluateContentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookStuEvaluateContentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        StuEvaluateContent stuEvaluateContent=stuEvaluateContentService.findStuEvaluateContentById(id);
        return new ResponseJson(stuEvaluateContent);
    }

    @PostMapping("/findStuEvaluateContentsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findStuEvaluateContentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuEvaluateContent stuEvaluateContent){
        stuEvaluateContent.setSchoolId(mySchoolId());
        List<StuEvaluateContent> data=stuEvaluateContentService.findStuEvaluateContentListByCondition(stuEvaluateContent);
        long count=stuEvaluateContentService.findStuEvaluateContentCountByCondition(stuEvaluateContent);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneStuEvaluateContentByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneStuEvaluateContentByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuEvaluateContent stuEvaluateContent){
        StuEvaluateContent one=stuEvaluateContentService.findOneStuEvaluateContentByCondition(stuEvaluateContent);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteStuEvaluateContent/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStuEvaluateContent(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        stuEvaluateContentService.deleteStuEvaluateContent(id);
        return new ResponseJson();
    }


    @PostMapping("/findStuEvaluateContentListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findStuEvaluateContentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuEvaluateContent stuEvaluateContent){
        List<StuEvaluateContent> data=stuEvaluateContentService.findStuEvaluateContentListByCondition(stuEvaluateContent);
        return new ResponseJson(data);
    }



}
