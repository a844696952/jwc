package com.yice.edu.cn.xw.controller.zc.assetsBasicCategory;

import com.yice.edu.cn.common.pojo.xw.zc.assetsBasicCategory.AssetsBasicCategory;
import com.yice.edu.cn.xw.service.zc.assetsBasicCategory.AssetsBasicCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assetsBasicCategory")
@Api(value = "/assetsBasicCategory",description = "资产类型基础表模块")
public class AssetsBasicCategoryController {
    @Autowired
    private AssetsBasicCategoryService assetsBasicCategoryService;

    @GetMapping("/findAssetsBasicCategoryById/{id}")
    @ApiOperation(value = "通过id查找资产类型基础表", notes = "返回资产类型基础表对象")
    public AssetsBasicCategory findAssetsBasicCategoryById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return assetsBasicCategoryService.findAssetsBasicCategoryById(id);
    }

    @PostMapping("/saveAssetsBasicCategory")
    @ApiOperation(value = "保存资产类型基础表", notes = "返回资产类型基础表对象")
    public AssetsBasicCategory saveAssetsBasicCategory(
            @ApiParam(value = "资产类型基础表对象", required = true)
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        assetsBasicCategoryService.saveAssetsBasicCategory(assetsBasicCategory);
        return assetsBasicCategory;
    }

    @PostMapping("/findAssetsBasicCategoryListByCondition")
    @ApiOperation(value = "根据条件查找资产类型基础表列表", notes = "返回资产类型基础表列表")
    public List<AssetsBasicCategory> findAssetsBasicCategoryListByCondition(
            @ApiParam(value = "资产类型基础表对象")
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        return assetsBasicCategoryService.findAssetsBasicCategoryListByCondition(assetsBasicCategory);
    }
    @PostMapping("/findAssetsBasicCategoryCountByCondition")
    @ApiOperation(value = "根据条件查找资产类型基础表列表个数", notes = "返回资产类型基础表总个数")
    public long findAssetsBasicCategoryCountByCondition(
            @ApiParam(value = "资产类型基础表对象")
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        return assetsBasicCategoryService.findAssetsBasicCategoryCountByCondition(assetsBasicCategory);
    }

    @PostMapping("/updateAssetsBasicCategory")
    @ApiOperation(value = "修改资产类型基础表所有非@Transient注释的字段", notes = "资产类型基础表对象必传")
    public void updateAssetsBasicCategory(
            @ApiParam(value = "资产类型基础表对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        assetsBasicCategoryService.updateAssetsBasicCategory(assetsBasicCategory);
    }
    @PostMapping("/updateAssetsBasicCategoryForNotNull")
    @ApiOperation(value = "修改资产类型基础表有值的字段", notes = "资产类型基础表对象必传")
    public void updateAssetsBasicCategoryForNotNull(
            @ApiParam(value = "资产类型基础表对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        assetsBasicCategoryService.updateAssetsBasicCategoryForNotNull(assetsBasicCategory);
    }

    @GetMapping("/deleteAssetsBasicCategory/{id}")
    @ApiOperation(value = "通过id删除资产类型基础表")
    public void deleteAssetsBasicCategory(
            @ApiParam(value = "资产类型基础表对象", required = true)
            @PathVariable String id){
        assetsBasicCategoryService.deleteAssetsBasicCategory(id);
    }
    @PostMapping("/deleteAssetsBasicCategoryByCondition")
    @ApiOperation(value = "根据条件删除资产类型基础表")
    public void deleteAssetsBasicCategoryByCondition(
            @ApiParam(value = "资产类型基础表对象")
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        assetsBasicCategoryService.deleteAssetsBasicCategoryByCondition(assetsBasicCategory);
    }
    @PostMapping("/findOneAssetsBasicCategoryByCondition")
    @ApiOperation(value = "根据条件查找单个资产类型基础表,结果必须为单条数据", notes = "返回单个资产类型基础表,没有时为空")
    public AssetsBasicCategory findOneAssetsBasicCategoryByCondition(
            @ApiParam(value = "资产类型基础表对象")
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        return assetsBasicCategoryService.findOneAssetsBasicCategoryByCondition(assetsBasicCategory);
    }
}
