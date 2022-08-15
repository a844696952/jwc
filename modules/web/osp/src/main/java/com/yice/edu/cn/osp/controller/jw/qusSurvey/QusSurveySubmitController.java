package com.yice.edu.cn.osp.controller.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveySubmit;
import com.yice.edu.cn.osp.service.jw.qusSurvey.QusSurveySubmitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/qusSurveySubmit")
@Api(value = "/qusSurveySubmit",description = "提交整个问卷记录的表模块")
public class QusSurveySubmitController {
    @Autowired
    private QusSurveySubmitService qusSurveySubmitService;

    @PostMapping("/saveQusSurveySubmit")
    @ApiOperation(value = "保存提交整个问卷记录的表对象", notes = "返回响应对象")
    public ResponseJson saveQusSurveySubmit(
            @ApiParam(value = "提交整个问卷记录的表对象", required = true)
            @RequestBody QusSurveySubmit qusSurveySubmit){
        QusSurveySubmit s=qusSurveySubmitService.saveQusSurveySubmit(qusSurveySubmit);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findQusSurveySubmitById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找提交整个问卷记录的表", notes = "返回响应对象")
    public ResponseJson findQusSurveySubmitById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        QusSurveySubmit qusSurveySubmit=qusSurveySubmitService.findQusSurveySubmitById(id);
        return new ResponseJson(qusSurveySubmit);
    }

    @PostMapping("/update/updateQusSurveySubmit")
    @ApiOperation(value = "修改提交整个问卷记录的表对象", notes = "返回响应对象")
    public ResponseJson updateQusSurveySubmit(
            @ApiParam(value = "被修改的提交整个问卷记录的表对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurveySubmit qusSurveySubmit){
        qusSurveySubmitService.updateQusSurveySubmit(qusSurveySubmit);
        return new ResponseJson();
    }

    @GetMapping("/look/lookQusSurveySubmitById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找提交整个问卷记录的表", notes = "返回响应对象")
    public ResponseJson lookQusSurveySubmitById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        QusSurveySubmit qusSurveySubmit=qusSurveySubmitService.findQusSurveySubmitById(id);
        return new ResponseJson(qusSurveySubmit);
    }

    @PostMapping("/findQusSurveySubmitsByCondition")
    @ApiOperation(value = "根据条件查找提交整个问卷记录的表", notes = "返回响应对象")
    public ResponseJson findQusSurveySubmitsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QusSurveySubmit qusSurveySubmit){
        List<QusSurveySubmit> data=qusSurveySubmitService.findQusSurveySubmitListByCondition(qusSurveySubmit);
        long count=qusSurveySubmitService.findQusSurveySubmitCountByCondition(qusSurveySubmit);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneQusSurveySubmitByCondition")
    @ApiOperation(value = "根据条件查找单个提交整个问卷记录的表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneQusSurveySubmitByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody QusSurveySubmit qusSurveySubmit){
        QusSurveySubmit one=qusSurveySubmitService.findOneQusSurveySubmitByCondition(qusSurveySubmit);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteQusSurveySubmit/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteQusSurveySubmit(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        qusSurveySubmitService.deleteQusSurveySubmit(id);
        return new ResponseJson();
    }


    @PostMapping("/findQusSurveySubmitListByCondition")
    @ApiOperation(value = "根据条件查找提交整个问卷记录的表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findQusSurveySubmitListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QusSurveySubmit qusSurveySubmit){
        List<QusSurveySubmit> data=qusSurveySubmitService.findQusSurveySubmitListByCondition(qusSurveySubmit);
        return new ResponseJson(data);
    }

    @PostMapping("/updateSubmit")
    @ApiOperation(value = "提交教师互评", notes = "返回响应对象")
    public ResponseJson updateSubmit(
            @ApiParam(value = "被修改的提交整个问卷记录的表对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurveySubmit qusSurveySubmit){
        qusSurveySubmitService.updateSubmit(qusSurveySubmit);
        return new ResponseJson();
    }

}
