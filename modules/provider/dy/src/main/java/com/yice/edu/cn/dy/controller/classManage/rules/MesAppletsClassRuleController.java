package com.yice.edu.cn.dy.controller.classManage.rules;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsClassRule;
import com.yice.edu.cn.dy.service.classManage.rules.MesAppletsClassRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesAppletsClassRule")
@Api(value = "/mesAppletsClassRule",description = "德育小程序班级制度表模块")
public class MesAppletsClassRuleController {
    @Autowired
    private MesAppletsClassRuleService mesAppletsClassRuleService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findMesAppletsClassRuleById/{id}")
    @ApiOperation(value = "通过id查找德育小程序班级制度表", notes = "返回德育小程序班级制度表对象")
    public MesAppletsClassRule findMesAppletsClassRuleById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return mesAppletsClassRuleService.findMesAppletsClassRuleById(id);
    }

    @PostMapping("/saveMesAppletsClassRule")
    @ApiOperation(value = "保存德育小程序班级制度表", notes = "返回德育小程序班级制度表对象")
    public MesAppletsClassRule saveMesAppletsClassRule(
            @ApiParam(value = "德育小程序班级制度表对象", required = true)
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
        mesAppletsClassRuleService.saveMesAppletsClassRule(mesAppletsClassRule);
        return mesAppletsClassRule;
    }

    @PostMapping("/batchSaveMesAppletsClassRule")
    @ApiOperation(value = "批量保存德育小程序班级制度表")
    public void batchSaveMesAppletsClassRule(
            @ApiParam(value = "德育小程序班级制度表对象集合", required = true)
            @RequestBody List<MesAppletsClassRule> mesAppletsClassRules){
        mesAppletsClassRuleService.batchSaveMesAppletsClassRule(mesAppletsClassRules);
    }

    @PostMapping("/findMesAppletsClassRuleListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序班级制度表列表", notes = "返回德育小程序班级制度表列表")
    public List<MesAppletsClassRule> findMesAppletsClassRuleListByCondition(
            @ApiParam(value = "德育小程序班级制度表对象")
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
        return mesAppletsClassRuleService.findMesAppletsClassRuleListByCondition(mesAppletsClassRule);
    }
    @PostMapping("/findMesAppletsClassRuleCountByCondition")
    @ApiOperation(value = "根据条件查找德育小程序班级制度表列表个数", notes = "返回德育小程序班级制度表总个数")
    public long findMesAppletsClassRuleCountByCondition(
            @ApiParam(value = "德育小程序班级制度表对象")
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
        return mesAppletsClassRuleService.findMesAppletsClassRuleCountByCondition(mesAppletsClassRule);
    }

    @PostMapping("/updateMesAppletsClassRule")
    @ApiOperation(value = "修改德育小程序班级制度表有值的字段", notes = "德育小程序班级制度表对象必传")
    public void updateMesAppletsClassRule(
            @ApiParam(value = "德育小程序班级制度表对象,对象属性不为空则修改", required = true)
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
        mesAppletsClassRuleService.updateMesAppletsClassRule(mesAppletsClassRule);
    }
    @PostMapping("/updateMesAppletsClassRuleForAll")
    @ApiOperation(value = "修改德育小程序班级制度表所有字段", notes = "德育小程序班级制度表对象必传")
    public void updateMesAppletsClassRuleForAll(
            @ApiParam(value = "德育小程序班级制度表对象", required = true)
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
        mesAppletsClassRuleService.updateMesAppletsClassRuleForAll(mesAppletsClassRule);
    }

    @GetMapping("/deleteMesAppletsClassRule/{id}")
    @ApiOperation(value = "通过id删除德育小程序班级制度表")
    public void deleteMesAppletsClassRule(
            @ApiParam(value = "德育小程序班级制度表对象", required = true)
            @PathVariable String id){
        mesAppletsClassRuleService.deleteMesAppletsClassRule(id);
    }
    @PostMapping("/deleteMesAppletsClassRuleByCondition")
    @ApiOperation(value = "根据条件删除德育小程序班级制度表")
    public void deleteMesAppletsClassRuleByCondition(
            @ApiParam(value = "德育小程序班级制度表对象")
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
        mesAppletsClassRuleService.deleteMesAppletsClassRuleByCondition(mesAppletsClassRule);
    }
    @PostMapping("/findOneMesAppletsClassRuleByCondition")
    @ApiOperation(value = "根据条件查找单个德育小程序班级制度表,结果必须为单条数据", notes = "返回单个德育小程序班级制度表,没有时为空")
    public MesAppletsClassRule findOneMesAppletsClassRuleByCondition(
            @ApiParam(value = "德育小程序班级制度表对象")
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
        return mesAppletsClassRuleService.findOneMesAppletsClassRuleByCondition(mesAppletsClassRule);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    @PostMapping("/index")
    @ApiOperation(value = "班主任进入班规主页", notes = "返回德育小程序班级制度表列表")
    public ResponseJson index(
            @ApiParam(value = "德育小程序班级制度表对象")
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
        return mesAppletsClassRuleService.index(mesAppletsClassRule);
    }


    @PostMapping("/move")
    @ApiOperation(value = "前端小程序移动班级标签", notes = "返回德育小程序班级制度表列表")
    public void move(
            @ApiParam(value = "前端小程序移动班级标签")
            @RequestBody List<MesAppletsClassRule>  mesAppletsClassRule){
         mesAppletsClassRuleService.move(mesAppletsClassRule);
    }
}
