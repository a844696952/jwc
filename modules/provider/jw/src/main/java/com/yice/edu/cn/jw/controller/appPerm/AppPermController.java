package com.yice.edu.cn.jw.controller.appPerm;

import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import com.yice.edu.cn.jw.service.appPerm.AppPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appPerm")
@Api(value = "/appPerm",description = "app端菜单权限模块")
public class AppPermController {
    @Autowired
    private AppPermService appPermService;

    @GetMapping("/findAppPermById/{id}")
    @ApiOperation(value = "通过id查找app端菜单权限", notes = "返回app端菜单权限对象")
    public AppPerm findAppPermById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return appPermService.findAppPermById(id);
    }

    @PostMapping("/saveAppPerm")
    @ApiOperation(value = "保存app端菜单权限", notes = "返回app端菜单权限对象")
    public AppPerm saveAppPerm(
            @ApiParam(value = "app端菜单权限对象", required = true)
            @RequestBody AppPerm appPerm){
        appPermService.saveAppPerm(appPerm);
        return appPerm;
    }

    @PostMapping("/findAppPermListByCondition")
    @ApiOperation(value = "根据条件查找app端菜单权限列表", notes = "返回app端菜单权限列表")
    public List<AppPerm> findAppPermListByCondition(
            @ApiParam(value = "app端菜单权限对象")
            @RequestBody AppPerm appPerm){
        return appPermService.findAppPermListByCondition(appPerm);
    }
    @PostMapping("/findAppPermCountByCondition")
    @ApiOperation(value = "根据条件查找app端菜单权限列表个数", notes = "返回app端菜单权限总个数")
    public long findAppPermCountByCondition(
            @ApiParam(value = "app端菜单权限对象")
            @RequestBody AppPerm appPerm){
        return appPermService.findAppPermCountByCondition(appPerm);
    }

    @PostMapping("/updateAppPerm")
    @ApiOperation(value = "修改app端菜单权限", notes = "app端菜单权限对象必传")
    public void updateAppPerm(
            @ApiParam(value = "app端菜单权限对象,对象属性不为空则修改", required = true)
            @RequestBody AppPerm appPerm){
        appPermService.updateAppPerm(appPerm);
    }

    @GetMapping("/deleteAppPerm/{id}")
    @ApiOperation(value = "通过id删除app端菜单权限")
    public void deleteAppPerm(
            @ApiParam(value = "app端菜单权限对象", required = true)
            @PathVariable String id){
        appPermService.deleteAppPerm(id);
    }
    @PostMapping("/deleteAppPermByCondition")
    @ApiOperation(value = "根据条件删除app端菜单权限")
    public void deleteAppPermByCondition(
            @ApiParam(value = "app端菜单权限对象")
            @RequestBody AppPerm appPerm){
        appPermService.deleteAppPermByCondition(appPerm);
    }
    @PostMapping("/findOneAppPermByCondition")
    @ApiOperation(value = "根据条件查找单个app端菜单权限,结果必须为单条数据", notes = "返回单个app端菜单权限,没有时为空")
    public AppPerm findOneAppPermByCondition(
            @ApiParam(value = "app端菜单权限对象")
            @RequestBody AppPerm appPerm){
        return appPermService.findOneAppPermByCondition(appPerm);
    }

    @PostMapping("/findAppPermListTreeByClass")
    @ApiOperation(value = "根据条件查询单个教师或家长在小程序或App的菜单权限",notes = "返回权限树")
    public List<AppPerm> findAppPermListTreeByClass(
            @ApiParam(value = "客户端菜单权限树")
            @RequestBody AppPerm appPerm
            ){
        return appPermService.findAppPermListTreeByClass(appPerm);
    }


    @GetMapping("/findAppPermAndSchoolPermKong/{type}/{schoolId}")
    @ApiOperation(value = "根据条件查询某个学校在小程序或App的菜单权限",notes = "返回权限树")
    public List<AppPerm> findAppPermAndSchoolPermKong(
            @ApiParam(value = "学校小程序或App端的权限树")
            @PathVariable("type") Integer type,
            @PathVariable("schoolId") String schoolId
    ){
        return appPermService.findAppPermAndSchoolPermKong(type,schoolId);
    }

    @PostMapping("/updatesAppSchoolPerm/{type}/{schoolId}")
    @ApiOperation(value = "根据条件修改学校app或者小程序的展示",notes = "无返回值")
    public void updatesAppSchoolPerm(
            @ApiParam(value = "修改app或小程序端权限")
            @RequestBody List<AppPerm> appPermList,
            @PathVariable("type") Integer type,
            @PathVariable("schoolId")String schoolId
    ){
        appPermService.updatesAppSchoolPerm(type,schoolId,appPermList);
    }

    @PostMapping("/updateAppPermModel/{type}")
    @ApiOperation(value = "修改默认模板",notes = "无返回值")
    public void  updateAppPermModel(
            @ApiParam(value = "修改app或小程序默认模板")
            @PathVariable("type")Integer type,
            @RequestBody List<AppPerm> appPermList
    ){
        appPermService.updateAppPermModel(type,appPermList);
    }
}
