package com.yice.edu.cn.osp.feignClient.zc.assetsUnit;

import com.yice.edu.cn.common.pojo.xw.zc.assetsUnit.AssetsUnit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsUnitFeign",path = "/assetsUnit")
public interface AssetsUnitFeign {
    @GetMapping("/findAssetsUnitById/{id}")
    AssetsUnit findAssetsUnitById(@PathVariable("id") String id);
    @PostMapping("/saveAssetsUnit")
    AssetsUnit saveAssetsUnit(AssetsUnit assetsUnit);
    @PostMapping("/findAssetsUnitListByCondition")
    List<AssetsUnit> findAssetsUnitListByCondition(AssetsUnit assetsUnit);
    @PostMapping("/findOneAssetsUnitByCondition")
    AssetsUnit findOneAssetsUnitByCondition(AssetsUnit assetsUnit);
    @PostMapping("/findAssetsUnitCountByCondition")
    long findAssetsUnitCountByCondition(AssetsUnit assetsUnit);
    @PostMapping("/updateAssetsUnit")
    void updateAssetsUnit(AssetsUnit assetsUnit);
    @GetMapping("/deleteAssetsUnit/{id}")
    void deleteAssetsUnit(@PathVariable("id") String id);
    @PostMapping("/deleteAssetsUnitByCondition")
    void deleteAssetsUnitByCondition(AssetsUnit assetsUnit);

    @PostMapping("/findAssetsUnitCountByName")
    long findAssetsUnitCountByName(AssetsUnit assetsUnit);
}
