package com.yice.edu.cn.jy.controller.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.TeacherAccessConfiguration;
import com.yice.edu.cn.jy.service.titleQuota.TeacherAccessConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacherAccessConfiguration")
@Api(value = "/teacherAccessConfiguration",description = "题目额度教师访问配置模块")
public class TeacherAccessConfigurationController {
    @Autowired
    private TeacherAccessConfigurationService teacherAccessConfigurationService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findTeacherAccessConfigurationById/{id}")
    @ApiOperation(value = "通过id查找题目额度教师访问配置", notes = "返回题目额度教师访问配置对象")
    public TeacherAccessConfiguration findTeacherAccessConfigurationById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return teacherAccessConfigurationService.findTeacherAccessConfigurationById(id);
    }

    @PostMapping("/saveTeacherAccessConfiguration")
    @ApiOperation(value = "保存题目额度教师访问配置", notes = "返回题目额度教师访问配置对象")
    public TeacherAccessConfiguration saveTeacherAccessConfiguration(
            @ApiParam(value = "题目额度教师访问配置对象", required = true)
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        teacherAccessConfigurationService.saveTeacherAccessConfiguration(teacherAccessConfiguration);
        return teacherAccessConfiguration;
    }

    @PostMapping("/batchSaveTeacherAccessConfiguration")
    @ApiOperation(value = "批量保存题目额度教师访问配置")
    public void batchSaveTeacherAccessConfiguration(
            @ApiParam(value = "题目额度教师访问配置对象集合", required = true)
            @RequestBody List<TeacherAccessConfiguration> teacherAccessConfigurations){
        teacherAccessConfigurationService.batchSaveTeacherAccessConfiguration(teacherAccessConfigurations);
    }

    @PostMapping("/findTeacherAccessConfigurationListByCondition")
    @ApiOperation(value = "根据条件查找题目额度教师访问配置列表", notes = "返回题目额度教师访问配置列表")
    public List<TeacherAccessConfiguration> findTeacherAccessConfigurationListByCondition(
            @ApiParam(value = "题目额度教师访问配置对象")
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        return teacherAccessConfigurationService.findTeacherAccessConfigurationListByCondition(teacherAccessConfiguration);
    }
    @PostMapping("/findTeacherAccessConfigurationCountByCondition")
    @ApiOperation(value = "根据条件查找题目额度教师访问配置列表个数", notes = "返回题目额度教师访问配置总个数")
    public long findTeacherAccessConfigurationCountByCondition(
            @ApiParam(value = "题目额度教师访问配置对象")
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        return teacherAccessConfigurationService.findTeacherAccessConfigurationCountByCondition(teacherAccessConfiguration);
    }

    @PostMapping("/updateTeacherAccessConfiguration")
    @ApiOperation(value = "修改题目额度教师访问配置有值的字段", notes = "题目额度教师访问配置对象必传")
    public void updateTeacherAccessConfiguration(
            @ApiParam(value = "题目额度教师访问配置对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        teacherAccessConfigurationService.updateTeacherAccessConfiguration(teacherAccessConfiguration);
    }
    @PostMapping("/updateTeacherAccessConfigurationForAll")
    @ApiOperation(value = "修改题目额度教师访问配置所有字段", notes = "题目额度教师访问配置对象必传")
    public void updateTeacherAccessConfigurationForAll(
            @ApiParam(value = "题目额度教师访问配置对象", required = true)
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        teacherAccessConfigurationService.updateTeacherAccessConfigurationForAll(teacherAccessConfiguration);
    }

    @GetMapping("/deleteTeacherAccessConfiguration/{id}")
    @ApiOperation(value = "通过id删除题目额度教师访问配置")
    public void deleteTeacherAccessConfiguration(
            @ApiParam(value = "题目额度教师访问配置对象", required = true)
            @PathVariable String id){
        teacherAccessConfigurationService.deleteTeacherAccessConfiguration(id);
    }
    @PostMapping("/deleteTeacherAccessConfigurationByCondition")
    @ApiOperation(value = "根据条件删除题目额度教师访问配置")
    public void deleteTeacherAccessConfigurationByCondition(
            @ApiParam(value = "题目额度教师访问配置对象")
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        teacherAccessConfigurationService.deleteTeacherAccessConfigurationByCondition(teacherAccessConfiguration);
    }
    @PostMapping("/findOneTeacherAccessConfigurationByCondition")
    @ApiOperation(value = "根据条件查找单个题目额度教师访问配置,结果必须为单条数据", notes = "返回单个题目额度教师访问配置,没有时为空")
    public TeacherAccessConfiguration findOneTeacherAccessConfigurationByCondition(
            @ApiParam(value = "题目额度教师访问配置对象")
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        return teacherAccessConfigurationService.findOneTeacherAccessConfigurationByCondition(teacherAccessConfiguration);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findTeacherAccessConfigurationListByCondition4Like")
    @ApiOperation(value = "根据条件查找题目额度教师访问配置列表", notes = "返回题目额度教师访问配置列表")
    public List<TeacherAccessConfiguration> findTeacherAccessConfigurationListByCondition4Like(
            @ApiParam(value = "题目额度教师访问配置对象")
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        return teacherAccessConfigurationService.findTeacherAccessConfigurationListByCondition4Like(teacherAccessConfiguration);
    }

    @PostMapping("/saveOrUpdate")
    public TeacherAccessConfiguration saveOrUpdate(@RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        teacherAccessConfigurationService.saveOrUpdate(teacherAccessConfiguration);
        return  teacherAccessConfiguration;
    }


    @PostMapping("/findTeacherAccessConfigurationListByCondition4LikeCount")
    @ApiOperation(value = "根据条件查找题目额度教师访问配置列表个数", notes = "返回题目额度教师访问配置总个数")
    public long findTeacherAccessConfigurationListByCondition4LikeCount(
            @ApiParam(value = "题目额度教师访问配置对象")
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        return teacherAccessConfigurationService.findTeacherAccessConfigurationListByCondition4LikeCount(teacherAccessConfiguration);
    }

    @PostMapping("/findTeacherAccessConfigurationsByConditioOne")
    @ApiOperation(value = "根据条件只查询一个", notes = "返回题目额度教师访问配置列表")
    public TeacherAccessConfiguration findTeacherAccessConfigurationsByConditioOne(
            @ApiParam(value = "题目额度教师访问配置对象")
            @RequestBody TeacherAccessConfiguration teacherAccessConfiguration){
        return teacherAccessConfigurationService.findTeacherAccessConfigurationsByConditioOne(teacherAccessConfiguration);
    }


}
