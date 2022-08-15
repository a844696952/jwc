package com.yice.edu.cn.osp.feignClient.zc.assetsCategory;

import com.yice.edu.cn.common.pojo.xw.zc.assetsCatetory.AssetsCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsCategoryFeign",path = "/assetsCategory")
public interface AssetsCategoryFeign {
    @GetMapping("/findAssetsCategoryById/{id}")
    AssetsCategory findAssetsCategoryById(@PathVariable("id") String id);

    @PostMapping("/saveAssetsCategory")
    AssetsCategory saveAssetsCategory(AssetsCategory assetsCategory);

    @PostMapping("/findAssetsCategoryListByCondition")
    List<AssetsCategory> findAssetsCategoryListByCondition(AssetsCategory assetsCategory);

    @PostMapping("/findOneAssetsCategoryByCondition")
    AssetsCategory findOneAssetsCategoryByCondition(AssetsCategory assetsCategory);

    @PostMapping("/findAssetsCategoryCountByCondition")
    long findAssetsCategoryCountByCondition(AssetsCategory assetsCategory);

    @PostMapping("/updateAssetsCategory")
    void updateAssetsCategory(AssetsCategory assetsCategory);

    @GetMapping("/deleteAssetsCategory/{id}")
    void deleteAssetsCategory(@PathVariable("id") String id);

    @PostMapping("/deleteAssetsCategoryByCondition")
    void deleteAssetsCategoryByCondition(AssetsCategory assetsCategory);

    //    @PostMapping("/findAllAssetsCategory")
//    List<AssetsCategory> findAllAssetsCategory(AssetsCategory assetsCategory);
    @PostMapping("/findAllAssetsCategory")
    List<AssetsCategory> findAllAssetsCategory(AssetsCategory assetsCategory);

}
