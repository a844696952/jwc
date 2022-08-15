package com.yice.edu.cn.xw.dao.zc.assetsCategory;

import com.yice.edu.cn.common.pojo.xw.zc.assetsCatetory.AssetsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAssetsCategoryDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<AssetsCategory> findAssetsCategoryListByCondition(AssetsCategory assetsCategory);

    long findAssetsCategoryCountByCondition(AssetsCategory assetsCategory);

    AssetsCategory findOneAssetsCategoryByCondition(AssetsCategory assetsCategory);

    AssetsCategory findAssetsCategoryById(@Param("id") String id);

    void saveAssetsCategory(AssetsCategory assetsCategory);

    void updateAssetsCategory(AssetsCategory assetsCategory);

    void deleteAssetsCategory(@Param("id") String id);

    void deleteAssetsCategoryByCondition(AssetsCategory assetsCategory);

    void batchSaveAssetsCategory(List<AssetsCategory> assetsCategorys);

    List<AssetsCategory> findAllCategeoryMenu(AssetsCategory assetsCategory);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
