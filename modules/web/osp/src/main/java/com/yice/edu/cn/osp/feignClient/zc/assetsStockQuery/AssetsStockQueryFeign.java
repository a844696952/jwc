package com.yice.edu.cn.osp.feignClient.zc.assetsStockQuery;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockQuery.AssetsStockGatherDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@FeignClient(value="xw",contextId = "assetsStockQueryFeign",path = "assetsStockQuery")
public interface AssetsStockQueryFeign {

    @GetMapping("/findAssetsStockGather/{schoolId}")
    AssetsStockGatherDto findAssetsStockGather(@PathVariable("schoolId") String schoolId);

    @PostMapping("/findAssetsStockListByCondition")
    List<AssetsFile> findAssetsStockListByCondition(AssetsFile assetsFile);

    @PostMapping("/findAssetsStockListCountByCondition")
    public long findAssetsStockListCountByCondition(AssetsFile assetsFile);
}
