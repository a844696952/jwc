package com.yice.edu.cn.xw.controller.xwRegulatoryFramework;

import com.yice.edu.cn.common.pojo.xw.xwRegulatoryFramework.XwRegulatoryFramework;
import com.yice.edu.cn.xw.service.xwRegulatoryFramework.XwRegulatoryFrameworkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwRegulatoryFramework")
@Api(value = "/xwRegulatoryFramework",description = "模块")
public class XwRegulatoryFrameworkController {
    @Autowired
    private XwRegulatoryFrameworkService xwRegulatoryFrameworkService;

    @GetMapping("/findXwRegulatoryFrameworkById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public XwRegulatoryFramework findXwRegulatoryFrameworkById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwRegulatoryFrameworkService.findXwRegulatoryFrameworkById(id);
    }

    @PostMapping("/saveXwRegulatoryFramework")
    @ApiOperation(value = "保存", notes = "返回对象")
    public XwRegulatoryFramework saveXwRegulatoryFramework(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        xwRegulatoryFrameworkService.saveXwRegulatoryFramework(xwRegulatoryFramework);
        return xwRegulatoryFramework;
    }

    @PostMapping("/findXwRegulatoryFrameworkListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<XwRegulatoryFramework> findXwRegulatoryFrameworkListByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        return xwRegulatoryFrameworkService.findXwRegulatoryFrameworkListByCondition(xwRegulatoryFramework);
    }
    @PostMapping("/findXwRegulatoryFrameworkCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findXwRegulatoryFrameworkCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        return xwRegulatoryFrameworkService.findXwRegulatoryFrameworkCountByCondition(xwRegulatoryFramework);
    }

    @PostMapping("/updateXwRegulatoryFramework")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateXwRegulatoryFramework(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        xwRegulatoryFrameworkService.updateXwRegulatoryFramework(xwRegulatoryFramework);
    }

    @GetMapping("/deleteXwRegulatoryFramework/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteXwRegulatoryFramework(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        xwRegulatoryFrameworkService.deleteXwRegulatoryFramework(id);
    }
    @PostMapping("/deleteXwRegulatoryFrameworkByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteXwRegulatoryFrameworkByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        xwRegulatoryFrameworkService.deleteXwRegulatoryFrameworkByCondition(xwRegulatoryFramework);
    }
    @PostMapping("/findOneXwRegulatoryFrameworkByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public XwRegulatoryFramework findOneXwRegulatoryFrameworkByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        return xwRegulatoryFrameworkService.findOneXwRegulatoryFrameworkByCondition(xwRegulatoryFramework);
    }
}
