package com.yice.edu.cn.osp.controller.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscussReply;
import com.yice.edu.cn.osp.service.jy.collectivePlan.JyPrepareLessonsDiscussReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/jyPrepareLessonsDiscussReply")
@Api(value = "/jyPrepareLessonsDiscussReply",description = "关于教案讨论回复模块")
public class JyPrepareLessonsDiscussReplyController {
    @Autowired
    private JyPrepareLessonsDiscussReplyService jyPrepareLessonsDiscussReplyService;

    @PostMapping("/saveJyPrepareLessonsDiscussReply")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= JyPrepareLessonsDiscussReply.class)
    public ResponseJson saveJyPrepareLessonsDiscussReply(
            @ApiParam(value = "对象", required = true)
            @RequestBody JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply){
       jyPrepareLessonsDiscussReply.setSchoolId(mySchoolId());
       jyPrepareLessonsDiscussReply.setTeacherId(currentTeacher().getId());
        JyPrepareLessonsDiscussReply s=jyPrepareLessonsDiscussReplyService.saveJyPrepareLessonsDiscussReply(jyPrepareLessonsDiscussReply);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findJyPrepareLessonsDiscussReplyById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=JyPrepareLessonsDiscussReply.class)
    public ResponseJson findJyPrepareLessonsDiscussReplyById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply=jyPrepareLessonsDiscussReplyService.findJyPrepareLessonsDiscussReplyById(id);
        return new ResponseJson(jyPrepareLessonsDiscussReply);
    }

    @PostMapping("/update/updateJyPrepareLessonsDiscussReply")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateJyPrepareLessonsDiscussReply(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply){
        jyPrepareLessonsDiscussReplyService.updateJyPrepareLessonsDiscussReply(jyPrepareLessonsDiscussReply);
        return new ResponseJson();
    }

    @GetMapping("/look/lookJyPrepareLessonsDiscussReplyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=JyPrepareLessonsDiscussReply.class)
    public ResponseJson lookJyPrepareLessonsDiscussReplyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply=jyPrepareLessonsDiscussReplyService.findJyPrepareLessonsDiscussReplyById(id);
        return new ResponseJson(jyPrepareLessonsDiscussReply);
    }

    @PostMapping("/findJyPrepareLessonsDiscussReplysByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=JyPrepareLessonsDiscussReply.class)
    public ResponseJson findJyPrepareLessonsDiscussReplysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply){
       jyPrepareLessonsDiscussReply.setSchoolId(mySchoolId());
        List<JyPrepareLessonsDiscussReply> data=jyPrepareLessonsDiscussReplyService.findJyPrepareLessonsDiscussReplyListByCondition(jyPrepareLessonsDiscussReply);
        long count=jyPrepareLessonsDiscussReplyService.findJyPrepareLessonsDiscussReplyCountByCondition(jyPrepareLessonsDiscussReply);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneJyPrepareLessonsDiscussReplyByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=JyPrepareLessonsDiscussReply.class)
    public ResponseJson findOneJyPrepareLessonsDiscussReplyByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply){
        JyPrepareLessonsDiscussReply one=jyPrepareLessonsDiscussReplyService.findOneJyPrepareLessonsDiscussReplyByCondition(jyPrepareLessonsDiscussReply);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteJyPrepareLessonsDiscussReply/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJyPrepareLessonsDiscussReply(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        jyPrepareLessonsDiscussReplyService.deleteJyPrepareLessonsDiscussReply(id);
        return new ResponseJson();
    }


    @PostMapping("/findJyPrepareLessonsDiscussReplyListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=JyPrepareLessonsDiscussReply.class)
    public ResponseJson findJyPrepareLessonsDiscussReplyListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply){
       jyPrepareLessonsDiscussReply.setSchoolId(mySchoolId());
        List<JyPrepareLessonsDiscussReply> data=jyPrepareLessonsDiscussReplyService.findJyPrepareLessonsDiscussReplyListByCondition(jyPrepareLessonsDiscussReply);
        return new ResponseJson(data);
    }



}
