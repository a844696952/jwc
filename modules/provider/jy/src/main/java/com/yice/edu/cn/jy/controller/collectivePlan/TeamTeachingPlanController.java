package com.yice.edu.cn.jy.controller.collectivePlan;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeamTeachingPlan;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.jy.service.collectivePlan.TeamTeachingPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/teamTeachingPlan")
@Api(value = "/teamTeachingPlan", description = "模块")
public class TeamTeachingPlanController {
    @Autowired
    private TeamTeachingPlanService teamTeachingPlanService;


    @PostMapping("/findTeamTeachingPlanListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public ResponseJson findTeachingPlanListByCondition(
            @ApiParam(value = "对象") @RequestBody TeamTeachingPlan teachingPlan) {
        return teamTeachingPlanService.findTeamTeachingPlanListByCondition(teachingPlan);
    }

    @GetMapping("/editTeamTeachingPlanById/{id}")
    @ApiOperation(value = "查找编辑页面详情(基本信息,包含课件和资源Id,用','隔开)", notes = "返回对象")
    public TeamTeachingPlan editTeamTeachingPlanById(@ApiParam(value = "教案id", required = true) @PathVariable String id) {

        return teamTeachingPlanService.editTeamTeachingPlanById(id);
    }

    @GetMapping("/lookTeamTeachingPlanById/{id}")
    @ApiOperation(value = "查找教案详情(基本信息,包含课件和资源名称地址等基本信息)", notes = "返回对象")
    public TeamTeachingPlan lookTeamTeachingPlanById(@ApiParam(value = "教案id", required = true) @PathVariable String id) {
        return teamTeachingPlanService.lookTeamTeachingPlanById(id);
    }

 /*   @PostMapping("/saveTeachingPlan")
    @ApiOperation(value = "保存", notes = "返回对象")
    public ResponseJson saveTeachingPlan(
            @ApiParam(value = "对象", required = true) @RequestBody @Validated(value = GroupOne.class) TeachingPlan teachingPlan) {
        ResponseJson result = teamTeachingPlanService.saveTeamTeachingPlan(teachingPlan);
        return result;
    }*/

    @PostMapping("/updateTeamTeachingPlan")
    @ApiOperation(value = "更新教案", notes = "对象必传")
    public ResponseJson updateTeamTeachingPlan(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true) @RequestBody @Validated(value = GroupTwo.class) TeamTeachingPlan teachingPlan) {
        return teamTeachingPlanService.updateTeamTeachingPlan(teachingPlan);
    }

    @GetMapping("/deleteTeamTeachingPlan/{id}")
    @ApiOperation(value = "通过id删除教案")
    public ResponseJson deleteTeamTeachingPlan(@ApiParam(value = "对象", required = true) @PathVariable String id) {
        return teamTeachingPlanService.deleteTeamTeachingPlan(id);
    }

/*    @GetMapping("/download/{id}")
    @ApiOperation(value = "下载教案")
    public void downTeamloadTeachingPlan(@ApiParam(value = "对象", required = true) @PathVariable String id) {
        teamTeachingPlanService.downTeamloadTeachingPlan(id);
    }*/

    @GetMapping("/delete/lessonsFile/{id}")
    @ApiOperation(value = "删除备课资源")
    public ResponseJson deleteLessonsFile(@ApiParam(value = "对象", required = true) @PathVariable String id) {
        return teamTeachingPlanService.deleteLessonsFile(id);
    }

    // 查询 集体备课讨论组下的 已完成 和 正在讨论的教案 并设置当前教师是否为主备人 true 是  false  不是
    @PostMapping("/findTeachingPlanList")
    @ApiOperation(value = "查询 集体备课讨论组下的已完成和正在讨论的教案", notes = "返回列表")
    public ResponseJson findTeachingPlanList(@ApiParam(value = "对象") @RequestBody TeamTeachingPlan teachingPlan) {
        List<TeamTeachingPlan> Tps = teamTeachingPlanService.findTeachingPlanIsPrincipal(teachingPlan);
        if (Tps.size() > 0) {
            ResponseJson s = teamTeachingPlanService.findTeachingPlanList(teachingPlan);
            s.setData2(true);
            return s;
        } else {
            ResponseJson s = teamTeachingPlanService.findTeachingPlanList(teachingPlan);
            s.setData2(false);
            return s;
        }
    }

    // 查询教师的个人教案列表（未被选择提交的教案）
    @PostMapping("/findTeachingPlanListNotChosen")
    @ApiOperation(value = "查询教师的个人教案列表（未被选择提交的教案）", notes = "返回列表")
    public ResponseJson findTeachingPlanListNotChosen(@ApiParam(value = "对象") @RequestBody TeamTeachingPlan teachingPlan) {
        return teamTeachingPlanService.findTeachingPlanListNotChosen(teachingPlan);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadTeachingPlan(@PathVariable("id") String id, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        ResponseEntity<byte[]> responseEntity = teamTeachingPlanService.downTeamloadTeachingPlan(id);
        return responseEntity;
    }
}
