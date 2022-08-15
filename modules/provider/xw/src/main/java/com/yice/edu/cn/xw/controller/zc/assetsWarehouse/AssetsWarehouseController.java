package com.yice.edu.cn.xw.controller.zc.assetsWarehouse;

import com.yice.edu.cn.common.pojo.xw.zc.assetsWarehouse.AssetsWarehouse;
import com.yice.edu.cn.xw.service.zc.assetsWarehouse.AssetsWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assetsWarehouse")
@Api(value = "/assetsWarehouse",description = "资产仓库模块")
public class AssetsWarehouseController {
    @Autowired
    private AssetsWarehouseService assetsWarehouseService;

    @GetMapping("/findAssetsWarehouseById/{id}")
    @ApiOperation(value = "通过id查找资产仓库", notes = "返回资产仓库对象")
    public AssetsWarehouse findAssetsWarehouseById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return assetsWarehouseService.findAssetsWarehouseById(id);
    }

    @PostMapping("/saveAssetsWarehouse")
    @ApiOperation(value = "保存资产仓库", notes = "返回资产仓库对象")
    public AssetsWarehouse saveAssetsWarehouse(
            @ApiParam(value = "资产仓库对象", required = true)
            @RequestBody AssetsWarehouse assetsWarehouse){
        assetsWarehouseService.saveAssetsWarehouse(assetsWarehouse);
        return assetsWarehouse;
    }

    @PostMapping("/findAssetsWarehouseListByCondition")
    @ApiOperation(value = "根据条件查找资产仓库列表", notes = "返回资产仓库列表")
    public List<AssetsWarehouse> findAssetsWarehouseListByCondition(
            @ApiParam(value = "资产仓库对象")
            @RequestBody AssetsWarehouse assetsWarehouse){
        return assetsWarehouseService.findAssetsWarehouseListByCondition(assetsWarehouse);
    }
    @PostMapping("/findAssetsWarehouseCountByCondition")
    @ApiOperation(value = "根据条件查找资产仓库列表个数", notes = "返回资产仓库总个数")
    public long findAssetsWarehouseCountByCondition(
            @ApiParam(value = "资产仓库对象")
            @RequestBody AssetsWarehouse assetsWarehouse){
        return assetsWarehouseService.findAssetsWarehouseCountByCondition(assetsWarehouse);
    }

    @PostMapping("/updateAssetsWarehouse")
    @ApiOperation(value = "修改资产仓库", notes = "资产仓库对象必传")
    public void updateAssetsWarehouse(
            @ApiParam(value = "资产仓库对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsWarehouse assetsWarehouse){
        assetsWarehouseService.updateAssetsWarehouse(assetsWarehouse);
    }

    @GetMapping("/deleteAssetsWarehouse/{id}")
    @ApiOperation(value = "通过id删除资产仓库")
    public void deleteAssetsWarehouse(
            @ApiParam(value = "资产仓库对象", required = true)
            @PathVariable String id){
        assetsWarehouseService.deleteAssetsWarehouse(id);
    }
    @PostMapping("/deleteAssetsWarehouseByCondition")
    @ApiOperation(value = "根据条件删除资产仓库")
    public void deleteAssetsWarehouseByCondition(
            @ApiParam(value = "资产仓库对象")
            @RequestBody AssetsWarehouse assetsWarehouse){
        assetsWarehouseService.deleteAssetsWarehouseByCondition(assetsWarehouse);
    }
    @PostMapping("/findOneAssetsWarehouseByCondition")
    @ApiOperation(value = "根据条件查找单个资产仓库,结果必须为单条数据", notes = "返回单个资产仓库,没有时为空")
    public AssetsWarehouse findOneAssetsWarehouseByCondition(
            @ApiParam(value = "资产仓库对象")
            @RequestBody AssetsWarehouse assetsWarehouse){
        return assetsWarehouseService.findOneAssetsWarehouseByCondition(assetsWarehouse);
    }


    @PostMapping("/findAssetsWarehouseCountByName")
    @ApiOperation(value = "根据条件查找资产仓库列表个数", notes = "返回资产仓库总个数")
    public long findAssetsWarehouseCountByName(
            @ApiParam(value = "资产仓库对象")
            @RequestBody AssetsWarehouse assetsWarehouse){
        return assetsWarehouseService.findAssetsWarehouseCountByName(assetsWarehouse);
    }
}
