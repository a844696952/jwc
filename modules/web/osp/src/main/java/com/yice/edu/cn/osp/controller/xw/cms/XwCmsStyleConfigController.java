package com.yice.edu.cn.osp.controller.xw.cms;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsStyleConfig;
import com.yice.edu.cn.osp.service.xw.cms.XwCmsStyleConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwCmsStyleConfig")
@Api(value = "/xwCmsStyleConfig", description = "系统风格配置表模块")
public class XwCmsStyleConfigController {
    @Autowired
    private XwCmsStyleConfigService xwCmsStyleConfigService;

    @PostMapping("/saveXwCmsStyleConfig")
    @ApiOperation(value = "保存系统风格配置表对象", notes = "返回保存好的系统风格配置表对象", response = XwCmsStyleConfig.class)
    public ResponseJson saveXwCmsStyleConfig(
            @ApiParam(value = "系统风格配置表对象", required = true)
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        xwCmsStyleConfig.setSchoolId(mySchoolId());
        XwCmsStyleConfig data = xwCmsStyleConfigService.saveXwCmsStyleConfig(xwCmsStyleConfig);
        return new ResponseJson(data);
    }

    @GetMapping("/update/findXwCmsStyleConfigById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找系统风格配置表", notes = "返回响应对象", response = XwCmsStyleConfig.class)
    public ResponseJson findXwCmsStyleConfigById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwCmsStyleConfig xwCmsStyleConfig = xwCmsStyleConfigService.findXwCmsStyleConfigById(id);
        return new ResponseJson(xwCmsStyleConfig);
    }

    @PostMapping("/update/updateXwCmsStyleConfig")
    @ApiOperation(value = "修改系统风格配置表对象", notes = "返回响应对象")
    public ResponseJson updateXwCmsStyleConfig(
            @ApiParam(value = "被修改的系统风格配置表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        xwCmsStyleConfigService.updateXwCmsStyleConfig(xwCmsStyleConfig);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwCmsStyleConfigById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找系统风格配置表", notes = "返回响应对象", response = XwCmsStyleConfig.class)
    public ResponseJson lookXwCmsStyleConfigById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwCmsStyleConfig xwCmsStyleConfig = xwCmsStyleConfigService.findXwCmsStyleConfigById(id);
        return new ResponseJson(xwCmsStyleConfig);
    }

    @PostMapping("/findXwCmsStyleConfigsByCondition")
    @ApiOperation(value = "根据条件查找系统风格配置表", notes = "返回响应对象", response = XwCmsStyleConfig.class)
    public ResponseJson findXwCmsStyleConfigsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        xwCmsStyleConfig.setSchoolId(mySchoolId());
        List<XwCmsStyleConfig> data = xwCmsStyleConfigService.findXwCmsStyleConfigListByCondition(xwCmsStyleConfig);
        long count = xwCmsStyleConfigService.findXwCmsStyleConfigCountByCondition(xwCmsStyleConfig);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneXwCmsStyleConfigByCondition")
    @ApiOperation(value = "根据条件查找单个系统风格配置表,结果必须为单条数据", notes = "没有时返回空", response = XwCmsStyleConfig.class)
    public ResponseJson findOneXwCmsStyleConfigByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        XwCmsStyleConfig one = xwCmsStyleConfigService.findOneXwCmsStyleConfigByCondition(xwCmsStyleConfig);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteXwCmsStyleConfig/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwCmsStyleConfig(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        xwCmsStyleConfigService.deleteXwCmsStyleConfig(id);
        return new ResponseJson();
    }

    @PostMapping("/findXwCmsStyleConfigListByCondition")
    @ApiOperation(value = "根据条件查找系统风格配置表列表", notes = "返回响应对象,不包含总条数", response = XwCmsStyleConfig.class)
    public ResponseJson findXwCmsStyleConfigListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsStyleConfig xwCmsStyleConfig) {
        xwCmsStyleConfig.setSchoolId(mySchoolId());
        List<XwCmsStyleConfig> data = xwCmsStyleConfigService.findXwCmsStyleConfigListByCondition(xwCmsStyleConfig);
        return new ResponseJson(data);
    }

}
