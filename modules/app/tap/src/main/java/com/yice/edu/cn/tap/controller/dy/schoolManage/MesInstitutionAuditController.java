package com.yice.edu.cn.tap.controller.dy.schoolManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesInstitutionAudit;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.dy.schoolManage.MesInstitutionAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author xiezhi
 * 操作审核 相关入口，为移动端提供服务
 */
@RestController
@RequestMapping("/mesInstitutionAudit")
@Api(value = "/mesInstitutionAudit",description = "事件审核表模块")
public class MesInstitutionAuditController {

    private final MesInstitutionAuditService mesInstitutionAuditService;

    private final CurSchoolYearService curSchoolYearService;

    @Autowired
    public MesInstitutionAuditController(MesInstitutionAuditService mesInstitutionAuditService, CurSchoolYearService curSchoolYearService) {
        this.mesInstitutionAuditService = mesInstitutionAuditService;
        this.curSchoolYearService = curSchoolYearService;
    }


    @PostMapping("/saveState")
    @ApiOperation(value = "保存事件审核表", notes = "返回事件审核表对象")
    public ResponseJson saveState(
            @ApiParam(value = "事件审核表对象", required = true)
            @RequestBody MesInstitutionAudit mesInstitutionAudit){
        if(Objects.isNull(LoginInterceptor.currentTeacher())){
            return new ResponseJson(false,"登录用户不存在");
        }
        mesInstitutionAudit.setSchoolId(LoginInterceptor.mySchoolId());
        mesInstitutionAudit.setClaimantId(LoginInterceptor.myId());
        mesInstitutionAudit.setClaimantName(LoginInterceptor.currentTeacher().getName());
        curSchoolYearService.setSchoolYearTerm(mesInstitutionAudit,LoginInterceptor.mySchoolId());
        MesInstitutionAudit institutionAudit = mesInstitutionAuditService.saveState(mesInstitutionAudit);
        return  new ResponseJson(institutionAudit);
    }


}
