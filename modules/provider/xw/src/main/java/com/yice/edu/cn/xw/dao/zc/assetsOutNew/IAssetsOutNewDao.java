package com.yice.edu.cn.xw.dao.zc.assetsOutNew;

import com.yice.edu.cn.common.pojo.xw.zc.assetsOutNew.AssetsOutNew;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAssetsOutNewDao {
    List<AssetsOutNew> findAssetsOutNewListByCondition(AssetsOutNew assetsOutNew);

    long findAssetsOutNewCountByCondition(AssetsOutNew assetsOutNew);

    AssetsOutNew findOneAssetsOutNewByCondition(AssetsOutNew assetsOutNew);

    AssetsOutNew findAssetsOutNewById(@Param("id") String id);

    void saveAssetsOutNew(AssetsOutNew assetsOutNew);

    void updateAssetsOutNew(AssetsOutNew assetsOutNew);

    void deleteAssetsOutNew(@Param("id") String id);

    void deleteAssetsOutNewByCondition(AssetsOutNew assetsOutNew);

    void batchSaveAssetsOutNew(List<AssetsOutNew> assetsOutNews);

    List<AssetsOutNew> findAssetsOutNewListByCondition4Gather(AssetsOutNew assetsOutNew);

    long findAssetsOutNewCountByCondition4Gather(AssetsOutNew assetsOutNew);
}
