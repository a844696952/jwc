package com.yice.edu.cn.xw.dao.zc.assetsBasicCategory;

import com.yice.edu.cn.common.pojo.xw.zc.assetsBasicCategory.AssetsBasicCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAssetsBasicCategoryDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<AssetsBasicCategory> findAssetsBasicCategoryListByCondition(AssetsBasicCategory assetsBasicCategory);

    long findAssetsBasicCategoryCountByCondition(AssetsBasicCategory assetsBasicCategory);

    AssetsBasicCategory findOneAssetsBasicCategoryByCondition(AssetsBasicCategory assetsBasicCategory);

    AssetsBasicCategory findAssetsBasicCategoryById(@Param("id") String id);

    void saveAssetsBasicCategory(AssetsBasicCategory assetsBasicCategory);

    void updateAssetsBasicCategory(AssetsBasicCategory assetsBasicCategory);

    void updateAssetsBasicCategoryForNotNull(AssetsBasicCategory assetsBasicCategory);

    void deleteAssetsBasicCategory(@Param("id") String id);

    void deleteAssetsBasicCategoryByCondition(AssetsBasicCategory assetsBasicCategory);

    void batchSaveAssetsBasicCategory(List<AssetsBasicCategory> assetsBasicCategorys);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
