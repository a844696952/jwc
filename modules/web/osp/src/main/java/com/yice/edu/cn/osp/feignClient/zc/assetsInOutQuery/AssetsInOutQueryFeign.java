package com.yice.edu.cn.osp.feignClient.zc.assetsInOutQuery;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsInOutQueryFeign",path = "/assetsInOutQuery")
public interface AssetsInOutQueryFeign {

    @PostMapping("/findInOutQueryByCondition")
    List<AssetsFile> findInOutQueryByCondition(AssetsFile assetsFile);

    @PostMapping("/findInOutQueryCountByCondition")
    long findInOutQueryCountByCondition(AssetsFile assetsFile);

    @PostMapping("/findAssetUseDataByCondition")
    List<AssetsFile> findAssetUseDataByCondition(AssetsFile assetsFile);

    @PostMapping("/findAssetUseDataCountByCondition")
    long findAssetUseDataCountByCondition(AssetsFile assetsFile);
}
