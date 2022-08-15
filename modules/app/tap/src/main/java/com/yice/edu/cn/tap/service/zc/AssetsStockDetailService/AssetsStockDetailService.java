package com.yice.edu.cn.tap.service.zc.AssetsStockDetailService;

import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.tap.feignClient.zc.AssetsStockDetailFeign.AssetsStockDetailFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AssetsStockDetailService {

	@Autowired
    private AssetsStockDetailFeign assetsStockDetailFeign;

	public AssetsStockDetail findOneAssetsStockDetailByCondition(AssetsStockDetail assetsStockDetail) {
		return assetsStockDetailFeign.findOneAssetsStockDetailByCondition(assetsStockDetail);
	}
    public List<AssetsStockDetail> findAssetsStockDetailListByCondition(AssetsStockDetail assetsStockDetail) {
        return assetsStockDetailFeign.findAssetsStockDetailListByCondition(assetsStockDetail);
    }
}
