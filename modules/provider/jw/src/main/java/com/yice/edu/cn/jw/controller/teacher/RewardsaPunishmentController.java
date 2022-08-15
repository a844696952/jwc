package com.yice.edu.cn.jw.controller.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.RewardsaPunishment;
import com.yice.edu.cn.jw.service.teacher.RewardsaPunishmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewardsaPunishment")
@Api(value = "/rewardsaPunishment",description = "教师奖惩情况模块")
public class RewardsaPunishmentController {
    @Autowired
    private RewardsaPunishmentService rewardsaPunishmentService;

    @GetMapping("/findRewardsaPunishmentById/{id}")
    @ApiOperation(value = "通过id查找教师奖惩情况", notes = "返回教师奖惩情况对象")
    public RewardsaPunishment findRewardsaPunishmentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return rewardsaPunishmentService.findRewardsaPunishmentById(id);
    }

    @PostMapping("/saveRewardsaPunishment")
    @ApiOperation(value = "保存教师奖惩情况", notes = "返回教师奖惩情况对象")
    public RewardsaPunishment saveRewardsaPunishment(
            @ApiParam(value = "教师奖惩情况对象", required = true)
            @RequestBody RewardsaPunishment rewardsaPunishment){
        rewardsaPunishmentService.saveRewardsaPunishment(rewardsaPunishment);
        return rewardsaPunishment;
    }

    @PostMapping("/findRewardsaPunishmentListByCondition")
    @ApiOperation(value = "根据条件查找教师奖惩情况列表", notes = "返回教师奖惩情况列表")
    public List<RewardsaPunishment> findRewardsaPunishmentListByCondition(
            @ApiParam(value = "教师奖惩情况对象")
            @RequestBody RewardsaPunishment rewardsaPunishment){
        return rewardsaPunishmentService.findRewardsaPunishmentListByCondition(rewardsaPunishment);
    }
    @PostMapping("/findRewardsaPunishmentListByCondition4Like")
    @ApiOperation(value = "根据条件查找教师奖惩情况列表", notes = "返回教师奖惩情况列表")
    public List<RewardsaPunishment> findRewardsaPunishmentListByCondition4Like(
            @ApiParam(value = "教师奖惩情况对象")
            @RequestBody RewardsaPunishment rewardsaPunishment){
        return rewardsaPunishmentService.findRewardsaPunishmentListByCondition4Like(rewardsaPunishment);
    }
    @PostMapping("/findRewardsaPunishmentCountByCondition")
    @ApiOperation(value = "根据条件查找教师奖惩情况列表个数", notes = "返回教师奖惩情况总个数")
    public long findRewardsaPunishmentCountByCondition(
            @ApiParam(value = "教师奖惩情况对象")
            @RequestBody RewardsaPunishment rewardsaPunishment){
        return rewardsaPunishmentService.findRewardsaPunishmentCountByCondition(rewardsaPunishment);
    }
    @PostMapping("/findRewardsaPunishmentCountByCondition4Like")
    @ApiOperation(value = "根据条件查找教师奖惩情况列表个数", notes = "返回教师奖惩情况总个数")
    public long findRewardsaPunishmentCountByCondition4Like(
            @ApiParam(value = "教师奖惩情况对象")
            @RequestBody RewardsaPunishment rewardsaPunishment){
        return rewardsaPunishmentService.findRewardsaPunishmentCountByCondition4Like(rewardsaPunishment);
    }

    @PostMapping("/updateRewardsaPunishment")
    @ApiOperation(value = "修改教师奖惩情况", notes = "教师奖惩情况对象必传")
    public void updateRewardsaPunishment(
            @ApiParam(value = "教师奖惩情况对象,对象属性不为空则修改", required = true)
            @RequestBody RewardsaPunishment rewardsaPunishment){
        rewardsaPunishmentService.updateRewardsaPunishment(rewardsaPunishment);
    }

    @GetMapping("/deleteRewardsaPunishment/{id}")
    @ApiOperation(value = "通过id删除教师奖惩情况")
    public void deleteRewardsaPunishment(
            @ApiParam(value = "教师奖惩情况对象", required = true)
            @PathVariable String id){
        rewardsaPunishmentService.deleteRewardsaPunishment(id);
    }
    @PostMapping("/deleteRewardsaPunishmentByCondition")
    @ApiOperation(value = "根据条件删除教师奖惩情况")
    public void deleteRewardsaPunishmentByCondition(
            @ApiParam(value = "教师奖惩情况对象")
            @RequestBody RewardsaPunishment rewardsaPunishment){
        rewardsaPunishmentService.deleteRewardsaPunishmentByCondition(rewardsaPunishment);
    }
    @PostMapping("/findOneRewardsaPunishmentByCondition")
    @ApiOperation(value = "根据条件查找单个教师奖惩情况,结果必须为单条数据", notes = "返回单个教师奖惩情况,没有时为空")
    public RewardsaPunishment findOneRewardsaPunishmentByCondition(
            @ApiParam(value = "教师奖惩情况对象")
            @RequestBody RewardsaPunishment rewardsaPunishment){
        return rewardsaPunishmentService.findOneRewardsaPunishmentByCondition(rewardsaPunishment);
    }
}
