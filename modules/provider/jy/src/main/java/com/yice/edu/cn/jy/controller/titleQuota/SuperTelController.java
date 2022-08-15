package com.yice.edu.cn.jy.controller.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.SuperTel;
import com.yice.edu.cn.jy.service.titleQuota.SuperTelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/superTel")
@Api(value = "/superTel",description = "运营超级管理员手机号码维护模块")
public class SuperTelController {
    @Autowired
    private SuperTelService superTelService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findSuperTelById/{id}")
    @ApiOperation(value = "通过id查找运营超级管理员手机号码维护", notes = "返回运营超级管理员手机号码维护对象")
    public SuperTel findSuperTelById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return superTelService.findSuperTelById(id);
    }

    @PostMapping("/saveSuperTel")
    @ApiOperation(value = "保存运营超级管理员手机号码维护", notes = "返回运营超级管理员手机号码维护对象")
    public SuperTel saveSuperTel(
            @ApiParam(value = "运营超级管理员手机号码维护对象", required = true)
            @RequestBody SuperTel superTel){
        superTelService.saveSuperTel(superTel);
        return superTel;
    }

    @PostMapping("/batchSaveSuperTel")
    @ApiOperation(value = "批量保存运营超级管理员手机号码维护")
    public void batchSaveSuperTel(
            @ApiParam(value = "运营超级管理员手机号码维护对象集合", required = true)
            @RequestBody List<SuperTel> superTels){
        superTelService.batchSaveSuperTel(superTels);
    }

    @PostMapping("/findSuperTelListByCondition")
    @ApiOperation(value = "根据条件查找运营超级管理员手机号码维护列表", notes = "返回运营超级管理员手机号码维护列表")
    public List<SuperTel> findSuperTelListByCondition(
            @ApiParam(value = "运营超级管理员手机号码维护对象")
            @RequestBody SuperTel superTel){
        return superTelService.findSuperTelListByCondition(superTel);
    }
    @PostMapping("/findSuperTelCountByCondition")
    @ApiOperation(value = "根据条件查找运营超级管理员手机号码维护列表个数", notes = "返回运营超级管理员手机号码维护总个数")
    public long findSuperTelCountByCondition(
            @ApiParam(value = "运营超级管理员手机号码维护对象")
            @RequestBody SuperTel superTel){
        return superTelService.findSuperTelCountByCondition(superTel);
    }

    @PostMapping("/updateSuperTel")
    @ApiOperation(value = "修改运营超级管理员手机号码维护有值的字段", notes = "运营超级管理员手机号码维护对象必传")
    public void updateSuperTel(
            @ApiParam(value = "运营超级管理员手机号码维护对象,对象属性不为空则修改", required = true)
            @RequestBody SuperTel superTel){
        superTelService.updateSuperTel(superTel);
    }
    @PostMapping("/updateSuperTelForAll")
    @ApiOperation(value = "修改运营超级管理员手机号码维护所有字段", notes = "运营超级管理员手机号码维护对象必传")
    public void updateSuperTelForAll(
            @ApiParam(value = "运营超级管理员手机号码维护对象", required = true)
            @RequestBody SuperTel superTel){
        superTelService.updateSuperTelForAll(superTel);
    }

    @GetMapping("/deleteSuperTel/{id}")
    @ApiOperation(value = "通过id删除运营超级管理员手机号码维护")
    public void deleteSuperTel(
            @ApiParam(value = "运营超级管理员手机号码维护对象", required = true)
            @PathVariable String id){
        superTelService.deleteSuperTel(id);
    }
    @PostMapping("/deleteSuperTelByCondition")
    @ApiOperation(value = "根据条件删除运营超级管理员手机号码维护")
    public void deleteSuperTelByCondition(
            @ApiParam(value = "运营超级管理员手机号码维护对象")
            @RequestBody SuperTel superTel){
        superTelService.deleteSuperTelByCondition(superTel);
    }
    @PostMapping("/findOneSuperTelByCondition")
    @ApiOperation(value = "根据条件查找单个运营超级管理员手机号码维护,结果必须为单条数据", notes = "返回单个运营超级管理员手机号码维护,没有时为空")
    public SuperTel findOneSuperTelByCondition(
            @ApiParam(value = "运营超级管理员手机号码维护对象")
            @RequestBody SuperTel superTel){
        return superTelService.findOneSuperTelByCondition(superTel);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
