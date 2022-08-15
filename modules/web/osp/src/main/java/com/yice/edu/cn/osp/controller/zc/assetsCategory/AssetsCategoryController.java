package com.yice.edu.cn.osp.controller.zc.assetsCategory;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.assetsCatetory.AssetsCategory;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.osp.service.zc.assetsCategory.AssetsCategoryService;
import com.yice.edu.cn.osp.service.zc.assetsStockDetail.AssetsStockDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/assetsCategory")
@Api(value = "/assetsCategory",description = " 资产类型，v0.0.02模块")
public class AssetsCategoryController {
    @Autowired
    private AssetsCategoryService assetsCategoryService;
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;

//    @PostMapping("/saveAssetsCategory")
    @PostMapping("/save/assetsCategory")
    @ApiOperation(value = "保存 资产类型，v0.0.02对象", notes = "返回保存好的 资产类型，v0.0.02对象", response=AssetsCategory.class)
    public ResponseJson saveAssetsCategory(
            @ApiParam(value = " 资产类型，v0.0.02对象", required = true)
            @RequestBody AssetsCategory assetsCategory){
        //如果当前父亲下的孩子,已经存在这个名称,返回提示信息
        AssetsCategory assetsCategoryParam = new AssetsCategory();
        assetsCategoryParam.setParentId(assetsCategory.getParentId());
        assetsCategoryParam.setAssetsCategoryName(assetsCategory.getAssetsCategoryName());
        long count=assetsCategoryService.findAssetsCategoryCountByCondition(assetsCategoryParam);
        if(count == 0){
            assetsCategory.setSchoolId(mySchoolId());
            AssetsCategory s=assetsCategoryService.saveAssetsCategory(assetsCategory);
            return new ResponseJson(s);
        }else{
            return new ResponseJson(false,"当前类型下已存在此名称");
        }
    }

    @GetMapping("/find/assetsCategoryById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找 资产类型，v0.0.02", notes = "返回响应对象", response=AssetsCategory.class)
    public ResponseJson findAssetsCategoryById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsCategory assetsCategory=assetsCategoryService.findAssetsCategoryById(id);
        return new ResponseJson(assetsCategory);
    }

    @PostMapping("/update/assetsCategory")
    @ApiOperation(value = "修改 资产类型，v0.0.02对象", notes = "返回响应对象")
    public ResponseJson updateAssetsCategory(
            @ApiParam(value = "被修改的 资产类型，v0.0.02对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsCategory assetsCategory){
        assetsCategory.setSchoolId(mySchoolId());
        assetsCategoryService.updateAssetsCategory(assetsCategory);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAssetsCategoryById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找 资产类型，v0.0.02", notes = "返回响应对象", response=AssetsCategory.class)
    public ResponseJson lookAssetsCategoryById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsCategory assetsCategory=assetsCategoryService.findAssetsCategoryById(id);
        return new ResponseJson(assetsCategory);
    }

    @PostMapping("/findAssetsCategorysByCondition")
    @ApiOperation(value = "根据条件查找 资产类型，v0.0.02", notes = "返回响应对象", response=AssetsCategory.class)
    public ResponseJson findAssetsCategorysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsCategory assetsCategory){
       assetsCategory.setSchoolId(mySchoolId());
        List<AssetsCategory> data=assetsCategoryService.findAssetsCategoryListByCondition(assetsCategory);
        long count=assetsCategoryService.findAssetsCategoryCountByCondition(assetsCategory);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAssetsCategoryByCondition")
    @ApiOperation(value = "根据条件查找单个 资产类型，v0.0.02,结果必须为单条数据", notes = "没有时返回空", response=AssetsCategory.class)
    public ResponseJson findOneAssetsCategoryByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AssetsCategory assetsCategory){
        AssetsCategory one=assetsCategoryService.findOneAssetsCategoryByCondition(assetsCategory);
        return new ResponseJson(one);
    }
    @GetMapping("/delete/assetsCategory/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAssetsCategory(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        // 删除之前判断是否有挂着资产
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setAssetsCategoryId(id);
        assetsStockDetail.setDel(1);
        long count = assetsStockDetailService.findAssetsStockDetailCountByFuzzyCondition(assetsStockDetail);
        if(count == 0){
            assetsCategoryService.deleteAssetsCategory(id);
            return new ResponseJson();
        }
        return new ResponseJson(false,"该类型下有关联资产，无法删除！");
    }


    @PostMapping("/findAssetsCategoryListByCondition")
    @ApiOperation(value = "根据条件查找 资产类型，v0.0.02列表", notes = "返回响应对象,不包含总条数", response=AssetsCategory.class)
    public ResponseJson findAssetsCategoryListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsCategory assetsCategory){
       assetsCategory.setSchoolId(mySchoolId());
        List<AssetsCategory> data=assetsCategoryService.findAssetsCategoryListByCondition(assetsCategory);
        return new ResponseJson(data);
    }

    @GetMapping("/find/findAllAssetsCategory")
    @ApiOperation(value = "根据条件查找 资产类型，v0.0.02列表", notes = "返回响应对象,不包含总条数", response=AssetsCategory.class)
    public ResponseJson findAllAssetsCategory(){

        AssetsCategory assetsCategory = new AssetsCategory();
        assetsCategory.setSchoolId(mySchoolId());
        List<AssetsCategory> categoryList=assetsCategoryService.findAllAssetsCategory(assetsCategory);
        return new ResponseJson(categoryList);
    }



//    @ApiOperation(value = "保存学校总权限对象", notes = "返回响应对象")
//    public ResponseJson saveAssetsCategory(
//            @ApiParam(value = "学校总权限对象", required = true)
//            @RequestBody AssetsCategory assetsCategory){
//        AssetsCategory assetsCategory2 = assetsCategoryService.saveAssetsCategory(assetsCategory);
//        return new ResponseJson(assetsCategory2);
//
//    }

}
