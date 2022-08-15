package com.yice.edu.cn.xw.controller.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.WorkerKqRules;
import com.yice.edu.cn.xw.service.workerKq.WorkerKqRulesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workerKqRules")
@Api(value = "/workerKqRules", description = "模块")
public class WorkerKqRulesController {
    @Autowired
    private WorkerKqRulesService workerKqRulesService;

    @GetMapping("/findWorkerKqRulesById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public WorkerKqRules findWorkerKqRulesById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return workerKqRulesService.findWorkerKqRulesById(id);
    }

    @PostMapping("/saveWorkerKqRules")
    @ApiOperation(value = "保存", notes = "返回对象")
    public WorkerKqRules saveWorkerKqRules(
            @ApiParam(value = "对象", required = true)
            @RequestBody WorkerKqRules workerKqRules) {
        workerKqRulesService.saveWorkerKqRules(workerKqRules);
        return workerKqRules;
    }

    @PostMapping("/findWorkerKqRulesListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WorkerKqRules> findWorkerKqRulesListByCondition(
            @ApiParam(value = "对象")
            @RequestBody WorkerKqRules workerKqRules) {
        return workerKqRulesService.findWorkerKqRulesListByCondition(workerKqRules);
    }

    @PostMapping("/findWorkerKqRulesCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findWorkerKqRulesCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody WorkerKqRules workerKqRules) {
        return workerKqRulesService.findWorkerKqRulesCountByCondition(workerKqRules);
    }

    @PostMapping("/updateWorkerKqRules")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateWorkerKqRules(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody WorkerKqRules workerKqRules) {
        workerKqRulesService.updateWorkerKqRules(workerKqRules);
    }

    @GetMapping("/deleteWorkerKqRules/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteWorkerKqRules(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        workerKqRulesService.deleteWorkerKqRules(id);
    }

    @PostMapping("/deleteWorkerKqRulesByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteWorkerKqRulesByCondition(
            @ApiParam(value = "对象")
            @RequestBody WorkerKqRules workerKqRules) {
        workerKqRulesService.deleteWorkerKqRulesByCondition(workerKqRules);
    }

    @PostMapping("/findOneWorkerKqRulesByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public WorkerKqRules findOneWorkerKqRulesByCondition(
            @ApiParam(value = "对象")
            @RequestBody WorkerKqRules workerKqRules) {
        return workerKqRulesService.findOneWorkerKqRulesByCondition(workerKqRules);
    }

    @PostMapping("/createTeacherKqSendManageTask")
    @ApiOperation(value = "创建考勤动态任务", notes = "")
    public void createTeacherKqSendManageTask(
            @ApiParam(value = "对象")
            @RequestBody WorkerKqRules workerKqRules) {
        workerKqRulesService.createSendTask(workerKqRules);
    }

    @PostMapping("/sendToKqManager")
    @ApiOperation(value = "发送上班缺卡通知", notes = "")
    public void sendToKqManager(
            @ApiParam(value = "对象")
            @RequestBody String taskId) {
        System.out.println("发送上班缺卡通知");
        workerKqRulesService.sendToKqManager(taskId);
    }
}

