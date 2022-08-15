package com.yice.edu.cn.osp.controller.jy.titleQuota;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TeacherAccessConfiguration;
import com.yice.edu.cn.osp.service.jy.titleQuota.TeacherAccessConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/teacherAccessConfiguration")
@Api(value = "/teacherAccessConfiguration",description = "题目额度教师访问配置模块")
public class TeacherAccessConfigurationController {
    @Autowired
    private TeacherAccessConfigurationService teacherAccessConfigurationService;

    @PostMapping("/findTeacherAccessConfigurationsByConditioOne")
    @ApiOperation(value = "只查询一条", notes = "没有时返回空", response=TeacherAccessConfiguration.class)
    public ResponseJson findOneTeacherAccessConfigurationByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        teacherAccessConfiguration.setSchoolId(mySchoolId());
        TeacherAccessConfiguration one=teacherAccessConfigurationService.findTeacherAccessConfigurationsByConditioOne(teacherAccessConfiguration);
        return new ResponseJson(one);
    }

    @PostMapping("/findTeacherAccessConfigurationsByCondition4Like")
    @ApiOperation(value = "根据条件查找题目额度教师访问配置", notes = "返回响应对象", response=TeacherAccessConfiguration.class)
    public ResponseJson findTeacherAccessConfigurationsByCondition4Like(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        teacherAccessConfiguration.setSchoolId(mySchoolId());
        List<TeacherAccessConfiguration> data = teacherAccessConfigurationService.findTeacherAccessConfigurationListByCondition4Like(teacherAccessConfiguration);
        long count = teacherAccessConfigurationService.findTeacherAccessConfigurationListByCondition4LikeCount(teacherAccessConfiguration);
        return new ResponseJson(data,count);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "根据条件查找题目额度教师访问配置", notes = "返回响应对象", response=TeacherAccessConfiguration.class)
    public ResponseJson saveOrUpdate(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        teacherAccessConfiguration.setSchoolId(mySchoolId());
        teacherAccessConfigurationService.saveOrUpdate(teacherAccessConfiguration);
        return new ResponseJson(teacherAccessConfiguration);
    }

    @PostMapping("/update/batchupdateTeacherAccessConfiguration")
    @ApiOperation(value = "修改题目额度教师访问配置对象所有字段", notes = "返回响应对象")
    public ResponseJson batchupdateTeacherAccessConfiguration(
            @ApiParam(value = "被修改的题目额度教师访问配置对象", required = true)
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        teacherAccessConfiguration.setSchoolId(mySchoolId());
        teacherAccessConfigurationService.updateTeacherAccessConfiguration(teacherAccessConfiguration);
        return new ResponseJson();
    }

    @PostMapping("/saveTeacherAccessConfiguration")
    @ApiOperation(value = "保存题目额度教师访问配置对象", notes = "返回保存好的题目额度教师访问配置对象", response=TeacherAccessConfiguration.class)
    public ResponseJson saveTeacherAccessConfiguration(
            @ApiParam(value = "题目额度教师访问配置对象", required = true)
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
       teacherAccessConfiguration.setSchoolId(mySchoolId());
        TeacherAccessConfiguration s=teacherAccessConfigurationService.saveTeacherAccessConfiguration(teacherAccessConfiguration);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findTeacherAccessConfigurationById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找题目额度教师访问配置", notes = "返回响应对象", response=TeacherAccessConfiguration.class)
    public ResponseJson findTeacherAccessConfigurationById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        TeacherAccessConfiguration teacherAccessConfiguration=teacherAccessConfigurationService.findTeacherAccessConfigurationById(id);
        return new ResponseJson(teacherAccessConfiguration);
    }

    @PostMapping("/update/updateTeacherAccessConfiguration")
    @ApiOperation(value = "修改题目额度教师访问配置对象非空字段", notes = "返回响应对象")
    public ResponseJson updateTeacherAccessConfiguration(
            @ApiParam(value = "被修改的题目额度教师访问配置对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        teacherAccessConfigurationService.updateTeacherAccessConfiguration(teacherAccessConfiguration);
        return new ResponseJson();
    }

    @PostMapping("/update/updateTeacherAccessConfigurationForAll")
    @ApiOperation(value = "修改题目额度教师访问配置对象所有字段", notes = "返回响应对象")
    public ResponseJson updateTeacherAccessConfigurationForAll(
            @ApiParam(value = "被修改的题目额度教师访问配置对象", required = true)
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        teacherAccessConfiguration.setSchoolId(mySchoolId());
        teacherAccessConfigurationService.updateTeacherAccessConfigurationForAll(teacherAccessConfiguration);
        return new ResponseJson();
    }

    @GetMapping("/look/lookTeacherAccessConfigurationById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找题目额度教师访问配置", notes = "返回响应对象", response=TeacherAccessConfiguration.class)
    public ResponseJson lookTeacherAccessConfigurationById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        TeacherAccessConfiguration teacherAccessConfiguration=teacherAccessConfigurationService.findTeacherAccessConfigurationById(id);
        return new ResponseJson(teacherAccessConfiguration);
    }

    @PostMapping("/findTeacherAccessConfigurationsByCondition")
    @ApiOperation(value = "根据条件查找题目额度教师访问配置", notes = "返回响应对象", response=TeacherAccessConfiguration.class)
    public ResponseJson findTeacherAccessConfigurationsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
       teacherAccessConfiguration.setSchoolId(mySchoolId());
        List<TeacherAccessConfiguration> data=teacherAccessConfigurationService.findTeacherAccessConfigurationListByCondition(teacherAccessConfiguration);
        long count=teacherAccessConfigurationService.findTeacherAccessConfigurationCountByCondition(teacherAccessConfiguration);
        return new ResponseJson(data,count);
    }

    @GetMapping("/deleteTeacherAccessConfiguration/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteTeacherAccessConfiguration(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        teacherAccessConfigurationService.deleteTeacherAccessConfiguration(id);
        return new ResponseJson();
    }


    @PostMapping("/findTeacherAccessConfigurationListByCondition")
    @ApiOperation(value = "根据条件查找题目额度教师访问配置列表", notes = "返回响应对象,不包含总条数", response=TeacherAccessConfiguration.class)
    public ResponseJson findTeacherAccessConfigurationListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
       teacherAccessConfiguration.setSchoolId(mySchoolId());
        List<TeacherAccessConfiguration> data=teacherAccessConfigurationService.findTeacherAccessConfigurationListByCondition(teacherAccessConfiguration);
        return new ResponseJson(data);
    }



}
