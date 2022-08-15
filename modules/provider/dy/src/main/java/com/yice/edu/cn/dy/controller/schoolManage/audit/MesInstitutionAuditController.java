package com.yice.edu.cn.dy.controller.schoolManage.audit;

import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesInstitutionAudit;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.dy.service.schoolManage.audit.MesInstitutionAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mesInstitutionAudit")
@Api(value = "/mesInstitutionAudit",description = "事件审核表模块")
public class MesInstitutionAuditController {
    @Autowired
    private MesInstitutionAuditService mesInstitutionAuditService;

    @GetMapping("/findMesInstitutionAuditById/{id}")
    @ApiOperation(value = "通过id查找事件审核表", notes = "返回事件审核表对象")
    public MesInstitutionAudit findMesInstitutionAuditById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return mesInstitutionAuditService.findMesInstitutionAuditById(id);
    }

    @PostMapping("/saveMesInstitutionAudit")
    @ApiOperation(value = "保存事件审核表", notes = "返回事件审核表对象")
    public MesInstitutionAudit saveMesInstitutionAudit(
            @ApiParam(value = "事件审核表对象", required = true)
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        mesInstitutionAuditService.saveMesInstitutionAudit(mesInstitutionAudit);
        return mesInstitutionAudit;
    }

    @PostMapping("/findMesInstitutionAuditListByCondition")
    @ApiOperation(value = "根据条件查找事件审核表列表", notes = "返回事件审核表列表")
    public List<MesInstitutionAudit> findMesInstitutionAuditListByCondition(
            @ApiParam(value = "事件审核表对象")
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        return mesInstitutionAuditService.findMesInstitutionAuditListByCondition(mesInstitutionAudit);
    }
    @PostMapping("/findMesInstitutionAuditCountByCondition")
    @ApiOperation(value = "根据条件查找事件审核表列表个数", notes = "返回事件审核表总个数")
    public long findMesInstitutionAuditCountByCondition(
            @ApiParam(value = "事件审核表对象")
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        return mesInstitutionAuditService.findMesInstitutionAuditCountByCondition(mesInstitutionAudit);
    }

    @PostMapping("/updateMesInstitutionAudit")
    @ApiOperation(value = "修改事件审核表", notes = "事件审核表对象必传")
    public void updateMesInstitutionAudit(
            @ApiParam(value = "事件审核表对象,对象属性不为空则修改", required = true)
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        mesInstitutionAuditService.updateMesInstitutionAudit(mesInstitutionAudit);
    }

    @GetMapping("/deleteMesInstitutionAudit/{id}")
    @ApiOperation(value = "通过id删除事件审核表")
    public void deleteMesInstitutionAudit(
            @ApiParam(value = "事件审核表对象", required = true)
            @PathVariable String id){
        mesInstitutionAuditService.deleteMesInstitutionAudit(id);
    }

    @PostMapping("/deleteMesInstitutionAuditByCondition")
    @ApiOperation(value = "根据条件删除事件审核表")
    public void deleteMesInstitutionAuditByCondition(
            @ApiParam(value = "事件审核表对象")
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        mesInstitutionAuditService.deleteMesInstitutionAuditByCondition(mesInstitutionAudit);
    }
    @PostMapping("/findOneMesInstitutionAuditByCondition")
    @ApiOperation(value = "根据条件查找单个事件审核表,结果必须为单条数据", notes = "返回单个事件审核表,没有时为空")
    public MesInstitutionAudit findOneMesInstitutionAuditByCondition(
            @ApiParam(value = "事件审核表对象")
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        return mesInstitutionAuditService.findOneMesInstitutionAuditByCondition(mesInstitutionAudit);
    }


    @PostMapping("/lookMesInstitutionAuditById/{auditId}")
    @ApiOperation(value = "根据条件查找事件审核表列表", notes = "返回事件审核表列表")
    public Map lookMesInstitutionAuditById(
            @ApiParam(value = "事件审核表对象")
            @PathVariable String auditId){
        return mesInstitutionAuditService.lookMesInstitutionAuditById(auditId);
    }


    @GetMapping("/selectNoAudit")
    @ApiOperation(value = "查找操作记录表", notes = "返回操作记录表对象")
    public List<MesInstitutionAudit> selectNoAudit(){
        return mesInstitutionAuditService.selectNoAudit();
    }



    @PostMapping("/saveState")
    @ApiOperation(value = "保存事件审核表", notes = "返回事件审核表对象")
    public MesInstitutionAudit saveState(
            @ApiParam(value = "事件审核表对象", required = true)
            @RequestBody @Validated(GroupOne.class) MesInstitutionAudit mesInstitutionAudit){
        mesInstitutionAuditService.saveState(mesInstitutionAudit);
        return mesInstitutionAudit;
    }

    @GetMapping("/selectTeacherIdsByAuditId/{auditId}")
    @ApiOperation(value = "通过申诉id查找班主任id")
    public List<String> selectTeacherIdsByAuditId(
            @ApiParam(value = "需要用到的auditId", required = true)
            @PathVariable String auditId){
        return mesInstitutionAuditService.selectTeacherIdsByAuditId(auditId);
    }

    @PostMapping("/selectTeacherIdsByRecordId")
    @ApiOperation(value = "通过申诉对象查找班主任id")
    public List<Map> selectTeacherIdsByRecordId(
            @ApiParam(value = "操作记录表对象", required = true)
            @RequestBody List<MesInstitutionAudit> mesInstitutionAudits){
        return mesInstitutionAuditService.selectTeacherIdsByRecordId(mesInstitutionAudits);
    }

    @GetMapping("/saveMesOperateRecordBySchoolId/{schoolId}")
    @ApiOperation(value = "发布新制度待审核申诉修改为通过")
    public void saveMesOperateRecordBySchoolId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String schoolId){
        mesInstitutionAuditService.saveMesOperateRecordBySchoolId(schoolId);
    }
    @PostMapping("/findMesInstitutionAuditListCountByCondition")
    @ApiOperation(value = "根据条件查找事件审核表列表个数", notes = "返回事件审核表总个数")
    public long findMesInstitutionAuditListCountByCondition(
            @ApiParam(value = "事件审核表对象")
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        return mesInstitutionAuditService.findMesInstitutionAuditListCountByCondition(mesInstitutionAudit);
    }


    @PostMapping("/findCurrentSchoolYear/{schoolId}")
    @ApiOperation(value = "查询当前学年时间范围")
    public Map findCurrentSchoolYear(
            @PathVariable String schoolId
    ){
        return mesInstitutionAuditService.findCurrentSchoolYear(schoolId);
    }

}
