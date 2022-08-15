package com.yice.edu.cn.osp.controller.xw.cms;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpaceConfig;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.osp.service.xw.cms.XwCmsSchoolSpaceConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwCmsSchoolSpaceConfig")
@Api(value = "/xwCmsSchoolSpaceConfig",description = "模块")
public class XwCmsSchoolSpaceConfigController {
    @Autowired
    private XwCmsSchoolSpaceConfigService xwCmsSchoolSpaceConfigService;

    @PostMapping("/saveXwCmsSchoolSpaceConfig")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= XwCmsSchoolSpaceConfig.class)
    public ResponseJson saveXwCmsSchoolSpaceConfig(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig){
       xwCmsSchoolSpaceConfig.setSchoolId(mySchoolId());
       XwCmsSchoolSpaceConfig s=xwCmsSchoolSpaceConfigService.saveXwCmsSchoolSpaceConfig(xwCmsSchoolSpaceConfig);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwCmsSchoolSpaceConfigById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=XwCmsSchoolSpaceConfig.class)
    public ResponseJson findXwCmsSchoolSpaceConfigById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig=xwCmsSchoolSpaceConfigService.findXwCmsSchoolSpaceConfigById(id);
        return new ResponseJson(xwCmsSchoolSpaceConfig);
    }

    @PostMapping("/update/updateXwCmsSchoolSpaceConfig")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateXwCmsSchoolSpaceConfig(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig){
        xwCmsSchoolSpaceConfigService.updateXwCmsSchoolSpaceConfig(xwCmsSchoolSpaceConfig);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwCmsSchoolSpaceConfigById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=XwCmsSchoolSpaceConfig.class)
    public ResponseJson lookXwCmsSchoolSpaceConfigById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig=xwCmsSchoolSpaceConfigService.findXwCmsSchoolSpaceConfigById(id);
        return new ResponseJson(xwCmsSchoolSpaceConfig);
    }

    @PostMapping("/findXwCmsSchoolSpaceConfigsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=XwCmsSchoolSpaceConfig.class)
    public ResponseJson findXwCmsSchoolSpaceConfigsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig){
       xwCmsSchoolSpaceConfig.setSchoolId(mySchoolId());
        List<XwCmsSchoolSpaceConfig> data=xwCmsSchoolSpaceConfigService.findXwCmsSchoolSpaceConfigListByCondition(xwCmsSchoolSpaceConfig);
        long count=xwCmsSchoolSpaceConfigService.findXwCmsSchoolSpaceConfigCountByCondition(xwCmsSchoolSpaceConfig);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findOneXwCmsSchoolSpaceConfigByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=XwCmsSchoolSpaceConfig.class)
    public ResponseJson findOneXwCmsSchoolSpaceConfigByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig){
        xwCmsSchoolSpaceConfig.setSchoolId(mySchoolId());
        XwCmsSchoolSpaceConfig one=xwCmsSchoolSpaceConfigService.findOneXwCmsSchoolSpaceConfigByCondition(xwCmsSchoolSpaceConfig);
        if(Objects.nonNull(one)){
            if(StringUtils.isNotEmpty(one.getSecondDomain())){
               one.setDomain(String.format(one.getDomain(),one.getSecondDomain()));
            }else{
                one.setDomain("");
            }
        }
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwCmsSchoolSpaceConfig/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwCmsSchoolSpaceConfig(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwCmsSchoolSpaceConfigService.deleteXwCmsSchoolSpaceConfig(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwCmsSchoolSpaceConfigListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=XwCmsSchoolSpaceConfig.class)
    public ResponseJson findXwCmsSchoolSpaceConfigListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig){
       xwCmsSchoolSpaceConfig.setSchoolId(mySchoolId());
        List<XwCmsSchoolSpaceConfig> data=xwCmsSchoolSpaceConfigService.findXwCmsSchoolSpaceConfigListByCondition(xwCmsSchoolSpaceConfig);
        return new ResponseJson(data);
    }

    @PostMapping("/findSchoolIdBySecondDomain/{secondDomain}")
    @ApiOperation(value = "根据二级域名查询学校ID", notes = "返回对应的学校ID")
    public ResponseJson findSchoolIdBySecondDomain(
            @ApiParam(value = "二级域名",required = true)
            @PathVariable("secondDomain")String secondDomain){
        if(StringUtils.isNotEmpty(secondDomain)){
            String schoolId = xwCmsSchoolSpaceConfigService.findSchoolIdBySecondDomain(secondDomain);
            if(StringUtils.isNotEmpty(schoolId)){
                return new ResponseJson(true,schoolId);
            }
        }
        return new ResponseJson(false,404,"域名不存在或者被关闭");
    }


}
