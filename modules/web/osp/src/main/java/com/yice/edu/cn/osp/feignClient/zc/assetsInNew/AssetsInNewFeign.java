package com.yice.edu.cn.osp.feignClient.zc.assetsInNew;

import com.yice.edu.cn.common.pojo.xw.zc.assetsInNew.AssetsInNew;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsInNewFeign",path = "/assetsInNew")
public interface AssetsInNewFeign {
    @GetMapping("/findAssetsInNewById/{id}")
    AssetsInNew findAssetsInNewById(@PathVariable("id") String id);
    @PostMapping("/saveAssetsInNew")
    AssetsInNew saveAssetsInNew(AssetsInNew assetsInNew);
    @PostMapping("/findAssetsInNewListByCondition")
    List<AssetsInNew> findAssetsInNewListByCondition(AssetsInNew assetsInNew);
    @PostMapping("/findOneAssetsInNewByCondition")
    AssetsInNew findOneAssetsInNewByCondition(AssetsInNew assetsInNew);
    @PostMapping("/findAssetsInNewCountByCondition")
    long findAssetsInNewCountByCondition(AssetsInNew assetsInNew);
    @PostMapping("/updateAssetsInNew")
    void updateAssetsInNew(AssetsInNew assetsInNew);
    @GetMapping("/deleteAssetsInNew/{id}")
    void deleteAssetsInNew(@PathVariable("id") String id);
    @PostMapping("/deleteAssetsInNewByCondition")
    void deleteAssetsInNewByCondition(AssetsInNew assetsInNew);
    @PostMapping("/findAssetsNoListByCondition")
    List<AssetsInNew> findAssetsNoListByCondition(AssetsInNew assetsInNew);
    @PostMapping("/findAssetsNoInfoByNo")
    AssetsInNew findAssetsNoInfoByNo(AssetsInNew assetsInNew);
    @PostMapping("/findAssetsNoInfoByNo")
    List<AssetsInNew> findAssetsInNewListByNo(AssetsInNew assetsInNew);

    // 查询单号，查询入库单，详细信息，包括，几个资产。
    @PostMapping("/findAssetsInNewDetailByNo")
    List<AssetsInNew> findAssetsInNewDetailByNo(AssetsInNew assetsInNew);

    @PostMapping("/findAssetsInNewDetailCountByNo")
    long findAssetsInNewDetailCountByNo(AssetsInNew assetsInNew);

    @PostMapping("/findAssetsNoCountByCondition")
    long findAssetsNoCountByCondition(AssetsInNew assetsInNew);
}
