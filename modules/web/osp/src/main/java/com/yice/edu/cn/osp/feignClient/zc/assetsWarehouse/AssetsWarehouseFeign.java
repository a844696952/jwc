package com.yice.edu.cn.osp.feignClient.zc.assetsWarehouse;

import com.yice.edu.cn.common.pojo.xw.zc.assetsWarehouse.AssetsWarehouse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsWarehouse",path = "/assetsWarehouse")
public interface AssetsWarehouseFeign {
    @GetMapping("/findAssetsWarehouseById/{id}")
    AssetsWarehouse findAssetsWarehouseById(@PathVariable("id") String id);
    @PostMapping("/saveAssetsWarehouse")
    AssetsWarehouse saveAssetsWarehouse(AssetsWarehouse assetsWarehouse);
    @PostMapping("/findAssetsWarehouseListByCondition")
    List<AssetsWarehouse> findAssetsWarehouseListByCondition(AssetsWarehouse assetsWarehouse);
    @PostMapping("/findOneAssetsWarehouseByCondition")
    AssetsWarehouse findOneAssetsWarehouseByCondition(AssetsWarehouse assetsWarehouse);
    @PostMapping("/findAssetsWarehouseCountByCondition")
    long findAssetsWarehouseCountByCondition(AssetsWarehouse assetsWarehouse);
    @PostMapping("/updateAssetsWarehouse")
    void updateAssetsWarehouse(AssetsWarehouse assetsWarehouse);
    @GetMapping("/deleteAssetsWarehouse/{id}")
    void deleteAssetsWarehouse(@PathVariable("id") String id);
    @PostMapping("/deleteAssetsWarehouseByCondition")
    void deleteAssetsWarehouseByCondition(AssetsWarehouse assetsWarehouse);

    @PostMapping("/findAssetsWarehouseCountByName")
    long findAssetsWarehouseCountByName(AssetsWarehouse assetsWarehouse);
}
