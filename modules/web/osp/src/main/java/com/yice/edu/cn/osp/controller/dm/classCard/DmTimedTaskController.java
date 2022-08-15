package com.yice.edu.cn.osp.controller.dm.classCard;

import com.yice.edu.cn.common.annotation.SysLog;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import com.yice.edu.cn.osp.service.dm.classCard.DmTimedTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmTimedTask")
@Api(value = "/dmTimedTask",description = "模块")
public class DmTimedTaskController {
    @Autowired
    private DmTimedTaskService dmTimedTaskService;

    /**
     * 批量新增一个班牌的定时开关机或者修改一个定时任务
     *
     * @param dmTimedTask
     * @return
     */
    @PostMapping("/saveOrUpdateDmTimedTaskAll")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    //@SysLog("批量设置定时任务")
    public ResponseJson saveOrUpdateDmTimedTaskAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmTimedTask dmTimedTask) {
        dmTimedTask.setSchoolId(mySchoolId());
        ResponseJson responseJson = new ResponseJson();
        //校验输入的开关机时间，开机与关机对应，两者要么共存，要么都不设置
        ResponseJson x = getResponseJson(dmTimedTask);
        if (x != null) return x;
        if (dmTimedTask.getEquipments().length<=0) {
            return new ResponseJson(false, "请勾选设备");
        }
        DmTimedTask s = dmTimedTaskService.saveOrUpdateDmTimedTaskAll(dmTimedTask);
        DmClassCard card = new DmClassCard();
        card.setTeacherId(myId());
        card.setSchoolId(mySchoolId());
        card.setCardIds(dmTimedTask.getCardIds());
        return new ResponseJson(card);
    }

    /**
     * 批量新增一个班牌的定时开关机或者修改一个定时任务
     *
     * @param dmTimedTask
     * @return
     */
    @PostMapping("/saveOrUpdateDmTimedTask")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveOrUpdateDmTimedTask(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmTimedTask dmTimedTask) {
        dmTimedTask.setSchoolId(mySchoolId());
        ResponseJson responseJson = new ResponseJson();
        //校验输入的开关机时间，开机与关机对应，两者要么共存，要么都不设置
        ResponseJson x = getResponseJson(dmTimedTask);
        if (x != null) return x;
        DmTimedTask s = dmTimedTaskService.saveOrUpdateDmTimedTask(dmTimedTask);
        return new ResponseJson(s);
    }

    private ResponseJson getResponseJson(@ApiParam(value = "对象", required = true) @RequestBody DmTimedTask dmTimedTask) {
        if (StringUtils.isBlank(dmTimedTask.getMondayStartTime()) &&
                StringUtils.isNotBlank(dmTimedTask.getMondayShutdownTime())) {
            return new ResponseJson(false, "周一开机时间不能为空");
        }
        if (StringUtils.isBlank(dmTimedTask.getTuesdayStartTime()) &&
                StringUtils.isNotBlank(dmTimedTask.getTuesdayShutdownTime())) {
            return new ResponseJson(false, "周二开机时间不能为空");
        }
        if (StringUtils.isBlank(dmTimedTask.getWednesdayStartTime()) &&
                StringUtils.isNotBlank(dmTimedTask.getWednesdayShutdownTime())) {
            return new ResponseJson(false, "周三开机时间不能为空");
        }
        if (StringUtils.isBlank(dmTimedTask.getThursdayStartTime()) &&
                StringUtils.isNotBlank(dmTimedTask.getThursdayShutdownTime())) {
            return new ResponseJson(false, "周四开机时间不能为空");
        }
        if (StringUtils.isBlank(dmTimedTask.getFridayStartTime()) &&
                StringUtils.isNotBlank(dmTimedTask.getFridayShutdownTime())) {
            return new ResponseJson(false, "周五开机时间不能为空");
        }
        if (StringUtils.isBlank(dmTimedTask.getSaturdayStartTime()) &&
                StringUtils.isNotBlank(dmTimedTask.getSaturdayShutdownTime())) {
            return new ResponseJson(false, "周六开机时间不能为空");
        }
        if (StringUtils.isBlank(dmTimedTask.getSundayStartTime()) &&
                StringUtils.isNotBlank(dmTimedTask.getSundayShutdownTime())) {
            return new ResponseJson(false, "周日开机时间不能为空");
        }
        if (StringUtils.isNotBlank(dmTimedTask.getMondayStartTime()) &&
                StringUtils.isBlank(dmTimedTask.getMondayShutdownTime())) {
            return new ResponseJson(false, "周一关机时间不能为空");
        }
        if (StringUtils.isNotBlank(dmTimedTask.getTuesdayStartTime()) &&
                StringUtils.isBlank(dmTimedTask.getTuesdayShutdownTime())) {
            return new ResponseJson(false, "周二关机时间不能为空");
        }
        if (StringUtils.isNotBlank(dmTimedTask.getWednesdayStartTime()) &&
                StringUtils.isBlank(dmTimedTask.getWednesdayShutdownTime())) {
            return new ResponseJson(false, "周三关机时间不能为空");
        }
        if (StringUtils.isNotBlank(dmTimedTask.getThursdayStartTime()) &&
                StringUtils.isBlank(dmTimedTask.getThursdayShutdownTime())) {
            return new ResponseJson(false, "周四关机时间不能为空");
        }
        if (StringUtils.isNotBlank(dmTimedTask.getFridayStartTime()) &&
                StringUtils.isBlank(dmTimedTask.getFridayShutdownTime())) {
            return new ResponseJson(false, "周五关机时间不能为空");
        }
        if (StringUtils.isNotBlank(dmTimedTask.getSaturdayStartTime()) &&
                StringUtils.isBlank(dmTimedTask.getSaturdayShutdownTime())) {
            return new ResponseJson(false, "周六关机时间不能为空");
        }
        if (StringUtils.isNotBlank(dmTimedTask.getSundayStartTime()) &&
                StringUtils.isBlank(dmTimedTask.getSundayShutdownTime())) {
            return new ResponseJson(false, "周日关机时间不能为空");
        }
        return null;
    }

    @GetMapping("/update/findDmTimedTaskById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findDmTimedTaskById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        DmTimedTask dmTimedTask = dmTimedTaskService.findDmTimedTaskById(id);
        return new ResponseJson(dmTimedTask);
    }

    @PostMapping("/update/updateDmTimedTask")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDmTimedTask(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DmTimedTask dmTimedTask) {
        dmTimedTaskService.updateDmTimedTask(dmTimedTask);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmTimedTaskById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookDmTimedTaskById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        DmTimedTask dmTimedTask = dmTimedTaskService.findDmTimedTaskById(id);
        return new ResponseJson(dmTimedTask);
    }

    @PostMapping("/findDmTimedTasksByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findDmTimedTasksByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmTimedTask dmTimedTask) {
        dmTimedTask.setSchoolId(mySchoolId());
        List<DmTimedTask> data = dmTimedTaskService.findDmTimedTaskListByCondition(dmTimedTask);
        long count = dmTimedTaskService.findDmTimedTaskCountByCondition(dmTimedTask);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneDmTimedTaskByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneDmTimedTaskByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmTimedTask dmTimedTask) {
        DmTimedTask one = dmTimedTaskService.findOneDmTimedTaskByCondition(dmTimedTask);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteDmTimedTask/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmTimedTask(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        dmTimedTaskService.deleteDmTimedTask(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmTimedTaskListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDmTimedTaskListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmTimedTask dmTimedTask) {
        dmTimedTask.setSchoolId(mySchoolId());
        List<DmTimedTask> data = dmTimedTaskService.findDmTimedTaskListByCondition(dmTimedTask);
        return new ResponseJson(data);
    }

    /**
     * 批量新增一个班牌的定时开关机或者修改一个定时任务
     *
     * @param dmTimedTask
     * @return
     */
    @PostMapping("/informVersion")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    @SysLog("更新")
    public ResponseJson informVersion(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmTimedTask dmTimedTask) {
        dmTimedTask.setSchoolId(mySchoolId());
        dmTimedTask.setPosition(myId());
        ResponseJson responseJson = new ResponseJson();
        if (dmTimedTask.getEquipments().length<=0) {
            return new ResponseJson(false, "请勾选设备");
        }
        DmTimedTask s = dmTimedTaskService.informVersion(dmTimedTask);
        DmClassCard card = new DmClassCard();
        card.setTeacherId(myId());
        card.setSchoolId(mySchoolId());
        card.setCardIds(dmTimedTask.getCardIds());
        return new ResponseJson(card);
    }


}
