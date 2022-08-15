package com.yice.edu.cn.ecc.controller.dmMobileRedBanner;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBanner;
import com.yice.edu.cn.ecc.service.dmMobileRedBanner.DmMobileRedBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmMobileRedBanner")
@Api(value = "/dmMobileRedBanner",description = "流动红旗表模块")
public class DmMobileRedBannerController {
    @Autowired
    private DmMobileRedBannerService dmMobileRedBannerService;

    @PostMapping("/saveDmMobileRedBanner")
    @ApiOperation(value = "保存流动红旗表对象", notes = "返回保存好的流动红旗表对象", response=DmMobileRedBanner.class)
    public ResponseJson saveDmMobileRedBanner(
            @ApiParam(value = "流动红旗表对象", required = true)
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
       dmMobileRedBanner.setSchoolId(mySchoolId());
        DmMobileRedBanner s=dmMobileRedBannerService.saveDmMobileRedBanner(dmMobileRedBanner);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmMobileRedBannerById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找流动红旗表", notes = "返回响应对象", response=DmMobileRedBanner.class)
    public ResponseJson findDmMobileRedBannerById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmMobileRedBanner dmMobileRedBanner=dmMobileRedBannerService.findDmMobileRedBannerById(id);
        return new ResponseJson(dmMobileRedBanner);
    }

    @PostMapping("/update/updateDmMobileRedBanner")
    @ApiOperation(value = "修改流动红旗表对象所有非@Transient注释的字段", notes = "返回响应对象")
    public ResponseJson updateDmMobileRedBanner(
            @ApiParam(value = "被修改的流动红旗表对象", required = true)
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
        dmMobileRedBannerService.updateDmMobileRedBanner(dmMobileRedBanner);
        return new ResponseJson();
    }

    @PostMapping("/update/updateDmMobileRedBannerForNotNull")
    @ApiOperation(value = "修改流动红旗表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateDmMobileRedBannerForNotNull(
            @ApiParam(value = "被修改的流动红旗表对象,对象属性不为空则修改", required = true)
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
        dmMobileRedBannerService.updateDmMobileRedBannerForNotNull(dmMobileRedBanner);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmMobileRedBannerById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找流动红旗表", notes = "返回响应对象", response=DmMobileRedBanner.class)
    public ResponseJson lookDmMobileRedBannerById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmMobileRedBanner dmMobileRedBanner=dmMobileRedBannerService.findDmMobileRedBannerById(id);
        return new ResponseJson(dmMobileRedBanner);
    }

    @PostMapping("/findDmMobileRedBannersByCondition")
    @ApiOperation(value = "根据条件查找流动红旗表", notes = "返回响应对象", response=DmMobileRedBanner.class)
    public ResponseJson findDmMobileRedBannersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
       dmMobileRedBanner.setSchoolId(mySchoolId());
        List<DmMobileRedBanner> data=dmMobileRedBannerService.findDmMobileRedBannerListByCondition(dmMobileRedBanner);
        long count=dmMobileRedBannerService.findDmMobileRedBannerCountByCondition(dmMobileRedBanner);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmMobileRedBannerByCondition")
    @ApiOperation(value = "根据条件查找单个流动红旗表,结果必须为单条数据", notes = "没有时返回空", response=DmMobileRedBanner.class)
    public ResponseJson findOneDmMobileRedBannerByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
        DmMobileRedBanner one=dmMobileRedBannerService.findOneDmMobileRedBannerByCondition(dmMobileRedBanner);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmMobileRedBanner/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmMobileRedBanner(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmMobileRedBannerService.deleteDmMobileRedBanner(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmMobileRedBannerListByCondition")
    @ApiOperation(value = "根据条件查找流动红旗表列表", notes = "返回响应对象,不包含总条数", response=DmMobileRedBanner.class)
    public ResponseJson findDmMobileRedBannerListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
       dmMobileRedBanner.setSchoolId(mySchoolId());
        List<DmMobileRedBanner> data=dmMobileRedBannerService.findDmMobileRedBannerListByCondition(dmMobileRedBanner);
        return new ResponseJson(data);
    }

    @GetMapping("/findDmMobileRedBannerByClassId/{classId}")
    @ApiOperation(value = "通过classId查找流动红旗表", notes = "返回流动红旗表对象")
    public ResponseJson findDmMobileRedBannerByClassId(
            @ApiParam(value = "需要用到的classId", required = true)
            @PathVariable("classId") String classId){
        return new ResponseJson(dmMobileRedBannerService.findDmMobileRedBannerByClassId(classId));
    }


    @PostMapping("/updateAwardRedBanner")
    @ApiOperation(value = "根据条件查找流动红旗表列表", notes = "返回响应对象,不包含总条数", response=DmMobileRedBanner.class)
    public ResponseJson updateAwardRedBanner(){
        dmMobileRedBannerService.updateAwardRedBanner();
        return new ResponseJson();
    }
}
