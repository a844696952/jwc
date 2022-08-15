package com.yice.edu.cn.yed.service.xw.zc;

import com.yice.edu.cn.common.pojo.xw.zc.assetsBasicCategory.AssetsBasicCategory;
import com.yice.edu.cn.yed.feignClient.xw.zc.AssetsBasicCategoryFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsBasicCategoryService {
    @Autowired
    private AssetsBasicCategoryFeign assetsBasicCategoryFeign;

    public AssetsBasicCategory findAssetsBasicCategoryById(String id) {
        return assetsBasicCategoryFeign.findAssetsBasicCategoryById(id);
    }

    public AssetsBasicCategory saveAssetsBasicCategory(AssetsBasicCategory assetsBasicCategory) {
        return assetsBasicCategoryFeign.saveAssetsBasicCategory(assetsBasicCategory);
    }

    public List<AssetsBasicCategory> findAssetsBasicCategoryListByCondition(AssetsBasicCategory assetsBasicCategory) {
        return assetsBasicCategoryFeign.findAssetsBasicCategoryListByCondition(assetsBasicCategory);
    }

    public AssetsBasicCategory findOneAssetsBasicCategoryByCondition(AssetsBasicCategory assetsBasicCategory) {
        return assetsBasicCategoryFeign.findOneAssetsBasicCategoryByCondition(assetsBasicCategory);
    }

    public long findAssetsBasicCategoryCountByCondition(AssetsBasicCategory assetsBasicCategory) {
        return assetsBasicCategoryFeign.findAssetsBasicCategoryCountByCondition(assetsBasicCategory);
    }

    public void updateAssetsBasicCategory(AssetsBasicCategory assetsBasicCategory) {
        assetsBasicCategoryFeign.updateAssetsBasicCategory(assetsBasicCategory);
    }

    public void updateAssetsBasicCategoryForNotNull(AssetsBasicCategory assetsBasicCategory) {
        assetsBasicCategoryFeign.updateAssetsBasicCategoryForNotNull(assetsBasicCategory);
    }

    public void deleteAssetsBasicCategory(String id) {
        assetsBasicCategoryFeign.deleteAssetsBasicCategory(id);
    }

    public void deleteAssetsBasicCategoryByCondition(AssetsBasicCategory assetsBasicCategory) {
        assetsBasicCategoryFeign.deleteAssetsBasicCategoryByCondition(assetsBasicCategory);
    }
}
