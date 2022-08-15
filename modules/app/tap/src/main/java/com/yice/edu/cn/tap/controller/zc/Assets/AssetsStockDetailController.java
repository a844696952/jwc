package com.yice.edu.cn.tap.controller.zc.Assets;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.tap.service.zc.AssetsStockDetailService.AssetsStockDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/assetsStockDetail")
@Api(value = "/assetsStockDetail",description = "资产损耗管理模块")
public class AssetsStockDetailController {

	@Autowired
	private AssetsStockDetailService assetsStockDetailService;

    @PostMapping("/getAssetsStockDetail")
    @ApiOperation(value = "得到一个资产物品的所有信息,手机扫描二维码时的接口", notes = "返回一个资产的信息", response=AssetsStockDetail.class)
    public ResponseJson getOneAssetsByAssetsResId(@RequestBody AssetsStockDetail assetsStockDetail) {
		assetsStockDetail.setSchoolId(mySchoolId());
		AssetsStockDetail assetsStockDetail1 = assetsStockDetailService.findOneAssetsStockDetailByCondition(assetsStockDetail);
		if( assetsStockDetail1 == null){
			return new ResponseJson(false,"资产不存在");
		}
		if (assetsStockDetail1.getStatus() == 3 ){
			return new ResponseJson(false,"资产状态为“维修中”，无法报修");
		}else if (assetsStockDetail1.getStatus() == 4 ){
			return new ResponseJson(false,"资产状态为“故障”，无法报修");
		}else if (assetsStockDetail1.getStatus() == 5 ){
			return new ResponseJson(false,"资产状态为“报废”，无法报修");
		}
		if (assetsStockDetail1.getStatus() == 1){
			assetsStockDetail1.setUsePlaceId(assetsStockDetail1.getPutInWarehouse());
			assetsStockDetail1.setUsePlace(assetsStockDetail1.getPutInWarehouseName());
		}
		if (assetsStockDetail1.getStatus() == 2 && assetsStockDetail1.getUsePlaceId() == null){
			assetsStockDetail1.setUsePlaceId(assetsStockDetail1.getPutInWarehouse());
			assetsStockDetail1.setUsePlace(assetsStockDetail1.getPutInWarehouseName());
		}
    	return new ResponseJson(assetsStockDetail1);
    }

}
