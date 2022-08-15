package com.yice.edu.cn.osp.controller.jw.schoolDateCenter;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.SchoolDateCenter;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.schoolDateCenter.SchoolDateCenterService;
import com.yice.edu.cn.osp.service.zc.assetsFile.AssetsFileService;
import com.yice.edu.cn.osp.service.zc.assetsStockDetail.AssetsStockDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/schoolDateCenter")
@Api(value = "/schoolDateCenter",description = "模块")
public class SchoolDateCenterController {
    @Autowired
    private SchoolDateCenterService schoolDateCenterService;
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;
    @Autowired
    private AssetsFileService assetsFileService;

    @PostMapping("/saveSchoolDateCenter")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=SchoolDateCenter.class)
    public ResponseJson saveSchoolDateCenter(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolDateCenter schoolDateCenter){
        schoolDateCenter.setSchoolId(LoginInterceptor.mySchoolId());
        SchoolDateCenter s=schoolDateCenterService.saveSchoolDateCenter(schoolDateCenter);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSchoolDateCenterById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=SchoolDateCenter.class)
    public ResponseJson findSchoolDateCenterById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolDateCenter schoolDateCenter=schoolDateCenterService.findSchoolDateCenterById(id);
        return new ResponseJson(schoolDateCenter);
    }

    @PostMapping("/update/updateSchoolDateCenter")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateSchoolDateCenter(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolDateCenter schoolDateCenter){
        schoolDateCenterService.updateSchoolDateCenter(schoolDateCenter);
        return new ResponseJson();
    }

    @PostMapping("/update/updateSchoolDateCenterForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateSchoolDateCenterForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody SchoolDateCenter schoolDateCenter){
        schoolDateCenterService.updateSchoolDateCenterForAll(schoolDateCenter);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSchoolDateCenterById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=SchoolDateCenter.class)
    public ResponseJson lookSchoolDateCenterById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolDateCenter schoolDateCenter=schoolDateCenterService.findSchoolDateCenterById(id);
        return new ResponseJson(schoolDateCenter);
    }

    @PostMapping("/findSchoolDateCentersByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=SchoolDateCenter.class)
    public ResponseJson findSchoolDateCentersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolDateCenter schoolDateCenter){
        List<SchoolDateCenter> data=schoolDateCenterService.findSchoolDateCenterListByCondition(schoolDateCenter);
        long count=schoolDateCenterService.findSchoolDateCenterCountByCondition(schoolDateCenter);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSchoolDateCenterByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=SchoolDateCenter.class)
    public ResponseJson findOneSchoolDateCenterByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SchoolDateCenter schoolDateCenter){
        SchoolDateCenter one=schoolDateCenterService.findOneSchoolDateCenterByCondition(schoolDateCenter);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteSchoolDateCenter/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolDateCenter(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        schoolDateCenterService.deleteSchoolDateCenter(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolDateCenterListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=SchoolDateCenter.class)
    public ResponseJson findSchoolDateCenterListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolDateCenter schoolDateCenter){
        List<SchoolDateCenter> data=schoolDateCenterService.findSchoolDateCenterListByCondition(schoolDateCenter);
        return new ResponseJson(data);
    }

    @GetMapping("/ignore/findSchoolCompusCenterBySchoolId/{schoolId}")
    public ResponseJson findSchoolCompusCenterBySchoolId(@PathVariable("schoolId") String schoolId){
        SchoolDateCenter schoolDateCenter = schoolDateCenterService.findSchoolCompusCenterBySchoolId(schoolId);
        return new ResponseJson(schoolDateCenter);
    }

    /**
     * 获取一年的资产   1为固定，2为易耗品
     * @param number
     * @return
     */
    @GetMapping("/ignore/findRecentOneYearAssertsCount/{number}")
    public ResponseJson findRecentOneYearAssertsCount(@PathVariable("number") Integer number){
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setSchoolId(LoginInterceptor.mySchoolId());
        assetsStockDetail.setAssetsProperty(number);
        List<AssetsStockDetail> assetsStockDetails = assetsStockDetailService.findRecentOneYearAssertsCount(assetsStockDetail);
        return new ResponseJson(assetsStockDetails);
    }

    /**
     * 获取一个月的资产 1为固定 2为消耗
     */
    @GetMapping("/ignore/findRecentOneMonthAssertsCount/{number}")
    public ResponseJson findRecentOneMonthAssertsCount(@PathVariable("number") Integer number){
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setSchoolId(LoginInterceptor.mySchoolId());
        assetsStockDetail.setAssetsProperty(number);
        List<AssetsStockDetail> assetsStockDetails = assetsStockDetailService.findRecentOneMonthAssertsCount(assetsStockDetail);
        return new ResponseJson(assetsStockDetails);
    }

    @PostMapping("/ignore/updateSchoolDateCenterByType/{type}")
    public ResponseJson updateSchoolDateCenterByType(@RequestBody SchoolDateCenter schoolDateCenter,
                                                     @PathVariable("type") Integer type){
        schoolDateCenter.setSchoolId(LoginInterceptor.mySchoolId());
        schoolDateCenterService.updateSchoolDateCenterByType(schoolDateCenter,type);
        return new ResponseJson();
    }

    @GetMapping("/ignore/findSchoolDateCenter")
    public ResponseJson findSchoolDateCenter(){
        SchoolDateCenter schoolDateCenter = new SchoolDateCenter();
        schoolDateCenter.setSchoolId(LoginInterceptor.mySchoolId());
        SchoolDateCenter schoolDateCenter1 = schoolDateCenterService.findOneSchoolDateCenterByCondition(schoolDateCenter);
        return new ResponseJson(schoolDateCenter1);
    }

    @PostMapping("/ignore/getAssetsFile")
    public ResponseJson getAssetsFile(@RequestBody AssetsFile assetsFile){
        assetsFile.setSchoolId(LoginInterceptor.mySchoolId());
        List<AssetsFile> assetsFiles = assetsFileService.findAssetsFileListByCondition(assetsFile);
        return new ResponseJson(assetsFiles);
    }

    @GetMapping("/ignore/getTruesSchoolDateCenter")
    public ResponseJson getTruesSchoolDateCenter(){
        SchoolDateCenter schoolDateCenter = schoolDateCenterService.getTruesSchoolDateCenter(LoginInterceptor.mySchoolId());
        return new ResponseJson(schoolDateCenter);
    }
}



