package com.yice.edu.cn.tap.controller.dy.classManage;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsClassRule;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsRule;
import com.yice.edu.cn.tap.service.dy.classManage.MesAppletsClassRuleService;
import com.yice.edu.cn.tap.service.dy.classManage.MesAppletsRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesAppletsClassRule")
@Api(value = "/mesAppletsClassRule",description = "德育小程序班级制度表模块")
public class MesAppletsClassRuleController {
    @Autowired
    private MesAppletsClassRuleService mesAppletsClassRuleService;

    @Autowired
    private MesAppletsRuleService mesAppletsRuleService;


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



    @GetMapping("/findMesAppletsRuleById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找德育小程序制度表全网适用，每一个学校都是一样的制度", notes = "返回响应对象", response= MesAppletsRule.class)
    public ResponseJson findMesAppletsRuleById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        MesAppletsRule mesAppletsRule=mesAppletsRuleService.findMesAppletsRuleById(id);
        return new ResponseJson(mesAppletsRule);
    }


    @PostMapping("/move")
    @ApiOperation(value = "前端移动小程序班规", notes = "返回保存好的德育小程序班级制度表对象", response=ResponseJson.class)
    public ResponseJson move(
            @ApiParam(value = "德育小程序班级制度表对象", required = true)
            @RequestBody  List<MesAppletsClassRule>  mesAppletsClassRule){
        if(CollUtil.isEmpty(mesAppletsClassRule)){
            return new ResponseJson(false,"参数错误");
        }
        mesAppletsClassRuleService.move(mesAppletsClassRule);
        return new ResponseJson();
    }


   /* @PostMapping("/push")
    @ApiOperation(value = "测试的微信推送", notes = "对应信息")
    public ResponseJson push(){
        *//*String accessToken = wxUtilService.getAccessToken(Constant.DYWECHAT.DY_TEACHER_APPID,Constant.DYWECHAT.
                DY_TEACHER_APPSERECT,Constant.DYWECHAT.DY_PARENTS_REDIS_KEY);
        Map<String, WxData> map = WxUtil.structureDataByMessage("张三", -6, "天天打野不搞正事");
        WxPushDetail wxPushDetail = WxUtil.push(accessToken, Constant.DYWECHAT.DY_TEACHER_MESSAGE_TEM_ID,
                "oVCxL5ILY-63TKOtQhhxKjVQQdV0", map, "pages/index/index");*//*
        //wxPushDetail 需要save一次到mongoDB

        return new ResponseJson();
    }*/

}
