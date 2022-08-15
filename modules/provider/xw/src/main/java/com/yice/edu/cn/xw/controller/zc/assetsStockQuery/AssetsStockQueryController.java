package com.yice.edu.cn.xw.controller.zc.assetsStockQuery;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockQuery.AssetsStockGatherDto;
import com.yice.edu.cn.xw.service.zc.assetsStockQuery.AssetsStockQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/assetsStockQuery")
@Api(value = "/assetsStockQuery",description = "资产库存查询模块")
public class AssetsStockQueryController {
    @Autowired
    private AssetsStockQueryService assetsStockQueryService;

    @GetMapping("/findAssetsStockGather/{schoolId}")
    @ApiOperation(value = "资产库存查询学校资产汇总计算数据", notes = "返回汇总数据对象")
    public AssetsStockGatherDto findAssetsStockGather(@PathVariable("schoolId") String schoolId){
        return assetsStockQueryService.findAssetsStockGather(schoolId);
    }

    @PostMapping("/findAssetsStockListByCondition")
    @ApiOperation(value = "资产库存首页查询", notes = "返回资产档案对象")
    public List<AssetsFile> findAssetsStockListByCondition(
            @ApiParam(value = "资产档案对象")
            @RequestBody AssetsFile assetsFile){
        return assetsStockQueryService.findAssetsStockListByCondition(assetsFile);
    }

    @PostMapping("/findAssetsStockListCountByCondition")
    @ApiOperation(value = "资产库存首页查询", notes = "返回资产档案对象总数")
    public long findAssetsStockListCountByCondition(
            @ApiParam(value = "资产档案对象")
            @RequestBody AssetsFile assetsFile){
        return assetsStockQueryService.findAssetsStockListCountByCondition(assetsFile);
    }
}
