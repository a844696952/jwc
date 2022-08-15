package com.yice.edu.cn.dm.controller.apk;

import com.yice.edu.cn.common.pojo.dm.classCard.Apk;
import com.yice.edu.cn.dm.service.apk.ApkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apk")
@Api(value = "/apk",description = "apk管理模块")
public class ApkController {
    @Autowired
    private ApkService apkService;

    @GetMapping("/findApkById/{id}")
    @ApiOperation(value = "通过id查找apk管理", notes = "返回apk管理对象")
    public Apk findApkById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return apkService.findApkById(id);
    }

    @PostMapping("/saveApk")
    @ApiOperation(value = "保存apk管理", notes = "返回apk管理对象")
    public Apk saveApk(
            @ApiParam(value = "apk管理对象", required = true)
            @RequestBody Apk apk){
        apkService.saveApk(apk);
        return apk;
    }

    @PostMapping("/findApkListByCondition")
    @ApiOperation(value = "根据条件查找apk管理列表", notes = "返回apk管理列表")
    public List<Apk> findApkListByCondition(
            @ApiParam(value = "apk管理对象")
            @RequestBody Apk apk){
        return apkService.findApkListByCondition(apk);
    }
    @PostMapping("/findApkCountByCondition")
    @ApiOperation(value = "根据条件查找apk管理列表个数", notes = "返回apk管理总个数")
    public long findApkCountByCondition(
            @ApiParam(value = "apk管理对象")
            @RequestBody Apk apk){
        return apkService.findApkCountByCondition(apk);
    }

    @PostMapping("/updateApk")
    @ApiOperation(value = "修改apk管理", notes = "apk管理对象必传")
    public void updateApk(
            @ApiParam(value = "apk管理对象,对象属性不为空则修改", required = true)
            @RequestBody Apk apk){
        apkService.updateApk(apk);
    }

    @GetMapping("/deleteApk/{id}")
    @ApiOperation(value = "通过id删除apk管理")
    public void deleteApk(
            @ApiParam(value = "apk管理对象", required = true)
            @PathVariable String id){
        apkService.deleteApk(id);
    }
    @PostMapping("/deleteApkByCondition")
    @ApiOperation(value = "根据条件删除apk管理")
    public void deleteApkByCondition(
            @ApiParam(value = "apk管理对象")
            @RequestBody Apk apk){
        apkService.deleteApkByCondition(apk);
    }
    @PostMapping("/findOneApkByCondition")
    @ApiOperation(value = "根据条件查找单个apk管理,结果必须为单条数据", notes = "返回单个apk管理,没有时为空")
    public Apk findOneApkByCondition(
            @ApiParam(value = "apk管理对象")
            @RequestBody Apk apk){
        return apkService.findOneApkByCondition(apk);
    }
}
