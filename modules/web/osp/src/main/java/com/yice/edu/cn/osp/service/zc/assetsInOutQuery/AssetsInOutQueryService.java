package com.yice.edu.cn.osp.service.zc.assetsInOutQuery;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.yice.edu.cn.common.pojo.xw.zc.assertsInOutQuery.AssetsInOutListExcel;
import com.yice.edu.cn.common.pojo.xw.zc.assertsInOutQuery.AssetsUseListExcel;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockQuery.AssetsStockListExcel;
import com.yice.edu.cn.osp.feignClient.zc.assetsInOutQuery.AssetsInOutQueryFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetsInOutQueryService {
    @Autowired
    private AssetsInOutQueryFeign assetsInOutQueryFeign;

    public List<AssetsFile> findInOutQueryByCondition(AssetsFile assetsFile){
        return assetsInOutQueryFeign.findInOutQueryByCondition(assetsFile);
    }

    public long findInOutQueryCountByCondition(AssetsFile assetsFile){
        return assetsInOutQueryFeign.findInOutQueryCountByCondition(assetsFile);
    }

    public List<AssetsFile> findAssetUseDataByCondition(AssetsFile assetsFile){
        return assetsInOutQueryFeign.findAssetUseDataByCondition(assetsFile);
    }

    public long findAssetUseDataCountByCondition(AssetsFile assetsFile){
        return assetsInOutQueryFeign.findAssetUseDataCountByCondition(assetsFile);
    }

    public Workbook exportAssetsInOutExcel(List<AssetsFile> assetsFiles){
        List<AssetsInOutListExcel> listExcels = assetsFiles.stream().map(ss -> {
            AssetsInOutListExcel excel = new AssetsInOutListExcel();
            BeanUtils.copyProperties(ss, excel);
            excel.setAssetsProperty(ss.getAssetsProperty() == 1 ? "固定资产" : "易耗品");
            return excel;
        }).collect(Collectors.toList());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "资产出入库统计"), AssetsInOutListExcel.class, listExcels);
        return workbook;
    }

    public Workbook exportAssetsUseExcel(List<AssetsFile> assetsFiles) {
        List<AssetsUseListExcel> listExcels = assetsFiles.stream().map(ss -> {
            AssetsUseListExcel excel = new AssetsUseListExcel();
            BeanUtils.copyProperties(ss, excel);
            excel.setAssetsProperty(ss.getAssetsProperty() == 1 ? "固定资产" : "易耗品");
            return excel;
        }).collect(Collectors.toList());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "资产出入库统计"), AssetsUseListExcel.class, listExcels);
        return workbook;
    }
}
