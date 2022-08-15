package com.yice.edu.cn.xw.controller.zc.assetsInOutQuery;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.xw.service.zc.assetsInOutQuery.AssetsInOutQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/assetsInOutQuery")
@Api(value = "/assetsInOutQuery",description = "资产库存明细模块")
public class AssetsInOutQueryController {
    @Autowired
    private AssetsInOutQueryService assetsInOutQueryService;


    @PostMapping("/findInOutQueryByCondition")
    @ApiOperation(value = "根据条件查询出入库统计", notes = "返回资产库存明细列表")
    public List<AssetsFile> findInOutQueryByCondition(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsFile assetsFile){
        return assetsInOutQueryService.findInOutQueryByCondition(assetsFile);
    }

    @PostMapping("/findInOutQueryCountByCondition")
    @ApiOperation(value = "根据条件查询出入库统计", notes = "返回资产库存明细列表")
    public long findInOutQueryCountByCondition(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsFile assetsFile){
        return assetsInOutQueryService.findInOutQueryCountByCondition(assetsFile);
    }

    @PostMapping("/findAssetUseDataByCondition")
    @ApiOperation(value = "根据条件资产使用统计", notes = "返回资产库存明细列表")
    public List<AssetsFile> findAssetUseDataByCondition(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsFile assetsFile){
        return assetsInOutQueryService.findAssetUseDataByCondition(assetsFile);
    }

    @PostMapping("/findAssetUseDataCountByCondition")
    @ApiOperation(value = "根据条件资产使用统计", notes = "返回资产库存明细列表")
    public long findAssetUseDataCountByCondition(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsFile assetsFile){
        return assetsInOutQueryService.findAssetUseDataCountByCondition(assetsFile);
    }

}
