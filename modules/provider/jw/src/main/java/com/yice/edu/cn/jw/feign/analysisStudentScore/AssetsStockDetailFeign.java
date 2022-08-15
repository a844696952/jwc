package com.yice.edu.cn.jw.feign.analysisStudentScore;

import com.yice.edu.cn.common.pojo.xw.zc.AssetsQrcodeMsg;
import com.yice.edu.cn.common.pojo.xw.zc.AssetsResQrcode;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsStockDetailFeign",path = "/assetsStockDetail")
public interface AssetsStockDetailFeign {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findAssetsStockDetailById/{id}")
    AssetsStockDetail findAssetsStockDetailById(@PathVariable("id") String id);
    @PostMapping("/saveAssetsStockDetail")
    AssetsStockDetail saveAssetsStockDetail(AssetsStockDetail assetsStockDetail);
    @PostMapping("/findAssetsStockDetailListByCondition")
    List<AssetsStockDetail> findAssetsStockDetailListByCondition(AssetsStockDetail assetsStockDetail);
    @PostMapping("/findOneAssetsStockDetailByCondition")
    AssetsStockDetail findOneAssetsStockDetailByCondition(AssetsStockDetail assetsStockDetail);
    @PostMapping("/findAssetsStockDetailCountByCondition")
    long findAssetsStockDetailCountByCondition(AssetsStockDetail assetsStockDetail);
    @PostMapping("/updateAssetsStockDetail")
    void updateAssetsStockDetail(AssetsStockDetail assetsStockDetail);
    @PostMapping("/updateAssetsStockDetailForNotNull")
    void updateAssetsStockDetailForNotNull(AssetsStockDetail assetsStockDetail);
    @GetMapping("/deleteAssetsStockDetail/{id}")
    void deleteAssetsStockDetail(@PathVariable("id") String id);
    @PostMapping("/deleteAssetsStockDetailByCondition")
    void deleteAssetsStockDetailByCondition(AssetsStockDetail assetsStockDetail);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @GetMapping("/findAssetsStockDetailJoinTableById/{id}")
    AssetsStockDetail findAssetsStockDetailJoinTableById(@PathVariable("id") String id);

    @PostMapping("/findAssetsStockDetailListByFuzzyCondition")
    List<AssetsStockDetail> findAssetsStockDetailListByFuzzyCondition(AssetsStockDetail assetsStockDetail);

    @PostMapping("/findAssetsStockDetailCountByFuzzyCondition")
    long findAssetsStockDetailCountByFuzzyCondition(AssetsStockDetail assetsStockDetail);

    //资产使用记录查询
    @PostMapping("/findAssetsUseRecordById")
    List<AssetsStockDetail> findAssetsUseRecordById(AssetsStockDetail assetsStockDetail);

    //资产使用记录条数
    @PostMapping("/findAssetsUseRecordCountById")
    long findAssetsUseRecordCountById(AssetsStockDetail assetsStockDetail);

    @PostMapping("/getAssetsQrcodeMsg")
    AssetsQrcodeMsg getAssetsQrcodeMsg(List<String> assetsResIds);

    @PostMapping("/createQrCodes")
    AssetsQrcodeMsg createQrCodes(List<String> assetsResIds);

    @PostMapping("/findAssetsResQrcodes")
    List<AssetsResQrcode> findAssetsResQrcodes(List<String> ids);

//    @PostMapping("/findSemesterListByCondition")
//    List<Semester> findSemesterListByCondition(Semester semester);

    @PostMapping("/updateAssetsStockDetailByconfition")
    void updateAssetsStockDetailByconfition(AssetsStockDetail assetsStockDetail);

    @PostMapping("/updateAssetsStockDetailForNotNullByCondition")
    void updateAssetsStockDetailForNotNullByCondition(AssetsStockDetail assetsStockDetail);

    @PostMapping("/findAssetsUsePercentage")
    String findAssetsUsePercentage(AssetsStockDetail assetsStockDetail);

    @PostMapping("/findRecentOneYearAssertsCount")
    List<AssetsStockDetail> findRecentOneYearAssertsCount(AssetsStockDetail assetsStockDetail);

    @PostMapping("/findRecentOneMonthAssertsCount")
    List<AssetsStockDetail> findRecentOneMonthAssertsCount(AssetsStockDetail assetsStockDetail);

    @PostMapping("/findFileTotalPrice")
    List<AssetsFile> findFileTotalPrice(AssetsStockDetail assetsStockDetail);

}
