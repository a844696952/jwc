package com.yice.edu.cn.xw.controller.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpaceConfig;
import com.yice.edu.cn.xw.service.cms.XwCmsSchoolSpaceConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwCmsSchoolSpaceConfig")
@Api(value = "/xwCmsSchoolSpaceConfig",description = "模块")
public class XwCmsSchoolSpaceConfigController {
    @Autowired
    private XwCmsSchoolSpaceConfigService xwCmsSchoolSpaceConfigService;

    @GetMapping("/findXwCmsSchoolSpaceConfigById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public XwCmsSchoolSpaceConfig findXwCmsSchoolSpaceConfigById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwCmsSchoolSpaceConfigService.findXwCmsSchoolSpaceConfigById(id);
    }

    @GetMapping("/findSchoolIdBySecondDomain/{secondDomain}")
    @ApiOperation(value = "根据域名查询学校ID", notes = "返回学校ID")
    public String findSchoolIdBySecondDomain(
            @ApiParam(value = "根据二级域名查询学校ID", required = true)
            @PathVariable("secondDomain")String secondDomain){
        return xwCmsSchoolSpaceConfigService.findSchoolIdBySecondDomain(secondDomain);
    }


    @PostMapping("/saveXwCmsSchoolSpaceConfig")
    @ApiOperation(value = "保存", notes = "返回对象")
    public XwCmsSchoolSpaceConfig saveXwCmsSchoolSpaceConfig(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig){
        xwCmsSchoolSpaceConfigService.saveXwCmsSchoolSpaceConfig(xwCmsSchoolSpaceConfig);
        return xwCmsSchoolSpaceConfig;
    }

    @PostMapping("/findXwCmsSchoolSpaceConfigListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<XwCmsSchoolSpaceConfig> findXwCmsSchoolSpaceConfigListByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig){
        return xwCmsSchoolSpaceConfigService.findXwCmsSchoolSpaceConfigListByCondition(xwCmsSchoolSpaceConfig);
    }
    @PostMapping("/findXwCmsSchoolSpaceConfigCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findXwCmsSchoolSpaceConfigCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig){
        return xwCmsSchoolSpaceConfigService.findXwCmsSchoolSpaceConfigCountByCondition(xwCmsSchoolSpaceConfig);
    }

    @PostMapping("/updateXwCmsSchoolSpaceConfig")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateXwCmsSchoolSpaceConfig(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig){
        xwCmsSchoolSpaceConfigService.updateXwCmsSchoolSpaceConfig(xwCmsSchoolSpaceConfig);
    }

    @GetMapping("/deleteXwCmsSchoolSpaceConfig/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteXwCmsSchoolSpaceConfig(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        xwCmsSchoolSpaceConfigService.deleteXwCmsSchoolSpaceConfig(id);
    }
    @PostMapping("/deleteXwCmsSchoolSpaceConfigByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteXwCmsSchoolSpaceConfigByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig){
        xwCmsSchoolSpaceConfigService.deleteXwCmsSchoolSpaceConfigByCondition(xwCmsSchoolSpaceConfig);
    }
    @PostMapping("/findOneXwCmsSchoolSpaceConfigByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public XwCmsSchoolSpaceConfig findOneXwCmsSchoolSpaceConfigByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig){
        return xwCmsSchoolSpaceConfigService.findOneXwCmsSchoolSpaceConfigByCondition(xwCmsSchoolSpaceConfig);
    }
}
