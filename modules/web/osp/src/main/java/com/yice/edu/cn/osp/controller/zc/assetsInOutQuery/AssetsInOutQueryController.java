package com.yice.edu.cn.osp.controller.zc.assetsInOutQuery;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.osp.service.zc.assetsInOutQuery.AssetsInOutQueryService;
import com.yice.edu.cn.osp.service.zc.assetsStockDetail.AssetsStockDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/assetsInOutQuery")
@Api(value = "/assetsInOutQuery",description = "资产出入库统计模块")
public class AssetsInOutQueryController {
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;

    @Autowired
    private AssetsInOutQueryService assetsInOutQueryService;

//    @PostMapping("/find/findSemestersByCondition")
//    @ApiOperation(value = "根据条件查找学期表", notes = "返回响应对象")
//    public ResponseJson findSemestersByCondition(
//            @ApiParam(value = "属性不为空则作为条件查询")
//            @RequestBody Semester semester){
//
//        Pager pager = new Pager();
//        semester.setPager(pager);
//        List<Semester> data=assetsStockDetailService.findSemesterListByCondition(semester);
//        return new ResponseJson(data);
//    }

    @PostMapping("/find/findInOutQueryByCondition")
    @ApiOperation(value = "资产库存首页查询", notes = "返回资产档案对象", response = AssetsFile.class, responseContainer = "List")
    public ResponseJson findInOutQueryByCondition(
            @ApiParam(value = "资产档案对象")
            @Validated
            @RequestBody AssetsFile assetsFile){
        assetsFile.setSchoolId(mySchoolId());
        List<AssetsFile> assetsFileList = assetsInOutQueryService.findInOutQueryByCondition(assetsFile);
        long count = assetsInOutQueryService.findInOutQueryCountByCondition(assetsFile);
        return new ResponseJson(assetsFileList,count);
    }

    @PostMapping("/find/findAssetUseDataByCondition")
    @ApiOperation(value = "资产使用记录查询", notes = "返回资产档案对象", response = AssetsFile.class, responseContainer = "List")
    public ResponseJson findAssetUseDataByCondition(
            @ApiParam(value = "资产档案对象")
            @Validated
            @RequestBody AssetsFile assetsFile){
        assetsFile.setSchoolId(mySchoolId());
        List<AssetsFile> assetsFileList = assetsInOutQueryService.findAssetUseDataByCondition(assetsFile);
        long count = assetsInOutQueryService.findAssetUseDataCountByCondition(assetsFile);
        return new ResponseJson(assetsFileList,count);
    }

    @PostMapping("/find/exportAssetsInOutExcel")
    @ApiOperation(value = "导出excel资产库存查询页信息", notes = "返回一个excel")
    public void exportAssetsInOutExcel(
            @ApiParam(value = "资产档案对象")
            @Validated
            @RequestBody List<AssetsFile> assetsFiles,HttpServletResponse response){
        //告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=schedule.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
//            List<AssetsFile> assetsFiles1 = assetsInOutQueryService.findInOutQueryByCondition(new AssetsFile());
            Workbook workbook = assetsInOutQueryService.exportAssetsInOutExcel(assetsFiles);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/find/exportAssetsUseExcel")
    @ApiOperation(value = "导出excel资产使用页信息", notes = "返回一个excel")
    public void exportAssetsUseExcel(
            @ApiParam(value = "资产档案对象")
            @Validated
            @RequestBody List<AssetsFile> assetsFiles,HttpServletResponse response){
        //告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=schedule.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
//            List<AssetsFile> assetsFiles1 = assetsInOutQueryService.findAssetUseDataByCondition(new AssetsFile());
            Workbook workbook = assetsInOutQueryService.exportAssetsUseExcel(assetsFiles);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
