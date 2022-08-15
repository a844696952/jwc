package com.yice.edu.cn.dy.controller.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.dy.service.schoolManage.institution.MesUserAuthInstitutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesUserAuthInstitution")
@Api(value = "/mesUserAuthInstitution", description = "用户制度权限表模块")
public class MesUserAuthInstitutionController {
    @Autowired
    private MesUserAuthInstitutionService mesUserAuthInstitutionService;

    @GetMapping("/findMesUserAuthInstitutionById/{id}")
    @ApiOperation(value = "通过id查找用户制度权限表", notes = "返回用户制度权限表对象")
    public MesUserAuthInstitution findMesUserAuthInstitutionById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return mesUserAuthInstitutionService.findMesUserAuthInstitutionById(id);
    }

    @PostMapping("/saveMesUserAuthInstitution")
    @ApiOperation(value = "保存用户制度权限表", notes = "返回用户制度权限表对象")
    public MesUserAuthInstitution saveMesUserAuthInstitution(
            @ApiParam(value = "用户制度权限表对象", required = true)
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution) {
        mesUserAuthInstitutionService.saveMesUserAuthInstitution(mesUserAuthInstitution);
        return mesUserAuthInstitution;
    }

    @PostMapping("/findMesUserAuthInstitutionListByCondition")
    @ApiOperation(value = "根据条件查找用户制度权限表列表", notes = "返回用户制度权限表列表")
    public List<MesUserAuthInstitution> findMesUserAuthInstitutionListByCondition(
            @ApiParam(value = "用户制度权限表对象")
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution) {
        return mesUserAuthInstitutionService.findMesUserAuthInstitutionListByCondition(mesUserAuthInstitution);
    }

    @PostMapping("/findMesUserAuthInstitutionCountByCondition")
    @ApiOperation(value = "根据条件查找用户制度权限表列表个数", notes = "返回用户制度权限表总个数")
    public long findMesUserAuthInstitutionCountByCondition(
            @ApiParam(value = "用户制度权限表对象")
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution) {
        return mesUserAuthInstitutionService.findMesUserAuthInstitutionCountByCondition(mesUserAuthInstitution);
    }

    @PostMapping("/updateMesUserAuthInstitution")
    @ApiOperation(value = "修改用户制度权限表", notes = "用户制度权限表对象必传")
    public void updateMesUserAuthInstitution(
            @ApiParam(value = "用户制度权限表对象,对象属性不为空则修改", required = true)
            @RequestBody MesInstitution mesInstitution) {
        mesUserAuthInstitutionService.updateMesUserAuthInstitution(mesInstitution);
    }

    @GetMapping("/deleteMesUserAuthInstitution/{id}")
    @ApiOperation(value = "通过id删除用户制度权限表")
    public void deleteMesUserAuthInstitution(
            @ApiParam(value = "用户制度权限表对象", required = true)
            @PathVariable String id) {
        mesUserAuthInstitutionService.deleteMesUserAuthInstitution(id);
    }

    @PostMapping("/deleteMesUserAuthInstitutionByCondition")
    @ApiOperation(value = "根据条件删除用户制度权限表")
    public void deleteMesUserAuthInstitutionByCondition(
            @ApiParam(value = "用户制度权限表对象")
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution) {
        mesUserAuthInstitutionService.deleteMesUserAuthInstitutionByCondition(mesUserAuthInstitution);
    }

    @PostMapping("/findOneMesUserAuthInstitutionByCondition")
    @ApiOperation(value = "根据条件查找单个用户制度权限表,结果必须为单条数据", notes = "返回单个用户制度权限表,没有时为空")
    public MesUserAuthInstitution findOneMesUserAuthInstitutionByCondition(
            @ApiParam(value = "用户制度权限表对象")
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution) {
        return mesUserAuthInstitutionService.findOneMesUserAuthInstitutionByCondition(mesUserAuthInstitution);
    }

    @PostMapping("/haveCheckPermission")
    @ApiOperation(value = "查询用户是否有检查权限")
    public Boolean haveCheckPermission(
            @ApiParam(value = "用户制度权限表对象")
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution) {
        long num = mesUserAuthInstitutionService.haveCheckPermission(mesUserAuthInstitution);
        return num > 0;
    }

}
