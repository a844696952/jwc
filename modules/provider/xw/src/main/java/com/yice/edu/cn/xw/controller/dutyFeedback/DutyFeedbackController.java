package com.yice.edu.cn.xw.controller.dutyFeedback;

import com.yice.edu.cn.common.pojo.xw.dutyFeedback.DutyFeedback;
import com.yice.edu.cn.xw.service.dutyFeedback.DutyFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dutyFeedback")
@Api(value = "/dutyFeedback",description = "模块")
public class DutyFeedbackController {
    @Autowired
    private DutyFeedbackService dutyFeedbackService;

    @GetMapping("/findDutyFeedbackById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DutyFeedback findDutyFeedbackById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dutyFeedbackService.findDutyFeedbackById(id);
    }

    @PostMapping("/saveDutyFeedback")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DutyFeedback saveDutyFeedback(
            @ApiParam(value = "对象", required = true)
            @RequestBody DutyFeedback dutyFeedback){
        dutyFeedbackService.saveDutyFeedback(dutyFeedback);
        return dutyFeedback;
    }

    @PostMapping("/findDutyFeedbackListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DutyFeedback> findDutyFeedbackListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DutyFeedback dutyFeedback){
        return dutyFeedbackService.findDutyFeedbackListByCondition(dutyFeedback);
    }
    @PostMapping("/findDutyFeedbackCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDutyFeedbackCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DutyFeedback dutyFeedback){
        return dutyFeedbackService.findDutyFeedbackCountByCondition(dutyFeedback);
    }

    @PostMapping("/updateDutyFeedback")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDutyFeedback(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DutyFeedback dutyFeedback){
        dutyFeedbackService.updateDutyFeedback(dutyFeedback);
    }

    @GetMapping("/deleteDutyFeedback/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDutyFeedback(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dutyFeedbackService.deleteDutyFeedback(id);
    }
    @PostMapping("/deleteDutyFeedbackByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDutyFeedbackByCondition(
            @ApiParam(value = "对象")
            @RequestBody DutyFeedback dutyFeedback){
        dutyFeedbackService.deleteDutyFeedbackByCondition(dutyFeedback);
    }
    @PostMapping("/findOneDutyFeedbackByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DutyFeedback findOneDutyFeedbackByCondition(
            @ApiParam(value = "对象")
            @RequestBody DutyFeedback dutyFeedback){
        return dutyFeedbackService.findOneDutyFeedbackByCondition(dutyFeedback);
    }
}
