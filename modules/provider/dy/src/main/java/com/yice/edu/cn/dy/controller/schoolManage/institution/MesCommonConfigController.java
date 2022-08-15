package com.yice.edu.cn.dy.controller.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import com.yice.edu.cn.dy.service.schoolManage.institution.MesCommonConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesCommonConfig")
@Api(value = "/mesCommonConfig", description = "公共配置表模块")
public class MesCommonConfigController {
    @Autowired
    private MesCommonConfigService mesCommonConfigService;

    @GetMapping("/findMesCommonConfigById/{id}")
    @ApiOperation(value = "通过id查找公共配置表", notes = "返回公共配置表对象")
    public MesCommonConfig findMesCommonConfigById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return mesCommonConfigService.findMesCommonConfigById(id);
    }

    @PostMapping("/saveMesCommonConfig")
    @ApiOperation(value = "保存公共配置表", notes = "返回公共配置表对象")
    public MesCommonConfig saveMesCommonConfig(
            @ApiParam(value = "发布制度", required = true)
            @RequestBody MesCommonConfig mesCommonConfig) {
        mesCommonConfigService.saveMesCommonConfig(mesCommonConfig);
        return mesCommonConfig;
    }

    @PostMapping("/findMesCommonConfigListByCondition")
    @ApiOperation(value = "根据条件查找公共配置表列表", notes = "返回公共配置表列表")
    public List<MesCommonConfig> findMesCommonConfigListByCondition(
            @ApiParam(value = "公共配置表对象")
            @RequestBody MesCommonConfig mesCommonConfig) {
        return mesCommonConfigService.findMesCommonConfigListByCondition(mesCommonConfig);
    }

    @PostMapping("/findMesCommonConfigCountByCondition")
    @ApiOperation(value = "根据条件查找公共配置表列表个数", notes = "返回公共配置表总个数")
    public long findMesCommonConfigCountByCondition(
            @ApiParam(value = "公共配置表对象")
            @RequestBody MesCommonConfig mesCommonConfig) {
        return mesCommonConfigService.findMesCommonConfigCountByCondition(mesCommonConfig);
    }

    @PostMapping("/updateMesCommonConfig")
    @ApiOperation(value = "修改公共配置表", notes = "公共配置表对象必传")
    public void updateMesCommonConfig(
            @ApiParam(value = "公共配置表对象,对象属性不为空则修改", required = true)
            @RequestBody MesCommonConfig mesCommonConfig) {
        mesCommonConfigService.updateMesCommonConfig(mesCommonConfig);
    }

    @GetMapping("/deleteMesCommonConfig/{id}")
    @ApiOperation(value = "通过id删除公共配置表")
    public void deleteMesCommonConfig(
            @ApiParam(value = "公共配置表对象", required = true)
            @PathVariable String id) {
        mesCommonConfigService.deleteMesCommonConfig(id);
    }

    @PostMapping("/deleteMesCommonConfigByCondition")
    @ApiOperation(value = "根据条件删除公共配置表")
    public void deleteMesCommonConfigByCondition(
            @ApiParam(value = "公共配置表对象")
            @RequestBody MesCommonConfig mesCommonConfig) {
        mesCommonConfigService.deleteMesCommonConfigByCondition(mesCommonConfig);
    }

    @PostMapping("/findOneMesCommonConfigByCondition")
    @ApiOperation(value = "根据条件查找单个公共配置表,结果必须为单条数据", notes = "返回单个公共配置表,没有时为空")
    public MesCommonConfig findOneMesCommonConfigByCondition(
            @ApiParam(value = "公共配置表对象")
            @RequestBody MesCommonConfig mesCommonConfig) {
        return mesCommonConfigService.findOneMesCommonConfigByCondition(mesCommonConfig);
    }
}
