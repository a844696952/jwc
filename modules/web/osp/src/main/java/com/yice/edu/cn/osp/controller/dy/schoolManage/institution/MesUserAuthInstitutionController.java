package com.yice.edu.cn.osp.controller.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.osp.service.dy.schoolManage.institution.MesUserAuthInstitutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesUserAuthInstitution")
@Api(value = "/mesUserAuthInstitution", description = "用户制度权限表模块")
public class MesUserAuthInstitutionController {
    @Autowired
    private MesUserAuthInstitutionService mesUserAuthInstitutionService;

    @PostMapping("/saveMesUserAuthInstitution")
    @ApiOperation(value = "保存用户制度权限表对象", notes = "返回保存好的用户制度权限表对象", response = MesUserAuthInstitution.class)
    public ResponseJson saveMesUserAuthInstitution(
            @ApiParam(value = "用户制度权限表对象", required = true)
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution) {
        mesUserAuthInstitution.setSchoolId(mySchoolId());
        MesUserAuthInstitution s = mesUserAuthInstitutionService.saveMesUserAuthInstitution(mesUserAuthInstitution);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesUserAuthInstitutionById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找用户制度权限表", notes = "返回响应对象", response = MesUserAuthInstitution.class)
    public ResponseJson findMesUserAuthInstitutionById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesUserAuthInstitution mesUserAuthInstitution = mesUserAuthInstitutionService.findMesUserAuthInstitutionById(id);
        return new ResponseJson(mesUserAuthInstitution);
    }

    @PostMapping("/update/updateMesUserAuthInstitution")
    @ApiOperation(value = "修改用户制度权限表对象", notes = "返回响应对象")
    public ResponseJson updateMesUserAuthInstitution(
            @ApiParam(value = "被修改的用户制度权限表对象,对象属性不为空则修改", required = true)
            @RequestBody MesInstitution mesInstitution) {
        mesInstitution.setSchoolId(mySchoolId());
        mesUserAuthInstitutionService.updateMesUserAuthInstitution(mesInstitution);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMesUserAuthInstitutionById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找用户制度权限表", notes = "返回响应对象", response = MesUserAuthInstitution.class)
    public ResponseJson lookMesUserAuthInstitutionById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesUserAuthInstitution mesUserAuthInstitution = mesUserAuthInstitutionService.findMesUserAuthInstitutionById(id);
        return new ResponseJson(mesUserAuthInstitution);
    }

    @PostMapping("/findMesUserAuthInstitutionsByCondition")
    @ApiOperation(value = "根据条件查找用户制度权限表", notes = "返回响应对象", response = MesUserAuthInstitution.class)
    public ResponseJson findMesUserAuthInstitutionsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution) {
        mesUserAuthInstitution.setSchoolId(mySchoolId());
        List<MesUserAuthInstitution> data = mesUserAuthInstitutionService.findMesUserAuthInstitutionListByCondition(mesUserAuthInstitution);
        long count = mesUserAuthInstitutionService.findMesUserAuthInstitutionCountByCondition(mesUserAuthInstitution);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneMesUserAuthInstitutionByCondition")
    @ApiOperation(value = "根据条件查找单个用户制度权限表,结果必须为单条数据", notes = "没有时返回空", response = MesUserAuthInstitution.class)
    public ResponseJson findOneMesUserAuthInstitutionByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution) {
        mesUserAuthInstitution.setSchoolId(mySchoolId());
        MesUserAuthInstitution one = mesUserAuthInstitutionService.findOneMesUserAuthInstitutionByCondition(mesUserAuthInstitution);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteMesUserAuthInstitution/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesUserAuthInstitution(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        mesUserAuthInstitutionService.deleteMesUserAuthInstitution(id);
        return new ResponseJson();
    }


    @PostMapping("/findMesUserAuthInstitutionListByCondition")
    @ApiOperation(value = "根据条件查找用户制度权限表列表", notes = "返回响应对象,不包含总条数", response = MesUserAuthInstitution.class)
    public ResponseJson findMesUserAuthInstitutionListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution) {
        mesUserAuthInstitution.setSchoolId(mySchoolId());
        List<MesUserAuthInstitution> data = mesUserAuthInstitutionService.findMesUserAuthInstitutionListByCondition(mesUserAuthInstitution);
        return new ResponseJson(data);
    }


}
