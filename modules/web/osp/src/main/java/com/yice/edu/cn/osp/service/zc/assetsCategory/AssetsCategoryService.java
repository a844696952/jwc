package com.yice.edu.cn.osp.service.zc.assetsCategory;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.zc.assetsBasicCategory.AssetsBasicCategory;
import com.yice.edu.cn.common.pojo.xw.zc.assetsCatetory.AssetsCategory;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.osp.feignClient.zc.assetsCategory.AssetsCategoryFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsCategoryService {
    @Autowired
    private AssetsCategoryFeign assetsCategoryFeign;

    public AssetsCategory findAssetsCategoryById(String id) {
        return assetsCategoryFeign.findAssetsCategoryById(id);
    }

    public AssetsCategory saveAssetsCategory(AssetsCategory assetsCategory) {
        return assetsCategoryFeign.saveAssetsCategory(assetsCategory);
    }

    public List<AssetsCategory> findAssetsCategoryListByCondition(AssetsCategory assetsCategory) {
        return assetsCategoryFeign.findAssetsCategoryListByCondition(assetsCategory);
    }

    public AssetsCategory findOneAssetsCategoryByCondition(AssetsCategory assetsCategory) {
        return assetsCategoryFeign.findOneAssetsCategoryByCondition(assetsCategory);
    }

    public long findAssetsCategoryCountByCondition(AssetsCategory assetsCategory) {
        return assetsCategoryFeign.findAssetsCategoryCountByCondition(assetsCategory);
    }

    public void updateAssetsCategory(AssetsCategory assetsCategory) {
        assetsCategoryFeign.updateAssetsCategory(assetsCategory);
    }

    public void deleteAssetsCategory(String id) {
        assetsCategoryFeign.deleteAssetsCategory(id);
    }

    public void deleteAssetsCategoryByCondition(AssetsCategory assetsCategory) {
        assetsCategoryFeign.deleteAssetsCategoryByCondition(assetsCategory);
    }

    public List<AssetsCategory> findAllAssetsCategory(AssetsCategory assetsCategory) {
//        AssetsCategory assetsCategory = new AssetsCategory();
//        Pager pager = new Pager().setPaging(false).addExcludes("path", "urlPath");
//        assetsCategory.setPager(pager);
        //查询出基本数据
//        List<AssetsCategory> assetsCategoryList = assetsCategoryFeign.findAllAssetsCategory(assetsCategory);
//        return assetsCategoryList;
//        return ObjectKit.buildTree(assetsCategoryList,"-1");
        return  assetsCategoryFeign.findAllAssetsCategory(assetsCategory);
    }
}
