package com.yice.edu.cn.xw.dao.zc.assetsUnit;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.zc.assetsUnit.AssetsUnit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IAssetsUnitDao {
    List<AssetsUnit> findAssetsUnitListByCondition(AssetsUnit assetsUnit);

    long findAssetsUnitCountByCondition(AssetsUnit assetsUnit);

    AssetsUnit findOneAssetsUnitByCondition(AssetsUnit assetsUnit);

    AssetsUnit findAssetsUnitById(@Param("id") String id);

    void saveAssetsUnit(AssetsUnit assetsUnit);

    void updateAssetsUnit(AssetsUnit assetsUnit);

    void deleteAssetsUnit(@Param("id") String id);

    void deleteAssetsUnitByCondition(AssetsUnit assetsUnit);

    void batchSaveAssetsUnit(List<AssetsUnit> assetsUnits);

    long findAssetsUnitCountByName(AssetsUnit assetsUnit);
}
