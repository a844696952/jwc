package com.yice.edu.cn.osp.service.zc.assetsWarehouse;

import com.yice.edu.cn.common.pojo.xw.zc.assetsWarehouse.AssetsWarehouse;
import com.yice.edu.cn.osp.feignClient.zc.assetsWarehouse.AssetsWarehouseFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsWarehouseService {
    @Autowired
    private AssetsWarehouseFeign assetsWarehouseFeign;

    public AssetsWarehouse findAssetsWarehouseById(String id) {
        return assetsWarehouseFeign.findAssetsWarehouseById(id);
    }

    public AssetsWarehouse saveAssetsWarehouse(AssetsWarehouse assetsWarehouse) {
        return assetsWarehouseFeign.saveAssetsWarehouse(assetsWarehouse);
    }

    public List<AssetsWarehouse> findAssetsWarehouseListByCondition(AssetsWarehouse assetsWarehouse) {
        return assetsWarehouseFeign.findAssetsWarehouseListByCondition(assetsWarehouse);
    }

    public AssetsWarehouse findOneAssetsWarehouseByCondition(AssetsWarehouse assetsWarehouse) {
        return assetsWarehouseFeign.findOneAssetsWarehouseByCondition(assetsWarehouse);
    }

    public long findAssetsWarehouseCountByCondition(AssetsWarehouse assetsWarehouse) {
        return assetsWarehouseFeign.findAssetsWarehouseCountByCondition(assetsWarehouse);
    }

    public void updateAssetsWarehouse(AssetsWarehouse assetsWarehouse) {
        assetsWarehouseFeign.updateAssetsWarehouse(assetsWarehouse);
    }

    public void deleteAssetsWarehouse(String id) {
        assetsWarehouseFeign.deleteAssetsWarehouse(id);
    }

    public void deleteAssetsWarehouseByCondition(AssetsWarehouse assetsWarehouse) {
        assetsWarehouseFeign.deleteAssetsWarehouseByCondition(assetsWarehouse);
    }


    public long findAssetsWarehouseCountByName(AssetsWarehouse assetsWarehouse) {
        return assetsWarehouseFeign.findAssetsWarehouseCountByName(assetsWarehouse);
    }
}
