package com.yice.edu.cn.osp.controller.zc.assetsStockQuery;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockQuery.AssetsStockGatherDto;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.osp.service.zc.assetsFile.AssetsFileService;
import com.yice.edu.cn.osp.service.zc.assetsStockQuery.AssetsStockQueryService;
import com.yice.edu.cn.osp.service.zc.repairNew.RepairNewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/assetsStockQuery")
@Api(value = "/assetsStockQuery",description = "资产库存查询模块")
public class AssetsStockQuery {
    @Autowired
    private AssetsStockQueryService assetsStockQueryService;
    @Autowired
    private AssetsFileService assetsFileService;
    @Autowired
    private RepairNewService repairNewService;

    @GetMapping("/find/findAssetsStockGather")
    @ApiOperation(value = "资产库存查询学校资产汇总计算数据", notes = "返回汇总数据对象", response=AssetsStockGatherDto.class)
    public ResponseJson findAssetsStockGather(){
        AssetsStockGatherDto assetsStockGather = assetsStockQueryService.findAssetsStockGather(mySchoolId());
        RepairNew repairNew = new RepairNew();
        repairNew.setSchoolId(mySchoolId());
        double costsTotal = repairNewService.findRepairNewUpkeepCostsBySchool(repairNew);
        assetsStockGather.setUpkeepCostsTotal(BigDecimal.valueOf(costsTotal));
        return new ResponseJson(assetsStockGather);
    }

    @PostMapping("/find/findAssetsStockListByCondition")
    @ApiOperation(value = "资产库存首页查询", notes = "返回资产档案对象", response = AssetsFile.class, responseContainer = "List")
    public ResponseJson findAssetsStockListByCondition(
            @ApiParam(value = "资产档案对象")
            @Validated
            @RequestBody AssetsFile assetsFile){
        assetsFile.setSchoolId(mySchoolId());
        List<AssetsFile> list = assetsStockQueryService.findAssetsStockListByCondition(assetsFile);
        long count = assetsStockQueryService.findAssetsStockListCountByCondition(assetsFile);
        return new ResponseJson(list,count);
    }

    @PostMapping("/find/exportAssetsStockExcel")
    @ApiOperation(value = "导出excel资产库存查询页信息", notes = "返回一个excel")
    public void exportAssetsStockExcel(
            @ApiParam(value = "资产档案对象")
            @Validated
            @RequestBody List<AssetsFile> assetsFiles,HttpServletResponse response){
        //告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=schedule.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = assetsStockQueryService.exportAssetsStockExcel(assetsFiles);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/updateAssetsThresholdValue")
    @ApiOperation(value = "设置资产预警数量", notes = "返回响应对象")
    public ResponseJson updateAssetsThresholdValue(@RequestBody AssetsFile assetsFile){
        assetsFile.setSchoolId(mySchoolId());
        assetsFileService.updateAssetsFile(assetsFile);
        return new ResponseJson();
    }
}
