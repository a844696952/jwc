package com.yice.edu.cn.jw.controller.feedback;

import com.yice.edu.cn.common.pojo.jw.feedback.Feedback;
import com.yice.edu.cn.jw.service.feedback.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@Api(value = "/feedback",description = "模块")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/findFeedbackById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Feedback findFeedbackById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return feedbackService.findFeedbackById(id);
    }

    @PostMapping("/saveFeedback")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Feedback saveFeedback(
            @ApiParam(value = "对象", required = true)
            @RequestBody Feedback feedback){
        feedbackService.saveFeedback(feedback);
        return feedback;
    }

    @PostMapping("/findFeedbackListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Feedback> findFeedbackListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Feedback feedback){
        return feedbackService.findFeedbackListByCondition(feedback);
    }
    @PostMapping("/findFeedbackCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findFeedbackCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Feedback feedback){
        return feedbackService.findFeedbackCountByCondition(feedback);
    }

    @PostMapping("/updateFeedback")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateFeedback(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Feedback feedback){
        feedbackService.updateFeedback(feedback);
    }

    @GetMapping("/deleteFeedback/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteFeedback(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        feedbackService.deleteFeedback(id);
    }
    @PostMapping("/deleteFeedbackByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteFeedbackByCondition(
            @ApiParam(value = "对象")
            @RequestBody Feedback feedback){
        feedbackService.deleteFeedbackByCondition(feedback);
    }
    @PostMapping("/findOneFeedbackByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Feedback findOneFeedbackByCondition(
            @ApiParam(value = "对象")
            @RequestBody Feedback feedback){
        return feedbackService.findOneFeedbackByCondition(feedback);
    }
}
