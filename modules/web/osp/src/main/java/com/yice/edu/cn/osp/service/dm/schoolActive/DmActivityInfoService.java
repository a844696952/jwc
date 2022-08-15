package com.yice.edu.cn.osp.service.dm.schoolActive;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.util.PoiMergeCellUtil;
import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamInfo;
import com.yice.edu.cn.common.pojo.dm.schoolActive.*;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.util.ExcelStyleUtil;
import com.yice.edu.cn.osp.feignClient.dd.DdFeign;
import com.yice.edu.cn.osp.feignClient.dm.schoolActive.DmActivityInfoFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class DmActivityInfoService {
    @Autowired
    private DmActivityInfoFeign dmActivityInfoFeign;

    @Autowired
    private DdFeign ddFeign;

    public DmActivityInfo findDmActivityInfoById(String id) {
        return dmActivityInfoFeign.findDmActivityInfoById(id);
    }

    public void saveDmActivityInfo(DmActivityInfo dmActivityInfo) {
        dmActivityInfoFeign.saveDmActivityInfo(dmActivityInfo);
    }

    public List<DmActivityInfo> findDmActivityInfoListByCondition(DmActivityInfo dmActivityInfo) {
        return dmActivityInfoFeign.findDmActivityInfoListByCondition(dmActivityInfo);
    }

    public DmActivityInfo findOneDmActivityInfoByCondition(DmActivityInfo dmActivityInfo) {
        return dmActivityInfoFeign.findOneDmActivityInfoByCondition(dmActivityInfo);
    }

    public long findDmActivityInfoCountByCondition(DmActivityInfo dmActivityInfo) {
        return dmActivityInfoFeign.findDmActivityInfoCountByCondition(dmActivityInfo);
    }

    public void updateDmActivityInfo(DmActivityInfo dmActivityInfo) {
        dmActivityInfoFeign.updateDmActivityInfo(dmActivityInfo);
    }

    public void deleteDmActivityInfo(String id) {
        dmActivityInfoFeign.deleteDmActivityInfo(id);
    }

    public void deleteDmActivityInfoByCondition(DmActivityInfo dmActivityInfo) {
        dmActivityInfoFeign.deleteDmActivityInfoByCondition(dmActivityInfo);
    }

    public List<Dd> findDdListBySchoolId(String schoolId) {
        return ddFeign.findDdListBySchoolId(schoolId);
    }

    public DmActivityInfo findDmActivityInfoByActivityId(String activityId) {
        return dmActivityInfoFeign.findDmActivityInfoByActivityId(activityId);
    }

    public boolean checkGradeSingUp(DmActivityInfo dmActivityInfo) {
        return dmActivityInfoFeign.checkGradeSingUp(dmActivityInfo);
    }

    public Map checkItemSingUp(String itemId) {
        return dmActivityInfoFeign.checkItemSingUp(itemId);
    }


    public ExportActiveByClass findClassItemByActiveId(DmActivityInfo dmActivityInfo){
       return dmActivityInfoFeign.findClassItemByActiveId(dmActivityInfo);
    }

    public ExportActiveByItem findItemByActiveId(DmActivityInfo dmActivityInfo){
      return    dmActivityInfoFeign.findItemByActiveId(dmActivityInfo);
    }


    /**
     * 根据班级导出活动信息
     * @param dmActivityInfo
     */
    public Workbook findClassItemByExportActiveId(DmActivityInfo dmActivityInfo){
        ExportActiveByClass classItemByActiveId = dmActivityInfoFeign.findClassItemByActiveId(dmActivityInfo);
        if(Objects.nonNull(classItemByActiveId) && CollUtil.isNotEmpty(classItemByActiveId.getActivityClassList())){
            ExportParams exportParams = new ExportParams(classItemByActiveId.getActivityName(), "活动报名列表按班级");
            exportParams.setStyle(ExcelStyleUtil.class);
            HSSFWorkbook workbook = (HSSFWorkbook) ExcelExportUtil.exportExcel(exportParams, ActivityItemClass.class,classItemByActiveId.getActivityClassList());
           return setExcelHead(null,classItemByActiveId,workbook,1);
        }
        return null;
    }


    /**
     * 导出活动信息根据项目
     * @param dmActivityInfo
     * @return
     */
    public Workbook findItemByExportActiveId(DmActivityInfo dmActivityInfo){
        ExportActiveByItem itemByActiveId = dmActivityInfoFeign.findItemByActiveId(dmActivityInfo);
        if(Objects.nonNull(itemByActiveId) && CollUtil.isNotEmpty(itemByActiveId.getActivityItemList())){
            ExportParams exportParams = new ExportParams(itemByActiveId.getActivityName(), "活动报名列表按项目");
            exportParams.setStyle(ExcelStyleUtil.class);
            HSSFWorkbook workbook = (HSSFWorkbook) ExcelExportUtil.exportExcel(exportParams, ActivityItem.class,itemByActiveId.getActivityItemList());
            return setExcelHead(itemByActiveId,null, workbook,2);
        }
        return null;
    }

    /**
     * 根据不同类型设置excel表头
     * @param itemByActiveId
     * @param exportActiveByClass
     * @param workbook
     * @param type
     */
    private Workbook setExcelHead(ExportActiveByItem itemByActiveId,ExportActiveByClass exportActiveByClass, HSSFWorkbook workbook,int type) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        sheet.shiftRows(1,sheet.getLastRowNum(),4,true  ,false);
        HSSFFont bigFont = getBigFont(workbook);
        HSSFFont smallFont = getSmallFont(workbook);
        HSSFRow rowEnd = sheet.createRow(1);
        CellRangeAddress rangeEnd=new CellRangeAddress(1,1,0,3);
        sheet.addMergedRegion(rangeEnd);
        rowEnd.setHeightInPoints(20);
        CellRangeAddress rangeCustomize=new CellRangeAddress(2,2,0,3);
        sheet.addMergedRegion(rangeCustomize);
        HSSFRow rowIsCustomize = sheet.createRow(2);
        HSSFCell celleEnd = rowEnd.createCell(0);
        HSSFCell cellCustomize = rowIsCustomize.createCell(0);
        String result;
        HSSFRow rowActivity = sheet.createRow(3);
        CellRangeAddress rangeActivity=new CellRangeAddress(3,4,0,3);
        sheet.addMergedRegion(rangeActivity);
        HSSFCell cellActivity = rowActivity.createCell(0);
        String content="活动介绍:";
        String contentText;
        String endDate;
        if(type == 1){
            endDate="报名截止日期:"+exportActiveByClass.getEndDate();
            result=exportActiveByClass.getIsCustomize()==1?"是":"否";
            content+=exportActiveByClass.getContent();
            contentText=exportActiveByClass.getContent();
        }else{
            endDate="报名截止日期:"+itemByActiveId.getEndDate();
            result=itemByActiveId.getIsCustomize()==1?"是":"否";
            content+=itemByActiveId.getContent();
            contentText=itemByActiveId.getContent();
        }
        celleEnd.setCellValue(endDate);
        cellCustomize.setCellValue("是否可自定义项目/节目:"+result);
        HSSFRichTextString richActivity = new HSSFRichTextString(content);
        richActivity.applyFont(0,4,bigFont);
        richActivity.applyFont(5,content.length(),smallFont);
        cellActivity.setCellValue(richActivity);
       return workbook;
    }

    private HSSFFont getBigFont(HSSFWorkbook workbook) {
        HSSFFont bigFont = workbook.createFont();
        bigFont.setBold(true);
        bigFont.setFontHeightInPoints((short) 13);
        return bigFont;
    }

    private HSSFFont getSmallFont(HSSFWorkbook workbook) {
        HSSFFont smallFont = workbook.createFont();
        smallFont.setFontHeightInPoints((short) 10);
        return smallFont;
    }


}
