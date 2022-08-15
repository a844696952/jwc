package com.yice.edu.cn.xw.controller.routineDutyFeedback;

import com.yice.edu.cn.common.pojo.xw.routineDutyFeedback.RoutineDutyFeedback;
import com.yice.edu.cn.xw.service.routineDutyFeedback.RoutineDutyFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routineDutyFeedback")
@Api(value = "/routineDutyFeedback",description = "常规值班反馈表模块")
public class RoutineDutyFeedbackController {
    @Autowired
    private RoutineDutyFeedbackService routineDutyFeedbackService;

    @GetMapping("/findRoutineDutyFeedbackById/{id}")
    @ApiOperation(value = "通过id查找常规值班反馈表", notes = "返回常规值班反馈表对象")
    public RoutineDutyFeedback findRoutineDutyFeedbackById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return routineDutyFeedbackService.findRoutineDutyFeedbackById(id);
    }

    @PostMapping("/saveRoutineDutyFeedback")
    @ApiOperation(value = "保存常规值班反馈表", notes = "返回常规值班反馈表对象")
    public RoutineDutyFeedback saveRoutineDutyFeedback(
            @ApiParam(value = "常规值班反馈表对象", required = true)
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
        routineDutyFeedbackService.saveRoutineDutyFeedback(routineDutyFeedback);
        return routineDutyFeedback;
    }

    @PostMapping("/findRoutineDutyFeedbackListByCondition")
    @ApiOperation(value = "根据条件查找常规值班反馈表列表", notes = "返回常规值班反馈表列表")
    public List<RoutineDutyFeedback> findRoutineDutyFeedbackListByCondition(
            @ApiParam(value = "常规值班反馈表对象")
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
        return routineDutyFeedbackService.findRoutineDutyFeedbackListByCondition(routineDutyFeedback);
    }
    @PostMapping("/findRoutineDutyFeedbackCountByCondition")
    @ApiOperation(value = "根据条件查找常规值班反馈表列表个数", notes = "返回常规值班反馈表总个数")
    public long findRoutineDutyFeedbackCountByCondition(
            @ApiParam(value = "常规值班反馈表对象")
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
        return routineDutyFeedbackService.findRoutineDutyFeedbackCountByCondition(routineDutyFeedback);
    }

    @PostMapping("/updateRoutineDutyFeedback")
    @ApiOperation(value = "修改常规值班反馈表", notes = "常规值班反馈表对象必传")
    public void updateRoutineDutyFeedback(
            @ApiParam(value = "常规值班反馈表对象,对象属性不为空则修改", required = true)
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
        routineDutyFeedbackService.updateRoutineDutyFeedback(routineDutyFeedback);
    }

    @GetMapping("/deleteRoutineDutyFeedback/{id}")
    @ApiOperation(value = "通过id删除常规值班反馈表")
    public void deleteRoutineDutyFeedback(
            @ApiParam(value = "常规值班反馈表对象", required = true)
            @PathVariable String id){
        routineDutyFeedbackService.deleteRoutineDutyFeedback(id);
    }
    @PostMapping("/deleteRoutineDutyFeedbackByCondition")
    @ApiOperation(value = "根据条件删除常规值班反馈表")
    public void deleteRoutineDutyFeedbackByCondition(
            @ApiParam(value = "常规值班反馈表对象")
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
        routineDutyFeedbackService.deleteRoutineDutyFeedbackByCondition(routineDutyFeedback);
    }
    @PostMapping("/findOneRoutineDutyFeedbackByCondition")
    @ApiOperation(value = "根据条件查找单个常规值班反馈表,结果必须为单条数据", notes = "返回单个常规值班反馈表,没有时为空")
    public RoutineDutyFeedback findOneRoutineDutyFeedbackByCondition(
            @ApiParam(value = "常规值班反馈表对象")
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
        return routineDutyFeedbackService.findOneRoutineDutyFeedbackByCondition(routineDutyFeedback);
    }
}
