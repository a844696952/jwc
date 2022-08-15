package com.yice.edu.cn.dy.controller.classManage.rules;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsRule;
import com.yice.edu.cn.dy.service.classManage.rules.MesAppletsRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesAppletsRule")
@Api(value = "/mesAppletsRule",description = "德育小程序制度表全网适用，每一个学校都是一样的制度模块")
public class MesAppletsRuleController {
    @Autowired
    private MesAppletsRuleService mesAppletsRuleService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findMesAppletsRuleById/{id}")
    @ApiOperation(value = "通过id查找德育小程序制度表全网适用，每一个学校都是一样的制度", notes = "返回德育小程序制度表全网适用，每一个学校都是一样的制度对象")
    public MesAppletsRule findMesAppletsRuleById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return mesAppletsRuleService.findMesAppletsRuleById(id);
    }

    @PostMapping("/saveMesAppletsRule")
    @ApiOperation(value = "保存德育小程序制度表全网适用，每一个学校都是一样的制度", notes = "返回德育小程序制度表全网适用，每一个学校都是一样的制度对象")
    public ResponseJson saveMesAppletsRule(
            @ApiParam(value = "德育小程序制度表全网适用，每一个学校都是一样的制度对象", required = true)
            @RequestBody MesAppletsRule mesAppletsRule){
        return mesAppletsRuleService.saveMesAppletsRule(mesAppletsRule);
    }

    @PostMapping("/batchSaveMesAppletsRule")
    @ApiOperation(value = "批量保存德育小程序制度表全网适用，每一个学校都是一样的制度")
    public void batchSaveMesAppletsRule(
            @ApiParam(value = "德育小程序制度表全网适用，每一个学校都是一样的制度对象集合", required = true)
            @RequestBody List<MesAppletsRule> mesAppletsRules){
        mesAppletsRuleService.batchSaveMesAppletsRule(mesAppletsRules);
    }

    @PostMapping("/findMesAppletsRuleListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序制度表全网适用，每一个学校都是一样的制度列表", notes = "返回德育小程序制度表全网适用，每一个学校都是一样的制度列表")
    public List<MesAppletsRule> findMesAppletsRuleListByCondition(
            @ApiParam(value = "德育小程序制度表全网适用，每一个学校都是一样的制度对象")
            @RequestBody MesAppletsRule mesAppletsRule){
        return mesAppletsRuleService.findMesAppletsRuleListByCondition(mesAppletsRule);
    }
    @PostMapping("/findMesAppletsRuleCountByCondition")
    @ApiOperation(value = "根据条件查找德育小程序制度表全网适用，每一个学校都是一样的制度列表个数", notes = "返回德育小程序制度表全网适用，每一个学校都是一样的制度总个数")
    public long findMesAppletsRuleCountByCondition(
            @ApiParam(value = "德育小程序制度表全网适用，每一个学校都是一样的制度对象")
            @RequestBody MesAppletsRule mesAppletsRule){
        return mesAppletsRuleService.findMesAppletsRuleCountByCondition(mesAppletsRule);
    }


    @PostMapping("/updateMesAppletsRule")
    @ApiOperation(value = "修改德育小程序制度表全网适用，每一个学校都是一样的制度有值的字段", notes = "德育小程序制度表全网适用，每一个学校都是一样的制度对象必传")
    public ResponseJson updateMesAppletsRule(
            @ApiParam(value = "德育小程序制度表全网适用，每一个学校都是一样的制度对象,对象属性不为空则修改", required = true)
            @RequestBody MesAppletsRule mesAppletsRule){
       return mesAppletsRuleService.updateMesAppletsRule(mesAppletsRule);
    }
    @PostMapping("/updateMesAppletsRuleForAll")
    @ApiOperation(value = "修改德育小程序制度表全网适用，每一个学校都是一样的制度所有字段", notes = "德育小程序制度表全网适用，每一个学校都是一样的制度对象必传")
    public void updateMesAppletsRuleForAll(
            @ApiParam(value = "德育小程序制度表全网适用，每一个学校都是一样的制度对象", required = true)
            @RequestBody MesAppletsRule mesAppletsRule){
        mesAppletsRuleService.updateMesAppletsRuleForAll(mesAppletsRule);
    }

    @GetMapping("/deleteMesAppletsRule/{id}")
    @ApiOperation(value = "通过id删除德育小程序制度表全网适用，每一个学校都是一样的制度")
    public void deleteMesAppletsRule(
            @ApiParam(value = "德育小程序制度表全网适用，每一个学校都是一样的制度对象", required = true)
            @PathVariable String id){
        mesAppletsRuleService.deleteMesAppletsRule(id);
    }
    @PostMapping("/deleteMesAppletsRuleByCondition")
    @ApiOperation(value = "根据条件删除德育小程序制度表全网适用，每一个学校都是一样的制度")
    public void deleteMesAppletsRuleByCondition(
            @ApiParam(value = "德育小程序制度表全网适用，每一个学校都是一样的制度对象")
            @RequestBody MesAppletsRule mesAppletsRule){
        mesAppletsRuleService.deleteMesAppletsRuleByCondition(mesAppletsRule);
    }
    @PostMapping("/findOneMesAppletsRuleByCondition")
    @ApiOperation(value = "根据条件查找单个德育小程序制度表全网适用，每一个学校都是一样的制度,结果必须为单条数据", notes = "返回单个德育小程序制度表全网适用，每一个学校都是一样的制度,没有时为空")
    public MesAppletsRule findOneMesAppletsRuleByCondition(
            @ApiParam(value = "德育小程序制度表全网适用，每一个学校都是一样的制度对象")
            @RequestBody MesAppletsRule mesAppletsRule){
        return mesAppletsRuleService.findOneMesAppletsRuleByCondition(mesAppletsRule);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    @PostMapping("/updateMove")
    @ApiOperation(value = "修改德育小程序制度移动", notes = "修改德育小程序制度移动")
    public ResponseJson updateMove(
            @ApiParam(value = "修改德育小程序制度移动", required = true)
            @RequestBody MesAppletsRule mesAppletsRule){
        return mesAppletsRuleService.updateMove(mesAppletsRule);
    }



    @PostMapping("/findMesAppletsRulesAndStatisticDataByCondition")
    @ApiOperation(value = "根据条件查找德育制度表并计算统计数据", notes = "返回德育小程序制度表全网适用，每一个学校都是一样的制度总个数")
    public List<MesAppletsRule> findMesAppletsRulesAndStatisticDataByCondition(
            @ApiParam(value = "德育小程序制度表全网适用，每一个学校都是一样的制度对象")
            @RequestBody MesAppletsRule mesAppletsRule){
        return mesAppletsRuleService.findMesAppletsRulesAndStatisticDataByCondition(mesAppletsRule);
    }
}
