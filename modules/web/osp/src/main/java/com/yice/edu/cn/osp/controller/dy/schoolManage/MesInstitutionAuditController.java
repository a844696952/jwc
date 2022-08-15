package com.yice.edu.cn.osp.controller.dy.schoolManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesInstitutionAudit;
import com.yice.edu.cn.osp.service.dy.schoolManage.MesInstitutionAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesInstitutionAudit")
@Api(value = "/mesInstitutionAudit",description = "事件审核表模块")
public class MesInstitutionAuditController {
    @Autowired
    private MesInstitutionAuditService mesInstitutionAuditService;

    @PostMapping("/saveMesInstitutionAudit")
    @ApiOperation(value = "保存事件审核表对象", notes = "返回保存好的事件审核表对象", response= MesInstitutionAudit.class)
    public ResponseJson saveMesInstitutionAudit(
            @ApiParam(value = "事件审核表对象", required = true)
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
       mesInstitutionAudit.setSchoolId(mySchoolId());
        MesInstitutionAudit s=mesInstitutionAuditService.saveMesInstitutionAudit(mesInstitutionAudit);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesInstitutionAuditById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找事件审核表", notes = "返回响应对象", response=MesInstitutionAudit.class)
    public ResponseJson findMesInstitutionAuditById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        MesInstitutionAudit mesInstitutionAudit=mesInstitutionAuditService.findMesInstitutionAuditById(id);
        return new ResponseJson(mesInstitutionAudit);
    }

    @PostMapping("/update/updateMesInstitutionAudit")
    @ApiOperation(value = "修改事件审核表对象", notes = "返回响应对象")
    public ResponseJson updateMesInstitutionAudit(
            @ApiParam(value = "被修改的事件审核表对象,对象属性不为空则修改", required = true)
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        mesInstitutionAuditService.updateMesInstitutionAudit(mesInstitutionAudit);
        return new ResponseJson();
    }


    @PostMapping("/findMesInstitutionAuditsByCondition")
    @ApiOperation(value = "根据条件查找事件审核表", notes = "返回响应对象", response=MesInstitutionAudit.class)
    public ResponseJson findMesInstitutionAuditsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        mesInstitutionAudit.setSchoolId(mySchoolId());
        List<MesInstitutionAudit> data=mesInstitutionAuditService.findMesInstitutionAuditListByCondition(mesInstitutionAudit);
        long count=mesInstitutionAuditService.findMesInstitutionAuditCountByCondition(mesInstitutionAudit);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneMesInstitutionAuditByCondition")
    @ApiOperation(value = "根据条件查找单个事件审核表,结果必须为单条数据", notes = "没有时返回空", response=MesInstitutionAudit.class)
    public ResponseJson findOneMesInstitutionAuditByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        MesInstitutionAudit one=mesInstitutionAuditService.findOneMesInstitutionAuditByCondition(mesInstitutionAudit);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteMesInstitutionAudit/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesInstitutionAudit(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        mesInstitutionAuditService.deleteMesInstitutionAudit(id);
        return new ResponseJson();
    }


    @PostMapping("/findMesInstitutionAuditListByCondition")
    @ApiOperation(value = "根据条件查找事件审核表列表", notes = "返回响应对象,不包含总条数", response=MesInstitutionAudit.class)
    public ResponseJson findMesInstitutionAuditListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        mesInstitutionAudit.setSchoolId(mySchoolId());
        List<MesInstitutionAudit> data=mesInstitutionAuditService.findMesInstitutionAuditListByCondition(mesInstitutionAudit);
        long count = mesInstitutionAuditService.findMesInstitutionAuditListCountByCondition(mesInstitutionAudit);
        return new ResponseJson(data,count);
    }

    @PostMapping("/look/lookMesInstitutionAuditById/{auditId}")
    @ApiOperation(value = "去查看页面,通过检查记录id查找事件审核表", notes = "返回响应对象", response= MesInspectRecord.class)
    public ResponseJson lookMesInstitutionAuditByIdAndRecordId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @PathVariable String auditId){
        Map map=mesInstitutionAuditService.lookMesInstitutionAuditById(auditId);
        return new ResponseJson(map);
    }

    @PostMapping("/findCurrentSchoolYear")
    @ApiOperation(value = "查询当前学年时间范围")
    public ResponseJson findCurrentSchoolYear(){
        Map map = mesInstitutionAuditService.findCurrentSchoolYear(mySchoolId());
        return new ResponseJson(map);
    }






}
