package com.yice.edu.cn.osp.controller.jw.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.RewardsaPunishment;
import com.yice.edu.cn.osp.service.jw.teacher.RewardsaPunishmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/rewardsaPunishment")
@Api(value = "/rewardsaPunishment",description = "教师奖惩情况模块")
public class RewardsaPunishmentController {
    @Autowired
    private RewardsaPunishmentService rewardsaPunishmentService;

    @PostMapping("/saveRewardsaPunishment")
    @ApiOperation(value = "保存教师奖惩情况对象", notes = "返回响应对象")
    public ResponseJson saveRewardsaPunishment(
            @ApiParam(value = "教师奖惩情况对象", required = true)
            @RequestBody RewardsaPunishment rewardsaPunishment){
        RewardsaPunishment s=rewardsaPunishmentService.saveRewardsaPunishment(rewardsaPunishment);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findRewardsaPunishmentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找教师奖惩情况", notes = "返回响应对象")
    public ResponseJson findRewardsaPunishmentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        RewardsaPunishment rewardsaPunishment=rewardsaPunishmentService.findRewardsaPunishmentById(id);
        return new ResponseJson(rewardsaPunishment);
    }

    @PostMapping("/update/updateRewardsaPunishment")
    @ApiOperation(value = "修改教师奖惩情况对象", notes = "返回响应对象")
    public ResponseJson updateRewardsaPunishment(
            @ApiParam(value = "被修改的教师奖惩情况对象,对象属性不为空则修改", required = true)
            @RequestBody RewardsaPunishment rewardsaPunishment){
        rewardsaPunishmentService.updateRewardsaPunishment(rewardsaPunishment);
        return new ResponseJson();
    }

    @GetMapping("/look/lookRewardsaPunishmentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找教师奖惩情况", notes = "返回响应对象")
    public ResponseJson lookRewardsaPunishmentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        RewardsaPunishment rewardsaPunishment=rewardsaPunishmentService.findRewardsaPunishmentById(id);
        return new ResponseJson(rewardsaPunishment);
    }

    @PostMapping("/findRewardsaPunishmentsByCondition")
    @ApiOperation(value = "根据条件查找教师奖惩情况", notes = "返回响应对象")
    public ResponseJson findRewardsaPunishmentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RewardsaPunishment rewardsaPunishment){
        List<RewardsaPunishment> data=rewardsaPunishmentService.findRewardsaPunishmentListByCondition(rewardsaPunishment);
        long count=rewardsaPunishmentService.findRewardsaPunishmentCountByCondition(rewardsaPunishment);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneRewardsaPunishmentByCondition")
    @ApiOperation(value = "根据条件查找单个教师奖惩情况,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneRewardsaPunishmentByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody RewardsaPunishment rewardsaPunishment){
        RewardsaPunishment one=rewardsaPunishmentService.findOneRewardsaPunishmentByCondition(rewardsaPunishment);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteRewardsaPunishment/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteRewardsaPunishment(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        rewardsaPunishmentService.deleteRewardsaPunishment(id);
        return new ResponseJson();
    }


    @PostMapping("/findRewardsaPunishmentListByCondition")
    @ApiOperation(value = "根据条件查找教师奖惩情况列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findRewardsaPunishmentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RewardsaPunishment rewardsaPunishment){
        List<RewardsaPunishment> data=rewardsaPunishmentService.findRewardsaPunishmentListByCondition(rewardsaPunishment);
        return new ResponseJson(data);
    }



}
