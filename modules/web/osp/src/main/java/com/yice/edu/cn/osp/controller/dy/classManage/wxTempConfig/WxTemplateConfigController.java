package com.yice.edu.cn.osp.controller.dy.classManage.wxTempConfig;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.wxTempConfig.WxTemplateConfig;
import com.yice.edu.cn.osp.service.dy.classManage.wxTempConfig.WxTemplateConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/wxTemplateConfig")
@Api(value = "/wxTemplateConfig",description = "微信模板消息配置表模块")
public class WxTemplateConfigController {
    @Autowired
    private WxTemplateConfigService wxTemplateConfigService;
    @PostMapping("/saveWxTemplateConfig")
    @ApiOperation(value = "保存微信模板消息配置表对象", notes = "返回保存好的微信模板消息配置表对象", response=WxTemplateConfig.class)
    public ResponseJson saveWxTemplateConfig(
            @ApiParam(value = "微信模板消息配置表对象", required = true)
            @RequestBody WxTemplateConfig wxTemplateConfig){
        wxTemplateConfigService.saveWxTemplateConfig(wxTemplateConfig);
        return new ResponseJson();
    }

    @GetMapping("/findWxTemplateConfigById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找微信模板消息配置表", notes = "返回响应对象", response=WxTemplateConfig.class)
    public ResponseJson findWxTemplateConfigById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        WxTemplateConfig wxTemplateConfig=wxTemplateConfigService.findWxTemplateConfigById(id);
        return new ResponseJson(wxTemplateConfig);
    }

    @PostMapping("/updateWxTemplateConfig")
    @ApiOperation(value = "修改微信模板消息配置表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateWxTemplateConfig(
            @ApiParam(value = "被修改的微信模板消息配置表对象,对象属性不为空则修改", required = true)
            @RequestBody WxTemplateConfig wxTemplateConfig){
        wxTemplateConfigService.updateWxTemplateConfig(wxTemplateConfig);
        return new ResponseJson();
    }

    @PostMapping("/updateWxTemplateConfigForAll")
    @ApiOperation(value = "修改微信模板消息配置表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateWxTemplateConfigForAll(
            @ApiParam(value = "被修改的微信模板消息配置表对象", required = true)
            @RequestBody WxTemplateConfig wxTemplateConfig){
        wxTemplateConfigService.updateWxTemplateConfigForAll(wxTemplateConfig);
        return new ResponseJson();
    }


    @PostMapping("/findWxTemplateConfigsByCondition")
    @ApiOperation(value = "根据条件查找微信模板消息配置表", notes = "返回响应对象", response=WxTemplateConfig.class)
    public ResponseJson findWxTemplateConfigsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WxTemplateConfig wxTemplateConfig){
        List<WxTemplateConfig> data=wxTemplateConfigService.findWxTemplateConfigListByCondition(wxTemplateConfig);
        long count=wxTemplateConfigService.findWxTemplateConfigCountByCondition(wxTemplateConfig);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneWxTemplateConfigByCondition")
    @ApiOperation(value = "根据条件查找单个微信模板消息配置表,结果必须为单条数据", notes = "没有时返回空", response=WxTemplateConfig.class)
    public ResponseJson findOneWxTemplateConfigByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody WxTemplateConfig wxTemplateConfig){
        WxTemplateConfig one=wxTemplateConfigService.findOneWxTemplateConfigByCondition(wxTemplateConfig);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteWxTemplateConfig/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteWxTemplateConfig(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        wxTemplateConfigService.deleteWxTemplateConfig(id);
        return new ResponseJson();
    }


    @PostMapping("/findWxTemplateConfigListByCondition")
    @ApiOperation(value = "根据条件查找微信模板消息配置表列表", notes = "返回响应对象,不包含总条数", response=WxTemplateConfig.class)
    public ResponseJson findWxTemplateConfigListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WxTemplateConfig wxTemplateConfig){
        List<WxTemplateConfig> data=wxTemplateConfigService.findWxTemplateConfigListByCondition(wxTemplateConfig);
        return new ResponseJson(data);
    }



}
