package com.yice.edu.cn.yed.controller.jw.feedback;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.feedback.Feedback;
import com.yice.edu.cn.yed.service.jw.feedback.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@Api(value = "/feedback",description = "模块")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/saveFeedback")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveFeedback(
            @ApiParam(value = "对象", required = true)
            @RequestBody Feedback feedback){
        Feedback s=feedbackService.saveFeedback(feedback);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findFeedbackById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findFeedbackById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Feedback feedback=feedbackService.findFeedbackById(id);
        return new ResponseJson(feedback);
    }

    @PostMapping("/update/updateFeedback")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateFeedback(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Feedback feedback){
        feedbackService.updateFeedback(feedback);
        return new ResponseJson();
    }

    @GetMapping("/look/lookFeedbackById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookFeedbackById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Feedback feedback=feedbackService.findFeedbackById(id);
        return new ResponseJson(feedback);
    }

    @PostMapping("/findFeedbacksByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findFeedbacksByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Feedback feedback){
        List<Feedback> data=feedbackService.findFeedbackListByCondition(feedback);
        long count=feedbackService.findFeedbackCountByCondition(feedback);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneFeedbackByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneFeedbackByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Feedback feedback){
        Feedback one=feedbackService.findOneFeedbackByCondition(feedback);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteFeedback/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteFeedback(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        feedbackService.deleteFeedback(id);
        return new ResponseJson();
    }


    @PostMapping("/findFeedbackListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findFeedbackListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Feedback feedback){
        List<Feedback> data=feedbackService.findFeedbackListByCondition(feedback);
        return new ResponseJson(data);
    }



}
