package com.yice.edu.cn.osp.service.zc.assetsStockQuery;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockQuery.AssetsStockGatherDto;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockQuery.AssetsStockListExcel;
import com.yice.edu.cn.osp.feignClient.zc.assetsStockQuery.AssetsStockQueryFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AssetsStockQueryService {
    @Autowired
    private AssetsStockQueryFeign assetsStockQueryFeign;

    public AssetsStockGatherDto findAssetsStockGather(String schoolId){
        return assetsStockQueryFeign.findAssetsStockGather(schoolId);
    }

    public List<AssetsFile> findAssetsStockListByCondition(AssetsFile assetsFile){
        return assetsStockQueryFeign.findAssetsStockListByCondition(assetsFile);
    }

    public long findAssetsStockListCountByCondition(AssetsFile assetsFile){
        return assetsStockQueryFeign.findAssetsStockListCountByCondition(assetsFile);
    }

    public Workbook exportAssetsStockExcel(List<AssetsFile> assetsFiles){
        List<AssetsStockListExcel> listExcels = assetsFiles.stream().map(ss -> {
            AssetsStockListExcel excel = new AssetsStockListExcel();
            BeanUtils.copyProperties(ss, excel);
            excel.setAssetsProperty(ss.getAssetsProperty()==1 ? "固定资产" : "易耗品");
            excel.setIsWarning(ss.getIsWarning()==1 ? "是" : "否");
            return excel;
        }).collect(Collectors.toList());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "资产库存"), AssetsStockListExcel.class, listExcels);
        return workbook;
    }
}
