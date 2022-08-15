package com.yice.edu.cn.tap.feignClient.zc.AssetsStockDetailFeign;


import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsStockDetail",path = "/assetsStockDetail")
public interface AssetsStockDetailFeign {

	@PostMapping("/findOneAssetsStockDetailByCondition")
	AssetsStockDetail findOneAssetsStockDetailByCondition(AssetsStockDetail assetsStockDetail);

    @PostMapping("/findAssetsStockDetailListByCondition")
    List<AssetsStockDetail> findAssetsStockDetailListByCondition(AssetsStockDetail assetsStockDetail);
}
