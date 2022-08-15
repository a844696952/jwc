package com.yice.edu.cn.osp.controller.jy.collectivePlan;

import com.yice.edu.cn.common.dto.jy.DiscussAndReply;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscuss;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscussReply;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeamTeachingPlan;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.jy.collectivePlan.JyPrepareLessonsDiscussService;
import com.yice.edu.cn.osp.service.jy.collectivePlan.TeamTeachingPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/jyPrepareLessonsDiscuss")
@Api(value = "/jyPrepareLessonsDiscuss", description = "关于教案讨论模块")
public class JyPrepareLessonsDiscussController {
    @Autowired
    private JyPrepareLessonsDiscussService jyPrepareLessonsDiscussService;
    @Autowired
    private TeamTeachingPlanService teamTeachingPlanService;

    @PostMapping("/saveJyPrepareLessonsDiscuss")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveJyPrepareLessonsDiscuss(
            @ApiParam(value = "对象", required = true)
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        jyPrepareLessonsDiscuss.setSchoolId(mySchoolId());
        jyPrepareLessonsDiscuss.setTeacherId(currentTeacher().getId());
        JyPrepareLessonsDiscuss s = jyPrepareLessonsDiscussService.saveJyPrepareLessonsDiscuss(jyPrepareLessonsDiscuss);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findJyPrepareLessonsDiscussById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findJyPrepareLessonsDiscussById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss = jyPrepareLessonsDiscussService.findJyPrepareLessonsDiscussById(id);
        return new ResponseJson(jyPrepareLessonsDiscuss);
    }

    @PostMapping("/update/updateJyPrepareLessonsDiscuss")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateJyPrepareLessonsDiscuss(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        jyPrepareLessonsDiscussService.updateJyPrepareLessonsDiscuss(jyPrepareLessonsDiscuss);
        return new ResponseJson();
    }

    @GetMapping("/look/lookJyPrepareLessonsDiscussById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookJyPrepareLessonsDiscussById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss = jyPrepareLessonsDiscussService.findJyPrepareLessonsDiscussById(id);
        return new ResponseJson(jyPrepareLessonsDiscuss);
    }

    @PostMapping("/findJyPrepareLessonsDiscusssByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findJyPrepareLessonsDiscusssByConditionDto(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        Map<String, List> map = new HashMap<>();
        jyPrepareLessonsDiscuss.setSchoolId(mySchoolId());
        List<DiscussAndReply> data = jyPrepareLessonsDiscussService.findJyPrepareLessonsDiscussListByConditionDto(jyPrepareLessonsDiscuss);
        String teacherId = teamTeachingPlanService.lookTeamTeachingPlanById(jyPrepareLessonsDiscuss.getTeachingPlanId()).getTeacherId();
        map.put(teacherId, data);
        long count = jyPrepareLessonsDiscussService.findJyPrepareLessonsDiscussCountByCondition(jyPrepareLessonsDiscuss);
        return new ResponseJson(map, count);
    }

    @PostMapping("/findOneJyPrepareLessonsDiscussByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneJyPrepareLessonsDiscussByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        JyPrepareLessonsDiscuss one = jyPrepareLessonsDiscussService.findOneJyPrepareLessonsDiscussByCondition(jyPrepareLessonsDiscuss);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteJyPrepareLessonsDiscuss/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJyPrepareLessonsDiscuss(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        jyPrepareLessonsDiscussService.deleteJyPrepareLessonsDiscuss(id);
        return new ResponseJson();
    }


    @PostMapping("/findJyPrepareLessonsDiscussListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findJyPrepareLessonsDiscussListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        jyPrepareLessonsDiscuss.setSchoolId(mySchoolId());
        List<JyPrepareLessonsDiscuss> data = jyPrepareLessonsDiscussService.findJyPrepareLessonsDiscussListByCondition(jyPrepareLessonsDiscuss);
        return new ResponseJson(data);
    }


}
