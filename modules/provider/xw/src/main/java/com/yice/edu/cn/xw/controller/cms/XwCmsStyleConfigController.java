package com.yice.edu.cn.xw.controller.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsStyleConfig;
import com.yice.edu.cn.xw.service.cms.XwCmsStyleConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwCmsStyleConfig")
@Api(value = "/xwCmsStyleConfig", description = "系统风格配置表模块")
public class XwCmsStyleConfigController {
    @Autowired
    private XwCmsStyleConfigService xwCmsStyleConfigService;

    @GetMapping("/findXwCmsStyleConfigById/{id}")
    @ApiOperation(value = "通过id查找系统风格配置表", notes = "返回系统风格配置表对象")
    public XwCmsStyleConfig findXwCmsStyleConfigById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return xwCmsStyleConfigService.findXwCmsStyleConfigById(id);
    }

    @PostMapping("/saveXwCmsStyleConfig")
    @ApiOperation(value = "保存系统风格配置表", notes = "返回系统风格配置表对象")
    public XwCmsStyleConfig saveXwCmsStyleConfig(
            @ApiParam(value = "系统风格配置表对象", required = true)
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        xwCmsStyleConfigService.saveXwCmsStyleConfig(xwCmsStyleConfig);
        return xwCmsStyleConfig;
    }

    @PostMapping("/findXwCmsStyleConfigListByCondition")
    @ApiOperation(value = "根据条件查找系统风格配置表列表", notes = "返回系统风格配置表列表")
    public List<XwCmsStyleConfig> findXwCmsStyleConfigListByCondition(
            @ApiParam(value = "系统风格配置表对象")
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        return xwCmsStyleConfigService.findXwCmsStyleConfigListByCondition(xwCmsStyleConfig);
    }

    @PostMapping("/findXwCmsStyleConfigCountByCondition")
    @ApiOperation(value = "根据条件查找系统风格配置表列表个数", notes = "返回系统风格配置表总个数")
    public long findXwCmsStyleConfigCountByCondition(
            @ApiParam(value = "系统风格配置表对象")
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        return xwCmsStyleConfigService.findXwCmsStyleConfigCountByCondition(xwCmsStyleConfig);
    }

    @PostMapping("/updateXwCmsStyleConfig")
    @ApiOperation(value = "修改系统风格配置表", notes = "系统风格配置表对象必传")
    public void updateXwCmsStyleConfig(
            @ApiParam(value = "系统风格配置表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        xwCmsStyleConfigService.updateXwCmsStyleConfig(xwCmsStyleConfig);
    }

    @GetMapping("/deleteXwCmsStyleConfig/{id}")
    @ApiOperation(value = "通过id删除系统风格配置表")
    public void deleteXwCmsStyleConfig(
            @ApiParam(value = "系统风格配置表对象", required = true)
            @PathVariable String id) {
        xwCmsStyleConfigService.deleteXwCmsStyleConfig(id);
    }

    @PostMapping("/deleteXwCmsStyleConfigByCondition")
    @ApiOperation(value = "根据条件删除系统风格配置表")
    public void deleteXwCmsStyleConfigByCondition(
            @ApiParam(value = "系统风格配置表对象")
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        xwCmsStyleConfigService.deleteXwCmsStyleConfigByCondition(xwCmsStyleConfig);
    }

    @PostMapping("/findOneXwCmsStyleConfigByCondition")
    @ApiOperation(value = "根据条件查找单个系统风格配置表,结果必须为单条数据", notes = "返回单个系统风格配置表,没有时为空")
    public XwCmsStyleConfig findOneXwCmsStyleConfigByCondition(
            @ApiParam(value = "系统风格配置表对象")
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        return xwCmsStyleConfigService.findOneXwCmsStyleConfigByCondition(xwCmsStyleConfig);
    }

    @PostMapping("/findXwCmsStyleConfigByDomain")
    @ApiOperation(value = "根据条件查找单个系统风格配置表,结果必须为单条数据", notes = "返回单个系统风格配置表,没有时为空")
    public XwCmsStyleConfig findXwCmsStyleConfigByDomain(
            @ApiParam(value = "系统风格配置表对象")
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        return xwCmsStyleConfigService.findXwCmsStyleConfigByDomain(xwCmsStyleConfig);
    }

}
