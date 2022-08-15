package com.yice.edu.cn.osp.service.xw.clCustomMaterial;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterial;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterialExport;
import com.yice.edu.cn.common.util.ExcelStyleUtil;
import com.yice.edu.cn.common.util.ExcelStyleUtilFontColor;
import com.yice.edu.cn.osp.feignClient.xw.clCustomMaterial.ClCustomMaterialFeign;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClCustomMaterialService {
    @Autowired
    private ClCustomMaterialFeign clCustomMaterialFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ClCustomMaterial findClCustomMaterialById(String id) {
        return clCustomMaterialFeign.findClCustomMaterialById(id);
    }

    public ClCustomMaterial saveClCustomMaterial(ClCustomMaterial clCustomMaterial) {
        return clCustomMaterialFeign.saveClCustomMaterial(clCustomMaterial);
    }

    public void batchSaveClCustomMaterial(List<ClCustomMaterial> clCustomMaterials){
        clCustomMaterialFeign.batchSaveClCustomMaterial(clCustomMaterials);
    }

    public List<ClCustomMaterial> findClCustomMaterialListByCondition(ClCustomMaterial clCustomMaterial) {
        return clCustomMaterialFeign.findClCustomMaterialListByCondition(clCustomMaterial);
    }

    public ClCustomMaterial findOneClCustomMaterialByCondition(ClCustomMaterial clCustomMaterial) {
        return clCustomMaterialFeign.findOneClCustomMaterialByCondition(clCustomMaterial);
    }

    public long findClCustomMaterialCountByCondition(ClCustomMaterial clCustomMaterial) {
        return clCustomMaterialFeign.findClCustomMaterialCountByCondition(clCustomMaterial);
    }

    public void updateClCustomMaterial(ClCustomMaterial clCustomMaterial) {
        clCustomMaterialFeign.updateClCustomMaterial(clCustomMaterial);
    }

    public void updateClCustomMaterialForAll(ClCustomMaterial clCustomMaterial) {
        clCustomMaterialFeign.updateClCustomMaterialForAll(clCustomMaterial);
    }

    public void deleteClCustomMaterial(String id) {
        clCustomMaterialFeign.deleteClCustomMaterial(id);
    }

    public void deleteClCustomMaterialByCondition(ClCustomMaterial clCustomMaterial) {
        clCustomMaterialFeign.deleteClCustomMaterialByCondition(clCustomMaterial);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public Long findClCustomMaterialMaxWeight(ClCustomMaterial clCustomMaterial){
        return clCustomMaterialFeign.findClCustomMaterialMaxWeight(clCustomMaterial);
    }

    public Workbook exportTemplate(Integer stuOrTea){
        List<ExcelExportEntity> excelExportEntities = new ArrayList<>();
        ClCustomMaterialExport clCustomMaterialExport = new ClCustomMaterialExport();
        if(stuOrTea==1){
            excelExportEntities.add(new ExcelExportEntity("学生姓名","stuName"));
            excelExportEntities.add(new ExcelExportEntity("学号","stuCode"));
            clCustomMaterialExport.setStuName("小王哥\r\n（红字示例部分请删除）");
            clCustomMaterialExport.setStuCode("10020\r\n（红字示例部分请删除）");
        }else {
            excelExportEntities.add(new ExcelExportEntity("教师姓名","stuName"));
            excelExportEntities.add(new ExcelExportEntity("教师电话","stuCode"));
            clCustomMaterialExport.setStuName("王老师\r\n（红字示例部分请删除）");
            clCustomMaterialExport.setStuCode("13812451452\r\n（红字示例部分请删除）");
        }
        excelExportEntities.add(new ExcelExportEntity("自定义表头","custom1"));
        excelExportEntities.add(new ExcelExportEntity("自定义表头","custom2"));
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(BeanUtil.beanToMap(clCustomMaterialExport));
        excelExportEntities.forEach(f->{
            f.setHeight(20);
            f.setWidth(30);
            f.setWrap(true);
        });
       ExportParams exportParams =  new ExportParams();
       exportParams.setStyle(ExcelStyleUtilFontColor.class);
       return ExcelExportUtil.exportExcel(exportParams,excelExportEntities,list);
    }

    public List<ClCustomMaterial> findClCustomMaterialListByConditionKong(ClCustomMaterial clCustomMaterial){
        return clCustomMaterialFeign.findClCustomMaterialListByConditionKong(clCustomMaterial);
    }

    public void  saveClCustomMaterialDataAndClWeight(ClCustomMaterial clCustomMaterial){
        clCustomMaterialFeign.saveClCustomMaterialDataAndClWeight(clCustomMaterial);
    }
}
