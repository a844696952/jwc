package com.yice.edu.cn.jw.controller.cloudMarket;

import com.yice.edu.cn.common.pojo.jw.CloudMarket.CloudMarket;
import com.yice.edu.cn.jw.service.cloudMarket.CloudMarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cloudMarket")
@Api(value = "/cloudMarket",description = "华为云配置表模块")
public class CloudMarketController {
    @Autowired
    private CloudMarketService cloudMarketService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findCloudMarketById/{id}")
    @ApiOperation(value = "通过id查找华为云配置表", notes = "返回华为云配置表对象")
    public CloudMarket findCloudMarketById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return cloudMarketService.findCloudMarketById(id);
    }

    @PostMapping("/saveCloudMarket")
    @ApiOperation(value = "保存华为云配置表", notes = "返回华为云配置表对象")
    public CloudMarket saveCloudMarket(
            @ApiParam(value = "华为云配置表对象", required = true)
            @RequestBody CloudMarket cloudMarket){
        cloudMarketService.saveCloudMarket(cloudMarket);
        return cloudMarket;
    }

    @PostMapping("/batchSaveCloudMarket")
    @ApiOperation(value = "批量保存华为云配置表")
    public void batchSaveCloudMarket(
            @ApiParam(value = "华为云配置表对象集合", required = true)
            @RequestBody List<CloudMarket> cloudMarkets){
        cloudMarketService.batchSaveCloudMarket(cloudMarkets);
    }

    @PostMapping("/findCloudMarketListByCondition")
    @ApiOperation(value = "根据条件查找华为云配置表列表", notes = "返回华为云配置表列表")
    public List<CloudMarket> findCloudMarketListByCondition(
            @ApiParam(value = "华为云配置表对象")
            @RequestBody CloudMarket cloudMarket){
        return cloudMarketService.findCloudMarketListByCondition(cloudMarket);
    }
    @PostMapping("/findCloudMarketCountByCondition")
    @ApiOperation(value = "根据条件查找华为云配置表列表个数", notes = "返回华为云配置表总个数")
    public long findCloudMarketCountByCondition(
            @ApiParam(value = "华为云配置表对象")
            @RequestBody CloudMarket cloudMarket){
        return cloudMarketService.findCloudMarketCountByCondition(cloudMarket);
    }

    @PostMapping("/updateCloudMarket")
    @ApiOperation(value = "修改华为云配置表有值的字段", notes = "华为云配置表对象必传")
    public void updateCloudMarket(
            @ApiParam(value = "华为云配置表对象,对象属性不为空则修改", required = true)
            @RequestBody CloudMarket cloudMarket){
        cloudMarketService.updateCloudMarket(cloudMarket);
    }
    @PostMapping("/updateCloudMarketForAll")
    @ApiOperation(value = "修改华为云配置表所有字段", notes = "华为云配置表对象必传")
    public void updateCloudMarketForAll(
            @ApiParam(value = "华为云配置表对象", required = true)
            @RequestBody CloudMarket cloudMarket){
        cloudMarketService.updateCloudMarketForAll(cloudMarket);
    }

    @GetMapping("/deleteCloudMarket/{id}")
    @ApiOperation(value = "通过id删除华为云配置表")
    public void deleteCloudMarket(
            @ApiParam(value = "华为云配置表对象", required = true)
            @PathVariable String id){
        cloudMarketService.deleteCloudMarket(id);
    }
    @PostMapping("/deleteCloudMarketByCondition")
    @ApiOperation(value = "根据条件删除华为云配置表")
    public void deleteCloudMarketByCondition(
            @ApiParam(value = "华为云配置表对象")
            @RequestBody CloudMarket cloudMarket){
        cloudMarketService.deleteCloudMarketByCondition(cloudMarket);
    }
    @PostMapping("/findOneCloudMarketByCondition")
    @ApiOperation(value = "根据条件查找单个华为云配置表,结果必须为单条数据", notes = "返回单个华为云配置表,没有时为空")
    public CloudMarket findOneCloudMarketByCondition(
            @ApiParam(value = "华为云配置表对象")
            @RequestBody CloudMarket cloudMarket){
        return cloudMarketService.findOneCloudMarketByCondition(cloudMarket);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

}
