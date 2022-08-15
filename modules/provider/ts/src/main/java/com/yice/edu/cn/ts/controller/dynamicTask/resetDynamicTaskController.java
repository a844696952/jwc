package com.yice.edu.cn.ts.controller.dynamicTask;

import com.yice.edu.cn.ts.task.CompleteScheduleConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resetDynamicTask")
@Api(value = "/resetDynamicTask",description = "更新重置动态任务列表")
public class resetDynamicTaskController {

    @Autowired
    private CompleteScheduleConfig completeScheduleConfig;

    @PostMapping("/reset")
    @ApiOperation(value =" 重置动态任务列表", notes = "重置动态任务列表")
    public void resetDynamicTask(){
        System.out.println("下一步执行重置动态任务列表");
        completeScheduleConfig.resetDynamicTask();
        System.out.println("重置完成");
    }

}
