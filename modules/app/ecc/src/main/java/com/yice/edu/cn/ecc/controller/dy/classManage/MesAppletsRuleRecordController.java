package com.yice.edu.cn.ecc.controller.dy.classManage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesStudentRecordVo;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.ecc.service.dy.classManage.MesAppletsRuleRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesAppletsRuleRecord")
@Api(value = "/mesAppletsRuleRecord",description = "德育小程序点评记录表模块")
public class MesAppletsRuleRecordController {
    @Autowired
    private MesAppletsRuleRecordService mesAppletsRuleRecordService;

    @PostMapping("/saveMesAppletsRuleRecord")
    @ApiOperation(value = "保存德育小程序点评记录表对象", notes = "返回保存好的德育小程序点评记录表对象", response= MesAppletsRuleRecord.class)
    public ResponseJson saveMesAppletsRuleRecord(
            @ApiParam(value = "德育小程序点评记录表对象", required = true)
            @RequestBody @Validated(value = GroupOne.class) MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        //设置评论人职务位班干部
        mesAppletsRuleRecord.setCommentatorPost(3);
        MesAppletsRuleRecord s=mesAppletsRuleRecordService.saveMesAppletsRuleRecord(mesAppletsRuleRecord);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesAppletsRuleRecordById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找德育小程序点评记录表", notes = "返回响应对象", response=MesAppletsRuleRecord.class)
    public ResponseJson findMesAppletsRuleRecordById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        MesAppletsRuleRecord mesAppletsRuleRecord=mesAppletsRuleRecordService.findMesAppletsRuleRecordById(id);
        return new ResponseJson(mesAppletsRuleRecord);
    }

    @PostMapping("/update/updateMesAppletsRuleRecord")
    @ApiOperation(value = "修改德育小程序点评记录表对象", notes = "返回响应对象")
    public ResponseJson updateMesAppletsRuleRecord(
            @ApiParam(value = "被修改的德育小程序点评记录表对象,对象属性不为空则修改", required = true)
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecordService.updateMesAppletsRuleRecord(mesAppletsRuleRecord);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMesAppletsRuleRecordById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找德育小程序点评记录表", notes = "返回响应对象", response=MesAppletsRuleRecord.class)
    public ResponseJson lookMesAppletsRuleRecordById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MesAppletsRuleRecord mesAppletsRuleRecord=mesAppletsRuleRecordService.findMesAppletsRuleRecordById(id);
        return new ResponseJson(mesAppletsRuleRecord);
    }

    @PostMapping("/findMesAppletsRuleRecordsByCondition")
    @ApiOperation(value = "根据条件查找德育小程序点评记录表", notes = "返回响应对象", response=MesAppletsRuleRecord.class)
    public ResponseJson findMesAppletsRuleRecordsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
       mesAppletsRuleRecord.setSchoolId(mySchoolId());
        List<MesAppletsRuleRecord> data=mesAppletsRuleRecordService.findMesAppletsRuleRecordListByCondition(mesAppletsRuleRecord);
        long count=mesAppletsRuleRecordService.findMesAppletsRuleRecordCountByCondition(mesAppletsRuleRecord);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneMesAppletsRuleRecordByCondition")
    @ApiOperation(value = "根据条件查找单个德育小程序点评记录表,结果必须为单条数据", notes = "没有时返回空", response=MesAppletsRuleRecord.class)
    public ResponseJson findOneMesAppletsRuleRecordByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        MesAppletsRuleRecord one=mesAppletsRuleRecordService.findOneMesAppletsRuleRecordByCondition(mesAppletsRuleRecord);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteMesAppletsRuleRecord/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesAppletsRuleRecord(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        mesAppletsRuleRecordService.deleteMesAppletsRuleRecord(id);
        return new ResponseJson();
    }
    @PostMapping("/findClassRuleTotalList")
    @ApiOperation(value = "德育小程序总分榜单")
    public ResponseJson findClassRuleTotalList(
            @ApiParam(value = "德育小程序总分榜单")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        List<MesAppletsRuleRecord> classRuleTotalList = mesAppletsRuleRecordService.findClassRuleTotalList(mesAppletsRuleRecord);
        return new ResponseJson(classRuleTotalList);
    }
    @PostMapping("/findMesAppletsRuleRecordListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序点评记录表列表", notes = "返回响应对象,不包含总条数", response=MesAppletsRuleRecord.class)
    public ResponseJson findMesAppletsRuleRecordListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
       mesAppletsRuleRecord.setSchoolId(mySchoolId());
        List<MesAppletsRuleRecord> data=mesAppletsRuleRecordService.findMesAppletsRuleRecordListByCondition(mesAppletsRuleRecord);
        return new ResponseJson(data);
    }

    @PostMapping("/findTodayMesAppletsRuleRecordList")
    @ApiOperation(value = "查找班干部今日记录内容", notes = "返回德育小程序点评记录表列表")
    public ResponseJson findTodayMesAppletsRuleRecordList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord
    ){
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        //设置当天时间搜索区间
        mesAppletsRuleRecord.setSearchBeginTime(DateUtils.getOriginalDateTime(DateUtil.now(),1));
        mesAppletsRuleRecord.setSearchEndTime(DateUtils.getOriginalDateTime(DateUtil.now(),2));
        mesAppletsRuleRecord.setSearchType(0);//此条件为跳过时间设置
        List<MesAppletsRuleRecord> list =  mesAppletsRuleRecordService.findTodayMesAppletsRuleRecordList(mesAppletsRuleRecord);
        long count=mesAppletsRuleRecordService.findMesAppletsRuleRecordCountByCondition(mesAppletsRuleRecord);
        return new ResponseJson(list,count);
    }

    @GetMapping("/findMesStudentRecordVoListByCid/{classId}")
    @ApiOperation(value = "查询班级管理学生列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMesStudentRecordVoListByCid(
            @ApiParam(value = "班级id", required = true)
            @PathVariable("classId") String classId
    ){
        List<MesStudentRecordVo> list =  mesAppletsRuleRecordService.findMesStudentRecordVoListByCid(classId,mySchoolId());
        return new ResponseJson(list);
    }

    @GetMapping("/findJwClaCadresBySid/{studentId}")
    @ApiOperation(value = "查询当前学生是否为班干部")
    public ResponseJson findJwClaCadresBySid(
            @PathVariable("studentId") String studentId
    ){
        if(mesAppletsRuleRecordService.findJwClaCadresBySid(studentId)){
           return new ResponseJson(true,"当前学生为班干部");
        }else {
           return new ResponseJson(false,"当前学生不是班干部");
        }
    }

}
