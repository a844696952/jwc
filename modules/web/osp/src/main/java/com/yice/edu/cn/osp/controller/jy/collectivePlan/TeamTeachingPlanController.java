package com.yice.edu.cn.osp.controller.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeamTeachingPlan;
import com.yice.edu.cn.osp.service.jy.collectivePlan.TeamTeachingPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/teamTeachingPlan")
@Api(value = "/teamTeachingPlan",description = "集体备课教案 模块")
public class TeamTeachingPlanController {
    @Autowired
    private TeamTeachingPlanService teamTeachingPlanService;


    @PostMapping("/findTeamTeachingPlanListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public ResponseJson findTeamTeachingPlanListByCondition(
            @ApiParam(value = "对象")
            @RequestBody TeamTeachingPlan teachingPlan) {
        teachingPlan.setTeacherId(currentTeacher().getId());
        return teamTeachingPlanService.findTeamTeachingPlanListByCondition(teachingPlan);
    }

    @GetMapping("/editTeamTeachingPlanById/{id}")
    @ApiOperation(value = "查找编辑页面详情(基本信息,包含课件和资源Id,用','隔开)", notes = "返回对象")
    public TeamTeachingPlan editTeamTeachingPlanById(
            @ApiParam(value = "教案id", required = true)
            @PathVariable("id") String id) {
        TeamTeachingPlan teachingPlan = teamTeachingPlanService.editTeamTeachingPlanById(id);
        return teachingPlan;
    }

    @GetMapping("/lookTeamTeachingPlanById/{id}")
    @ApiOperation(value = "查找教案详情(基本信息,包含课件和资源名称地址等基本信息)", notes = "返回对象")
    public TeamTeachingPlan lookTeamTeachingPlanById(
            @ApiParam(value = "教案id", required = true)
            @PathVariable String id) {
        return teamTeachingPlanService.lookTeamTeachingPlanById(id);
    }

    @PostMapping("/updaTeamteTeachingPlan")
    @ApiOperation(value = "更新", notes = "对象必传")
    public ResponseJson updaTeamteTeachingPlan(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody TeamTeachingPlan teachingPlan) {

        if(StringUtils.isBlank(teachingPlan.getCollectivePlanId())){
            return new ResponseJson(false,"讨论组id为空");
        }
        return teamTeachingPlanService.updaTeamteTeachingPlan(teachingPlan);
    }

    @GetMapping("/deleteTeamTeachingPlan/{id}")
    @ApiOperation(value = "通过id删除教案并且删除资源")
    public ResponseJson deleteTeamTeachingPlan(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        return teamTeachingPlanService.deleteTeamTeachingPlan(id);
    }


/*    @GetMapping("/download/{id}")
    @ApiOperation(value = "下载教案")
    public void downloadTeachingPlan(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        teamTeachingPlanService.downloadTeachingPlan(id);
    }*/

    @GetMapping("/download/{id}")
    @ApiOperation(value = "下载教案")
    public ResponseEntity<byte[]> downloadTeachingPlan(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        ResponseEntity<byte[]> responseEntity=teamTeachingPlanService.downloadTeachingPlan(id);
        return responseEntity;
    }

    @GetMapping("/delete/lessonsFile/{id}")
    @ApiOperation(value = "删除资源文件")
    public ResponseJson deleteLessonsFile(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        return teamTeachingPlanService.deleteLessonsFile(id);
    }
    @PostMapping("/findTeachingPlanList")
    @ApiOperation(value = "查询集体备课讨论组下的已完成和正在讨论的教案--------------------------------------", notes = "返回列表")
    public ResponseJson findTeachingPlanList(
            @ApiParam(value = "对象")
            @RequestBody TeamTeachingPlan teachingPlan){
        teachingPlan.setTeacherId(myId());
        teachingPlan.setSchoolId(mySchoolId());
        return teamTeachingPlanService.findTeachingPlanList(teachingPlan);
    }

    // 查询教师的个人教案列表（未被选择提交的教案）
    @PostMapping("/findTeachingPlanListNotChosen")
    @ApiOperation(value = "查询教师的个人教案列表（未被选择提交的教案）--------------------------------------", notes = "返回列表")
    public ResponseJson findTeachingPlanListNotChosen(
            @ApiParam(value = "对象")
            @RequestBody TeamTeachingPlan teachingPlan){
        teachingPlan.setTeacherId(myId());
        teachingPlan.setSchoolId(mySchoolId());
        return teamTeachingPlanService.findTeachingPlanListNotChosen(teachingPlan);
    }

}
