package com.yice.edu.cn.osp.controller.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import com.yice.edu.cn.osp.service.dy.schoolManage.institution.MesCommonConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesCommonConfig")
@Api(value = "/mesCommonConfig", description = "公共配置表模块")
public class MesCommonConfigController {
    @Autowired
    private MesCommonConfigService mesCommonConfigService;

    @PostMapping("/saveMesCommonConfig")
    @ApiOperation(value = "保存公共配置表对象", notes = "返回保存好的公共配置表对象", response = MesCommonConfig.class)
    public ResponseJson saveMesCommonConfig(
            @ApiParam(value = "公共配置表对象", required = true)
            @RequestBody MesCommonConfig mesCommonConfig) {
        mesCommonConfig.setSchoolId(mySchoolId());
        MesCommonConfig s = mesCommonConfigService.saveMesCommonConfig(mesCommonConfig);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesCommonConfigById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找公共配置表", notes = "返回响应对象", response = MesCommonConfig.class)
    public ResponseJson findMesCommonConfigById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesCommonConfig mesCommonConfig = mesCommonConfigService.findMesCommonConfigById(id);
        return new ResponseJson(mesCommonConfig);
    }

    @PostMapping("/update/updateMesCommonConfig")
    @ApiOperation(value = "修改公共配置表对象", notes = "返回响应对象")
    public ResponseJson updateMesCommonConfig(
            @ApiParam(value = "被修改的公共配置表对象,对象属性不为空则修改", required = true)
            @RequestBody MesCommonConfig mesCommonConfig) {
        mesCommonConfig.setSchoolId(mySchoolId());
        mesCommonConfigService.updateMesCommonConfig(mesCommonConfig);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMesCommonConfigById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找公共配置表", notes = "返回响应对象", response = MesCommonConfig.class)
    public ResponseJson lookMesCommonConfigById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesCommonConfig mesCommonConfig = mesCommonConfigService.findMesCommonConfigById(id);
        return new ResponseJson(mesCommonConfig);
    }

    @PostMapping("/findMesCommonConfigsByCondition")
    @ApiOperation(value = "根据条件查找公共配置表", notes = "返回响应对象", response = MesCommonConfig.class)
    public ResponseJson findMesCommonConfigsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesCommonConfig mesCommonConfig) {
        mesCommonConfig.setSchoolId(mySchoolId());
        List<MesCommonConfig> data = mesCommonConfigService.findMesCommonConfigListByCondition(mesCommonConfig);
        long count = mesCommonConfigService.findMesCommonConfigCountByCondition(mesCommonConfig);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneMesCommonConfigByCondition")
    @ApiOperation(value = "根据条件查找单个公共配置表,结果必须为单条数据", notes = "没有时返回空", response = MesCommonConfig.class)
    public ResponseJson findOneMesCommonConfigByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesCommonConfig mesCommonConfig) {
        mesCommonConfig.setSchoolId(mySchoolId());
        MesCommonConfig one = mesCommonConfigService.findOneMesCommonConfigByCondition(mesCommonConfig);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteMesCommonConfig/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesCommonConfig(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        mesCommonConfigService.deleteMesCommonConfig(id);
        return new ResponseJson();
    }


    @PostMapping("/findMesCommonConfigListByCondition")
    @ApiOperation(value = "根据条件查找公共配置表列表", notes = "返回响应对象,不包含总条数", response = MesCommonConfig.class)
    public ResponseJson findMesCommonConfigListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesCommonConfig mesCommonConfig) {
        mesCommonConfig.setSchoolId(mySchoolId());
        List<MesCommonConfig> data = mesCommonConfigService.findMesCommonConfigListByCondition(mesCommonConfig);
        return new ResponseJson(data);
    }








}
