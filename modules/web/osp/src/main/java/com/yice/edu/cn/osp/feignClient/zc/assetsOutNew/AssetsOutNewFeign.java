package com.yice.edu.cn.osp.feignClient.zc.assetsOutNew;

import com.yice.edu.cn.common.pojo.xw.zc.assetsOutNew.AssetsOutNew;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsOutNewFeign",path = "/assetsOutNew")
public interface AssetsOutNewFeign {
    @GetMapping("/findAssetsOutNewById/{id}")
    AssetsOutNew findAssetsOutNewById(@PathVariable("id") String id);
    @PostMapping("/saveAssetsOutNew")
    AssetsOutNew saveAssetsOutNew(AssetsOutNew assetsOutNew);
    @PostMapping("/findAssetsOutNewListByCondition")
    List<AssetsOutNew> findAssetsOutNewListByCondition(AssetsOutNew assetsOutNew);
    @PostMapping("/findOneAssetsOutNewByCondition")
    AssetsOutNew findOneAssetsOutNewByCondition(AssetsOutNew assetsOutNew);
    @PostMapping("/findAssetsOutNewCountByCondition")
    long findAssetsOutNewCountByCondition(AssetsOutNew assetsOutNew);
    @PostMapping("/updateAssetsOutNew")
    void updateAssetsOutNew(AssetsOutNew assetsOutNew);
    @GetMapping("/deleteAssetsOutNew/{id}")
    void deleteAssetsOutNew(@PathVariable("id") String id);
    @PostMapping("/deleteAssetsOutNewByCondition")
    void deleteAssetsOutNewByCondition(AssetsOutNew assetsOutNew);
    @PostMapping("/batchSaveAssetsOutNew")
    void batchSaveAssetsOutNew(List<AssetsOutNew> assetsOutNews);
    @PostMapping("/findAssetsOutNewListByCondition4Gather")
    List<AssetsOutNew> findAssetsOutNewListByCondition4Gather(AssetsOutNew assetsOutNew);
    @PostMapping("/findAssetsOutNewCountByCondition4Gather")
    long findAssetsOutNewCountByCondition4Gather(AssetsOutNew assetsOutNew);
}
