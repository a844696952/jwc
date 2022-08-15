package com.yice.edu.cn.xw.dao.zc.assetsWarehouse;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.zc.assetsWarehouse.AssetsWarehouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IAssetsWarehouseDao {
    List<AssetsWarehouse> findAssetsWarehouseListByCondition(AssetsWarehouse assetsWarehouse);

    long findAssetsWarehouseCountByCondition(AssetsWarehouse assetsWarehouse);

    AssetsWarehouse findOneAssetsWarehouseByCondition(AssetsWarehouse assetsWarehouse);

    AssetsWarehouse findAssetsWarehouseById(@Param("id") String id);

    void saveAssetsWarehouse(AssetsWarehouse assetsWarehouse);

    void updateAssetsWarehouse(AssetsWarehouse assetsWarehouse);

    void deleteAssetsWarehouse(@Param("id") String id);

    void deleteAssetsWarehouseByCondition(AssetsWarehouse assetsWarehouse);

    void batchSaveAssetsWarehouse(List<AssetsWarehouse> assetsWarehouses);

    long findAssetsWarehouseCountByName(AssetsWarehouse assetsWarehouse);
}
