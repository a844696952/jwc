package com.yice.edu.cn.xw.controller.zc.assetsStockDetail;

//import com.yice.edu.cn.common.pojo.jw.semester.Semester;
import com.yice.edu.cn.common.pojo.xw.zc.AssetsQrcodeMsg;
import com.yice.edu.cn.common.pojo.xw.zc.AssetsResQrcode;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import com.yice.edu.cn.common.util.crypto.SimpleCryptoKit;
import com.yice.edu.cn.xw.service.zc.assetsStockDetail.AssetsResQrcodeServiceNew;
import com.yice.edu.cn.xw.service.zc.assetsStockDetail.AssetsStockDetailService;
import com.yice.edu.cn.xw.service.zc.repairNew.RepairFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/assetsStockDetail")
@Api(value = "/assetsStockDetail",description = "资产库存明细模块")
public class AssetsStockDetailController {
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findAssetsStockDetailById/{id}")
    @ApiOperation(value = "通过id查找资产库存明细", notes = "返回资产库存明细对象")
    public AssetsStockDetail findAssetsStockDetailById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return assetsStockDetailService.findAssetsStockDetailById(id);
    }

    @PostMapping("/saveAssetsStockDetail")
    @ApiOperation(value = "保存资产库存明细", notes = "返回资产库存明细对象")
    public AssetsStockDetail saveAssetsStockDetail(
            @ApiParam(value = "资产库存明细对象", required = true)
            @RequestBody AssetsStockDetail assetsStockDetail){
        assetsStockDetailService.saveAssetsStockDetail(assetsStockDetail);
        return assetsStockDetail;
    }

    @PostMapping("/findAssetsStockDetailListByCondition")
    @ApiOperation(value = "根据条件查找资产库存明细列表", notes = "返回资产库存明细列表")
    public List<AssetsStockDetail> findAssetsStockDetailListByCondition(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        return assetsStockDetailService.findAssetsStockDetailListByCondition(assetsStockDetail);
    }
    @PostMapping("/findAssetsStockDetailCountByCondition")
    @ApiOperation(value = "根据条件查找资产库存明细列表个数", notes = "返回资产库存明细总个数")
    public long findAssetsStockDetailCountByCondition(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        return assetsStockDetailService.findAssetsStockDetailCountByCondition(assetsStockDetail);
    }

    @PostMapping("/updateAssetsStockDetail")
    @ApiOperation(value = "修改资产库存明细所有非@Transient注释的字段", notes = "资产库存明细对象必传")
    public void updateAssetsStockDetail(
            @ApiParam(value = "资产库存明细对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsStockDetail assetsStockDetail){
        assetsStockDetailService.updateAssetsStockDetail(assetsStockDetail);
    }

    @GetMapping("/deleteAssetsStockDetail/{id}")
    @ApiOperation(value = "通过id删除资产库存明细")
    public void deleteAssetsStockDetail(
            @ApiParam(value = "资产库存明细对象", required = true)
            @PathVariable String id){
        assetsStockDetailService.deleteAssetsStockDetail(id);
    }
    @PostMapping("/deleteAssetsStockDetailByCondition")
    @ApiOperation(value = "根据条件删除资产库存明细")
    public void deleteAssetsStockDetailByCondition(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        assetsStockDetailService.deleteAssetsStockDetailByCondition(assetsStockDetail);
    }
    @PostMapping("/findOneAssetsStockDetailByCondition")
    @ApiOperation(value = "根据条件查找单个资产库存明细,结果必须为单条数据", notes = "返回单个资产库存明细,没有时为空")
    public AssetsStockDetail findOneAssetsStockDetailByCondition(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        //解密 资产编号
        assetsStockDetail.setId(SimpleCryptoKit.decrypt(assetsStockDetail.getId()));
        return assetsStockDetailService.findOneAssetsStockDetailByCondition(assetsStockDetail);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Autowired
    private RepairFileService repairFileService;
    @Autowired
    private AssetsResQrcodeServiceNew assetsResQrcodeService;


    @GetMapping("/findAssetsStockDetailJoinTableById/{id}")
    @ApiOperation(value = "通过id查找资产库存明细", notes = "返回资产库存明细对象")
    public AssetsStockDetail findAssetsStockDetailJoinTableById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return assetsStockDetailService.findAssetsStockDetailJoinTableById(id);
    }

    @PostMapping("/findAssetsStockDetailListByFuzzyCondition")
    @ApiOperation(value = "资产明细库存首页，模糊查询", notes = "返回资产库存明细列表")
    public List<AssetsStockDetail> findAssetsStockDetailListByFuzzyCondition(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        return assetsStockDetailService.findAssetsStockDetailListByFuzzyCondition(assetsStockDetail);
    }

    @PostMapping("/findAssetsStockDetailCountByFuzzyCondition")
    @ApiOperation(value = "资产明细库存首页count", notes = "返回count")
    public long findAssetsStockDetailCountByFuzzyCondition(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        return assetsStockDetailService.findAssetsStockDetailCountByFuzzyCondition(assetsStockDetail);
    }

    @PostMapping("/updateAssetsStockDetailForNotNull")
    @ApiOperation(value = "修改资产库存明细", notes = "资产库存明细对象必传")
    public void updateAssetsStockDetailForNotNull(
            @ApiParam(value = "资产库存明细对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsStockDetail assetsStockDetail){
        if( assetsStockDetail.getStatus() != null){
            if(assetsStockDetail.getStatus() == 5){ //报废
                RepairFile repairFile = new RepairFile();
                repairFile.setAssetsStockDetailId(assetsStockDetail.getId());
                repairFile.setScrapFileName(assetsStockDetail.getAssetsScrapDocName());
                repairFile.setScrapFileUrl(assetsStockDetail.getAssetsScrapDocUrl());
                repairFile.setSchoolId(assetsStockDetail.getSchoolId());
                repairFileService.saveRepairFile(repairFile);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("当前时间：" + sdf.format(new Date()));
                assetsStockDetail.setScrapTime(sdf.format(new Date()));// 设置报废时间
                assetsStockDetail.setScrapRemark(assetsStockDetail.getAssetsScrapRemark());//设置报废备注
            }else  if(assetsStockDetail.getStatus() == 1) {//归还
                assetsStockDetail.setDutyPerson("");
                assetsStockDetail.setDutyPersonName("");
                assetsStockDetail.setDutyPersonDept("");
                assetsStockDetail.setUsePlace("");
                assetsStockDetail.setUsePlaceId("");
                assetsStockDetail.setBuildingId("");
                assetsStockDetail.setTypeId("");
                assetsStockDetail.setBeforeRepairStatus(1);//维修之前的状态，置为 1
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("当前时间：" + sdf.format(new Date()));
                assetsStockDetail.setReturnTime(sdf.format(new Date()));// 设置时间
            }
        }
        if( assetsStockDetail.getDel() != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            assetsStockDetail.setDelTime(sdf.format(new Date()));// 设置时间
        }

        assetsStockDetailService.updateAssetsStockDetailForNotNull(assetsStockDetail);

    }

    @PostMapping("/findAssetsUseRecordById")
    @ApiOperation(value = "资产使用记录", notes = "返回单个资产库存明细,没有时为空")
    public List<AssetsStockDetail> findAssetsUseRecordById(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        return assetsStockDetailService.findAssetsUseRecordById(assetsStockDetail);
    }

    @PostMapping("/findAssetsUseRecordCountById")
    @ApiOperation(value = "资产使用记录", notes = "返回单个资产库存明细,没有时为空")
    public long findAssetsUseRecordCountById(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        return assetsStockDetailService.findAssetsUseRecordCountById(assetsStockDetail);
    }

    @PostMapping("/getAssetsQrcodeMsg")
    @ApiOperation(value = "生成多个二维码之前的判断", notes = "返回消息")
    public AssetsQrcodeMsg getAssetsQrcodeMsg(
            @ApiParam(value = "生成多个二维码的ids")
            @RequestBody List<String> assetsResIds) {
        //查询数据库，判断马上下载或者需要生成
        int count = assetsResQrcodeService.findAssetsResQrcodeCountByAssetsResIds(assetsResIds);

        if(count!=assetsResIds.size()){
            return new AssetsQrcodeMsg("正在生成二维码，请稍后",true);
        }else{
            return new AssetsQrcodeMsg("二维码生成完毕",false);
        }
    }



    @PostMapping("/createQrCodes")
    @ApiOperation(value = "生成多个二维码", notes = "返回单个所有资产物品 （最小的单位）,没有时为空")
    public AssetsQrcodeMsg createQrCodes(
            @ApiParam(value = "生成多个二维码")
            @RequestBody List<String> assetsResIds) {
        //查询数据库，判断马上下载或者需要生成
        int count = assetsResQrcodeService.findAssetsResQrcodeCountByAssetsResIds(assetsResIds);
        if(count!=assetsResIds.size()){
            assetsResQrcodeService.createQrCodes(assetsResIds);
            return new AssetsQrcodeMsg("正在生成二维码，请稍后",true);
        }else{
            return new AssetsQrcodeMsg("二维码生成完毕",false);
        }
    }

    @PostMapping("/findAssetsResQrcodes")
    @ApiOperation(value = "下载多个二维码", notes = "返回二维码集合")
    public List<AssetsResQrcode> findAssetsResQrcodes(@ApiParam(value = "生成多个二维码")
                                                      @RequestBody List<String> assetsResIds){
        return assetsResQrcodeService.findAssetsResQrcodes(assetsResIds);
    }

//    @PostMapping("/findSemesterListByCondition")
//    public List<Semester> findSemesterListByCondition(@ApiParam(value = "获取学期")
//                                                      @RequestBody AssetsStockDetail assetsStockDetail){
//
//        return assetsStockDetailService.findSemesterListByCondition(assetsStockDetail);
//    }


    @PostMapping("/updateAssetsStockDetailForNotNullByCondition")
    @ApiOperation(value = "修改资产库存明细所有非@Transient注释的字段", notes = "资产库存明细对象必传")
    public void updateAssetsStockDetailForNotNullByCondition(
            @ApiParam(value = "资产库存明细对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsStockDetail assetsStockDetail){
        assetsStockDetailService.updateAssetsStockDetailForNotNullByCondition(assetsStockDetail);

    }

    @PostMapping("/findAssetsUsePercentage")
    @ApiOperation(value = "查询资产使用百分比", notes = "资产库存明细对象必传")
    public String findAssetsUsePercentage(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        return assetsStockDetailService.findAssetsUsePercentage(assetsStockDetail);
    }


    @PostMapping("/findRecentOneYearAssertsCount")
    @ApiOperation(value = "查询最近一年资产使用数量", notes = "资产库存明细对象必传")
    public List<AssetsStockDetail> findRecentOneYearAssertsCount(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        return assetsStockDetailService.findRecentOneYearAssertsCount(assetsStockDetail);
    }

    @PostMapping("/findRecentOneMonthAssertsCount")
    @ApiOperation(value = "查询最近一个月资产使用数量", notes = "资产库存明细对象必传")
    public List<AssetsStockDetail> findRecentOneMonthAssertsCount(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        return assetsStockDetailService.findRecentOneMonthAssertsCount(assetsStockDetail);
    }

    @PostMapping("/findFileTotalPrice")
    @ApiOperation(value = "查询某个学校的各种档案总价", notes = "资产库存明细对象必传")
    public List<AssetsFile> findFileTotalPrice(
            @ApiParam(value = "资产库存明细对象")
            @RequestBody AssetsStockDetail assetsStockDetail){
        return assetsStockDetailService.findFileTotalPrice(assetsStockDetail);
    }

}
