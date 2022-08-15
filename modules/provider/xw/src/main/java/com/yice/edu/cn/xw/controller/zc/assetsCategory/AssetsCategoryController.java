package com.yice.edu.cn.xw.controller.zc.assetsCategory;

import com.yice.edu.cn.common.pojo.xw.zc.assetsCatetory.AssetsCategory;
import com.yice.edu.cn.xw.service.zc.assetsCategory.AssetsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assetsCategory")
@Api(value = "/assetsCategory",description = " 资产类型，v0.0.02模块")
public class AssetsCategoryController {
    @Autowired
    private AssetsCategoryService assetsCategoryService;

    @GetMapping("/findAssetsCategoryById/{id}")
    @ApiOperation(value = "通过id查找 资产类型，v0.0.02", notes = "返回 资产类型，v0.0.02对象")
    public AssetsCategory findAssetsCategoryById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return assetsCategoryService.findAssetsCategoryById(id);
    }

    @PostMapping("/saveAssetsCategory")
    @ApiOperation(value = "保存 资产类型，v0.0.02", notes = "返回 资产类型，v0.0.02对象")
    public AssetsCategory saveAssetsCategory(
            @ApiParam(value = " 资产类型，v0.0.02对象", required = true)
            @RequestBody AssetsCategory assetsCategory){
        assetsCategoryService.saveAssetsCategory(assetsCategory);
        return assetsCategory;
    }

    @PostMapping("/findAssetsCategoryListByCondition")
    @ApiOperation(value = "根据条件查找 资产类型，v0.0.02列表", notes = "返回 资产类型，v0.0.02列表")
    public List<AssetsCategory> findAssetsCategoryListByCondition(
            @ApiParam(value = " 资产类型，v0.0.02对象")
            @RequestBody AssetsCategory assetsCategory){
        return assetsCategoryService.findAssetsCategoryListByCondition(assetsCategory);
    }
    @PostMapping("/findAssetsCategoryCountByCondition")
    @ApiOperation(value = "根据条件查找 资产类型，v0.0.02列表个数", notes = "返回 资产类型，v0.0.02总个数")
    public long findAssetsCategoryCountByCondition(
            @ApiParam(value = " 资产类型，v0.0.02对象")
            @RequestBody AssetsCategory assetsCategory){
        return assetsCategoryService.findAssetsCategoryCountByCondition(assetsCategory);
    }

    @PostMapping("/updateAssetsCategory")
    @ApiOperation(value = "修改 资产类型，v0.0.02", notes = " 资产类型，v0.0.02对象必传")
    public void updateAssetsCategory(
            @ApiParam(value = " 资产类型，v0.0.02对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsCategory assetsCategory){
        assetsCategoryService.updateAssetsCategory(assetsCategory);
    }

    @GetMapping("/deleteAssetsCategory/{id}")
    @ApiOperation(value = "通过id删除 资产类型，v0.0.02")
    public void deleteAssetsCategory(
            @ApiParam(value = " 资产类型，v0.0.02对象", required = true)
            @PathVariable String id){
        assetsCategoryService.deleteAssetsCategory(id);
    }
    @PostMapping("/deleteAssetsCategoryByCondition")
    @ApiOperation(value = "根据条件删除 资产类型，v0.0.02")
    public void deleteAssetsCategoryByCondition(
            @ApiParam(value = " 资产类型，v0.0.02对象")
            @RequestBody AssetsCategory assetsCategory){
        assetsCategoryService.deleteAssetsCategoryByCondition(assetsCategory);
    }
    @PostMapping("/findOneAssetsCategoryByCondition")
    @ApiOperation(value = "根据条件查找单个 资产类型，v0.0.02,结果必须为单条数据", notes = "返回单个 资产类型，v0.0.02,没有时为空")
    public AssetsCategory findOneAssetsCategoryByCondition(
            @ApiParam(value = " 资产类型，v0.0.02对象")
            @RequestBody AssetsCategory assetsCategory){
        return assetsCategoryService.findOneAssetsCategoryByCondition(assetsCategory);
    }

    @PostMapping("/findAllAssetsCategory")
    @ApiOperation(value = "根据条件类型树", notes = "返回树结构")
    public List<AssetsCategory> findAllAssetsCategory(
            @ApiParam(value = " 资产类型，v0.0.02对象")
            @RequestBody AssetsCategory assetsCategory){

        return assetsCategoryService.findAllAssetsCategory(assetsCategory);
    }
}
