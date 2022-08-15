package com.yice.edu.cn.osp.controller.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyOption;
import com.yice.edu.cn.osp.service.jw.qusSurvey.QusSurveyOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/qusSurveyOption")
@Api(value = "/qusSurveyOption",description = "学生提交选项保存的记录的表模块")
public class QusSurveyOptionController {
    @Autowired
    private QusSurveyOptionService qusSurveyOptionService;

    @PostMapping("/saveQusSurveyOption")
    @ApiOperation(value = "保存学生提交选项保存的记录的表对象", notes = "返回响应对象")
    public ResponseJson saveQusSurveyOption(
            @ApiParam(value = "学生提交选项保存的记录的表对象", required = true)
            @RequestBody QusSurveyOption qusSurveyOption){
        QusSurveyOption s=qusSurveyOptionService.saveQusSurveyOption(qusSurveyOption);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findQusSurveyOptionById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学生提交选项保存的记录的表", notes = "返回响应对象")
    public ResponseJson findQusSurveyOptionById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        QusSurveyOption qusSurveyOption=qusSurveyOptionService.findQusSurveyOptionById(id);
        return new ResponseJson(qusSurveyOption);
    }

    @PostMapping("/update/updateQusSurveyOption")
    @ApiOperation(value = "修改学生提交选项保存的记录的表对象", notes = "返回响应对象")
    public ResponseJson updateQusSurveyOption(
            @ApiParam(value = "被修改的学生提交选项保存的记录的表对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurveyOption qusSurveyOption){
        qusSurveyOptionService.updateQusSurveyOption(qusSurveyOption);
        return new ResponseJson();
    }

    @GetMapping("/look/lookQusSurveyOptionById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生提交选项保存的记录的表", notes = "返回响应对象")
    public ResponseJson lookQusSurveyOptionById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        QusSurveyOption qusSurveyOption=qusSurveyOptionService.findQusSurveyOptionById(id);
        return new ResponseJson(qusSurveyOption);
    }

    @PostMapping("/findQusSurveyOptionsByCondition")
    @ApiOperation(value = "根据条件查找学生提交选项保存的记录的表", notes = "返回响应对象")
    public ResponseJson findQusSurveyOptionsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QusSurveyOption qusSurveyOption){
        List<QusSurveyOption> data=qusSurveyOptionService.findQusSurveyOptionListByCondition(qusSurveyOption);
        long count=qusSurveyOptionService.findQusSurveyOptionCountByCondition(qusSurveyOption);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneQusSurveyOptionByCondition")
    @ApiOperation(value = "根据条件查找单个学生提交选项保存的记录的表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneQusSurveyOptionByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody QusSurveyOption qusSurveyOption){
        QusSurveyOption one=qusSurveyOptionService.findOneQusSurveyOptionByCondition(qusSurveyOption);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteQusSurveyOption/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteQusSurveyOption(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        qusSurveyOptionService.deleteQusSurveyOption(id);
        return new ResponseJson();
    }


    @PostMapping("/findQusSurveyOptionListByCondition")
    @ApiOperation(value = "根据条件查找学生提交选项保存的记录的表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findQusSurveyOptionListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QusSurveyOption qusSurveyOption){
        List<QusSurveyOption> data=qusSurveyOptionService.findQusSurveyOptionListByCondition(qusSurveyOption);
        return new ResponseJson(data);
    }


    @PostMapping("/getOptionCountList")
    @ApiOperation(value = " 获取选项次数统计的结果集", notes = "返回每道题的选项选择次数")
    public ResponseJson getOptionCountList(
            @ApiParam(value = "问卷id、老师id")
            @Validated
            @RequestBody QusSurveyOption qusSurveyOption){
        List<QusSurveyOption> data=qusSurveyOptionService.getOptionCountList(qusSurveyOption);
        return new ResponseJson(data);
    }
}
