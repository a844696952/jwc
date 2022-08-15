package com.yice.edu.cn.yed.feignClient.xw.zc;

import com.yice.edu.cn.common.pojo.xw.zc.assetsBasicCategory.AssetsBasicCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsBasicCategoryFeign",path = "/assetsBasicCategory")
public interface AssetsBasicCategoryFeign {
    @GetMapping("/findAssetsBasicCategoryById/{id}")
    AssetsBasicCategory findAssetsBasicCategoryById(@PathVariable("id") String id);
    @PostMapping("/saveAssetsBasicCategory")
    AssetsBasicCategory saveAssetsBasicCategory(AssetsBasicCategory assetsBasicCategory);
    @PostMapping("/findAssetsBasicCategoryListByCondition")
    List<AssetsBasicCategory> findAssetsBasicCategoryListByCondition(AssetsBasicCategory assetsBasicCategory);
    @PostMapping("/findOneAssetsBasicCategoryByCondition")
    AssetsBasicCategory findOneAssetsBasicCategoryByCondition(AssetsBasicCategory assetsBasicCategory);
    @PostMapping("/findAssetsBasicCategoryCountByCondition")
    long findAssetsBasicCategoryCountByCondition(AssetsBasicCategory assetsBasicCategory);
    @PostMapping("/updateAssetsBasicCategory")
    void updateAssetsBasicCategory(AssetsBasicCategory assetsBasicCategory);
    @PostMapping("/updateAssetsBasicCategoryForNotNull")
    void updateAssetsBasicCategoryForNotNull(AssetsBasicCategory assetsBasicCategory);
    @GetMapping("/deleteAssetsBasicCategory/{id}")
    void deleteAssetsBasicCategory(@PathVariable("id") String id);
    @PostMapping("/deleteAssetsBasicCategoryByCondition")
    void deleteAssetsBasicCategoryByCondition(AssetsBasicCategory assetsBasicCategory);
}
