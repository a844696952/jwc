package com.yice.edu.cn.dm.controller.classCard;

import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import com.yice.edu.cn.dm.service.classCard.DmTimedTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmTimedTask")
@Api(value = "/dmTimedTask",description = "模块")
public class DmTimedTaskController {
    @Autowired
    private DmTimedTaskService dmTimedTaskService;

    @GetMapping("/findDmTimedTaskById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DmTimedTask findDmTimedTaskById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return dmTimedTaskService.findDmTimedTaskById(id);
    }

    /**
     * 批量新增一个班牌的定时开关机或者修改一个定时任务
     *
     * @param dmTimedTask
     * @return
     */
    @PostMapping("/saveOrUpdateDmTimedTaskAll")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DmTimedTask saveOrUpdateDmTimedTaskAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmTimedTask dmTimedTask) {
        dmTimedTaskService.saveDmTimedTaskAll(dmTimedTask);
        return dmTimedTask;
    }

    /**
     * 新增一个班牌的定时开关机或者修改一个定时任务
     *
     * @param dmTimedTask
     * @return
     */
    @PostMapping("/saveOrUpdateDmTimedTask")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DmTimedTask saveOrUpdateDmTimedTask(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmTimedTask dmTimedTask) {
        dmTimedTaskService.saveDmTimedTask(dmTimedTask);
        return dmTimedTask;
    }

    @PostMapping("/findDmTimedTaskListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DmTimedTask> findDmTimedTaskListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmTimedTask dmTimedTask) {
        return dmTimedTaskService.findDmTimedTaskListByCondition(dmTimedTask);
    }

    @PostMapping("/findDmTimedTaskCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDmTimedTaskCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmTimedTask dmTimedTask) {
        return dmTimedTaskService.findDmTimedTaskCountByCondition(dmTimedTask);
    }

    @PostMapping("/updateDmTimedTask")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDmTimedTask(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DmTimedTask dmTimedTask) {
        dmTimedTaskService.updateDmTimedTask(dmTimedTask);
    }

    @GetMapping("/deleteDmTimedTask/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDmTimedTask(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        dmTimedTaskService.deleteDmTimedTask(id);
    }

    @PostMapping("/deleteDmTimedTaskByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDmTimedTaskByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmTimedTask dmTimedTask) {
        dmTimedTaskService.deleteDmTimedTaskByCondition(dmTimedTask);
    }

    @PostMapping("/findOneDmTimedTaskByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DmTimedTask findOneDmTimedTaskByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmTimedTask dmTimedTask) {
        return dmTimedTaskService.findOneDmTimedTaskByCondition(dmTimedTask);
    }

    /**
     * 批量更新班牌
     *
     * @param dmTimedTask
     * @return
     */
    @PostMapping("/informVersion")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DmTimedTask informVersion(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmTimedTask dmTimedTask) {
        dmTimedTaskService.informVersion(dmTimedTask);
        return dmTimedTask;
    }

}
