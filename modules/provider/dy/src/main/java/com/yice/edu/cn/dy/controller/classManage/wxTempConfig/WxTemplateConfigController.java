package com.yice.edu.cn.dy.controller.classManage.wxTempConfig;

import com.yice.edu.cn.common.pojo.mes.classManage.wxTempConfig.WxTemplateConfig;
import com.yice.edu.cn.dy.service.classManage.wxTempConfig.WxTemplateConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wxTemplateConfig")
@Api(value = "/wxTemplateConfig",description = "微信模板消息配置表模块")
public class WxTemplateConfigController {
    @Autowired
    private WxTemplateConfigService wxTemplateConfigService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findWxTemplateConfigById/{id}")
    @ApiOperation(value = "通过id查找微信模板消息配置表", notes = "返回微信模板消息配置表对象")
    public WxTemplateConfig findWxTemplateConfigById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return wxTemplateConfigService.findWxTemplateConfigById(id);
    }

    @PostMapping("/saveWxTemplateConfig")
    @ApiOperation(value = "保存微信模板消息配置表", notes = "返回微信模板消息配置表对象")
    public WxTemplateConfig saveWxTemplateConfig(
            @ApiParam(value = "微信模板消息配置表对象", required = true)
            @RequestBody WxTemplateConfig wxTemplateConfig){
        wxTemplateConfigService.saveWxTemplateConfig(wxTemplateConfig);
        return wxTemplateConfig;
    }

    @PostMapping("/batchSaveWxTemplateConfig")
    @ApiOperation(value = "批量保存微信模板消息配置表")
    public void batchSaveWxTemplateConfig(
            @ApiParam(value = "微信模板消息配置表对象集合", required = true)
            @RequestBody List<WxTemplateConfig> wxTemplateConfigs){
        wxTemplateConfigService.batchSaveWxTemplateConfig(wxTemplateConfigs);
    }

    @PostMapping("/findWxTemplateConfigListByCondition")
    @ApiOperation(value = "根据条件查找微信模板消息配置表列表", notes = "返回微信模板消息配置表列表")
    public List<WxTemplateConfig> findWxTemplateConfigListByCondition(
            @ApiParam(value = "微信模板消息配置表对象")
            @RequestBody WxTemplateConfig wxTemplateConfig){
        return wxTemplateConfigService.findWxTemplateConfigListByCondition(wxTemplateConfig);
    }
    @PostMapping("/findWxTemplateConfigCountByCondition")
    @ApiOperation(value = "根据条件查找微信模板消息配置表列表个数", notes = "返回微信模板消息配置表总个数")
    public long findWxTemplateConfigCountByCondition(
            @ApiParam(value = "微信模板消息配置表对象")
            @RequestBody WxTemplateConfig wxTemplateConfig){
        return wxTemplateConfigService.findWxTemplateConfigCountByCondition(wxTemplateConfig);
    }

    @PostMapping("/updateWxTemplateConfig")
    @ApiOperation(value = "修改微信模板消息配置表有值的字段", notes = "微信模板消息配置表对象必传")
    public void updateWxTemplateConfig(
            @ApiParam(value = "微信模板消息配置表对象,对象属性不为空则修改", required = true)
            @RequestBody WxTemplateConfig wxTemplateConfig){
        wxTemplateConfigService.updateWxTemplateConfig(wxTemplateConfig);
    }
    @PostMapping("/updateWxTemplateConfigForAll")
    @ApiOperation(value = "修改微信模板消息配置表所有字段", notes = "微信模板消息配置表对象必传")
    public void updateWxTemplateConfigForAll(
            @ApiParam(value = "微信模板消息配置表对象", required = true)
            @RequestBody WxTemplateConfig wxTemplateConfig){
        wxTemplateConfigService.updateWxTemplateConfigForAll(wxTemplateConfig);
    }

    @GetMapping("/deleteWxTemplateConfig/{id}")
    @ApiOperation(value = "通过id删除微信模板消息配置表")
    public void deleteWxTemplateConfig(
            @ApiParam(value = "微信模板消息配置表对象", required = true)
            @PathVariable String id){
        wxTemplateConfigService.deleteWxTemplateConfig(id);
    }
    @PostMapping("/deleteWxTemplateConfigByCondition")
    @ApiOperation(value = "根据条件删除微信模板消息配置表")
    public void deleteWxTemplateConfigByCondition(
            @ApiParam(value = "微信模板消息配置表对象")
            @RequestBody WxTemplateConfig wxTemplateConfig){
        wxTemplateConfigService.deleteWxTemplateConfigByCondition(wxTemplateConfig);
    }
    @PostMapping("/findOneWxTemplateConfigByCondition")
    @ApiOperation(value = "根据条件查找单个微信模板消息配置表,结果必须为单条数据", notes = "返回单个微信模板消息配置表,没有时为空")
    public WxTemplateConfig findOneWxTemplateConfigByCondition(
            @ApiParam(value = "微信模板消息配置表对象")
            @RequestBody WxTemplateConfig wxTemplateConfig){
        return wxTemplateConfigService.findOneWxTemplateConfigByCondition(wxTemplateConfig);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

}
