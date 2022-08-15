package com.yice.edu.cn.jw.controller.appPerm;

import com.yice.edu.cn.common.pojo.yedAdmin.AppSchoolPerm;
import com.yice.edu.cn.jw.service.appPerm.AppSchoolPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appSchoolPerm")
@Api(value = "/appSchoolPerm",description = "模块")
public class AppSchoolPermController {
    @Autowired
    private AppSchoolPermService appSchoolPermService;

    @GetMapping("/findAppSchoolPermById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public AppSchoolPerm findAppSchoolPermById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return appSchoolPermService.findAppSchoolPermById(id);
    }

    @PostMapping("/saveAppSchoolPerm")
    @ApiOperation(value = "保存", notes = "返回对象")
    public AppSchoolPerm saveAppSchoolPerm(
            @ApiParam(value = "对象", required = true)
            @RequestBody AppSchoolPerm appSchoolPerm){
        appSchoolPermService.saveAppSchoolPerm(appSchoolPerm);
        return appSchoolPerm;
    }

    @PostMapping("/findAppSchoolPermListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<AppSchoolPerm> findAppSchoolPermListByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppSchoolPerm appSchoolPerm){
        return appSchoolPermService.findAppSchoolPermListByCondition(appSchoolPerm);
    }
    @PostMapping("/findAppSchoolPermCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findAppSchoolPermCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppSchoolPerm appSchoolPerm){
        return appSchoolPermService.findAppSchoolPermCountByCondition(appSchoolPerm);
    }

    @PostMapping("/updateAppSchoolPerm")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateAppSchoolPerm(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody AppSchoolPerm appSchoolPerm){
        appSchoolPermService.updateAppSchoolPerm(appSchoolPerm);
    }

    @GetMapping("/deleteAppSchoolPerm/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteAppSchoolPerm(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        appSchoolPermService.deleteAppSchoolPerm(id);
    }
    @PostMapping("/deleteAppSchoolPermByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteAppSchoolPermByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppSchoolPerm appSchoolPerm){
        appSchoolPermService.deleteAppSchoolPermByCondition(appSchoolPerm);
    }
    @PostMapping("/findOneAppSchoolPermByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public AppSchoolPerm findOneAppSchoolPermByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppSchoolPerm appSchoolPerm){
        return appSchoolPermService.findOneAppSchoolPermByCondition(appSchoolPerm);
    }
}
