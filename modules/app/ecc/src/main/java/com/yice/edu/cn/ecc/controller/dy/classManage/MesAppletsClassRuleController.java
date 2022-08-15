package com.yice.edu.cn.ecc.controller.dy.classManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsClassRule;
import com.yice.edu.cn.ecc.service.dy.classManage.MesAppletsClassRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesAppletsClassRule")
@Api(value = "/mesAppletsClassRule",description = "德育小程序班级制度表模块")
public class MesAppletsClassRuleController {
    @Autowired
    private MesAppletsClassRuleService mesAppletsClassRuleService;



    @PostMapping("/saveMesAppletsClassRule")
    @ApiOperation(value = "保存德育小程序班级制度表对象", notes = "返回保存好的德育小程序班级制度表对象", response=MesAppletsClassRule.class)
    public ResponseJson saveMesAppletsClassRule(
            @ApiParam(value = "德育小程序班级制度表对象", required = true)
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
       mesAppletsClassRule.setSchoolId(mySchoolId());
        MesAppletsClassRule s=mesAppletsClassRuleService.saveMesAppletsClassRule(mesAppletsClassRule);
        return new ResponseJson(s);
    }


    @PostMapping("/updateMesAppletsClassRule")
    @ApiOperation(value = "修改德育小程序班级制度表对象", notes = "返回响应对象")
    public ResponseJson updateMesAppletsClassRule(
            @ApiParam(value = "被修改的德育小程序班级制度表对象,对象属性不为空则修改", required = true)
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
        mesAppletsClassRuleService.updateMesAppletsClassRule(mesAppletsClassRule);
        return new ResponseJson();
    }

    @GetMapping("/findMesAppletsClassRuleById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找德育小程序班级制度表", notes = "返回响应对象", response=MesAppletsClassRule.class)
    public ResponseJson findMesAppletsClassRuleById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MesAppletsClassRule mesAppletsClassRule=mesAppletsClassRuleService.findMesAppletsClassRuleById(id);
        return new ResponseJson(mesAppletsClassRule);
    }

    @GetMapping("/deleteMesAppletsClassRule/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesAppletsClassRule(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        mesAppletsClassRuleService.deleteMesAppletsClassRule(id);
        return new ResponseJson();
    }


    @PostMapping("/findMesAppletsClassRuleListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序班级制度表列表", notes = "返回响应对象,不包含总条数", response=MesAppletsClassRule.class)
    public ResponseJson findMesAppletsClassRuleListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
       mesAppletsClassRule.setSchoolId(mySchoolId());
        List<MesAppletsClassRule> data=mesAppletsClassRuleService.findMesAppletsClassRuleListByCondition(mesAppletsClassRule);
        return new ResponseJson(data);
    }




    @PostMapping("/index")
    @ApiOperation(value = "班主任进入调整班规的主页面", notes = "返回响应对象,不包含总条数", response=MesAppletsClassRule.class)
    public ResponseJson index(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsClassRule mesAppletsClassRule){
        mesAppletsClassRule.setSchoolId(mySchoolId());
        return mesAppletsClassRuleService.index(mesAppletsClassRule);
    }



}
