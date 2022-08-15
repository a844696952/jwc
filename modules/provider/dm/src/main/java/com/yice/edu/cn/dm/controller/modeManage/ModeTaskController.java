package com.yice.edu.cn.dm.controller.modeManage;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamTask;
import com.yice.edu.cn.dm.service.modeManage.ModeTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/modeTask")
@Api(value = "/modeTask",description = "通知任务")
public class ModeTaskController {

    @Autowired
    private ModeTaskService modeTaskService;

    @GetMapping("/modeTaskSchedule")
    @ApiOperation(value = "模式通知任务调度", notes = "执行成功或失败")
    public ResponseJson modeTaskSchedule(){
        List<ExamTask> examTaskByCondition = modeTaskService.findExamTaskByCondition();
        if(CollUtil.isNotEmpty(examTaskByCondition)){
            modeTaskService.sendAndUpdate(examTaskByCondition);
        }
        return new ResponseJson(true);
    }

    @GetMapping("/classModeTaskSchedule")
    @ApiOperation(value = "上课模式通知任务调度", notes = "执行成功或失败")
    public ResponseJson classModeTaskSchedule(){
        List<ExamTask> classListTask = modeTaskService.findClassTaskByCondition();
        if(CollUtil.isNotEmpty(classListTask)){
            classListTask.forEach(x->modeTaskService.updateAndPushClassMode(classListTask));
        }
        return new ResponseJson(true);
    }

    /**
     * 检测时间冲突
     * @param id
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/checkDateTime")
    @ApiOperation(value = "检测时间冲突", notes = "执行成功或失败")
    public ResponseJson checkDateTime(@RequestParam("id")String id,@RequestParam("beginTime")String beginTime,@RequestParam("endTime")String endTime){
        boolean result = modeTaskService.checkDateTimeReply(id, beginTime, endTime,null);
        return new ResponseJson(result);
    }




}
