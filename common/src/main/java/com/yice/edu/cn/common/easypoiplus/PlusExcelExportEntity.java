package com.yice.edu.cn.common.easypoiplus;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengfengfeng
 * 加强版的ExcelExportEntity，主要增加了一个lock锁列的标记
 */
public class PlusExcelExportEntity extends ExcelExportEntity {

    private boolean lock;

    private List<PlusExcelExportEntity> plusList;


    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public List<PlusExcelExportEntity> getPlusList() {
        return plusList;
    }

    public void setPlusList(List<PlusExcelExportEntity> plusList) {
        this.plusList = plusList;
    }

    public ExcelExportEntity excelExportEntity(){
//        ExcelExportEntity excelExportEntity = new ExcelExportEntity();
//        excelExportEntity = (ExcelExportEntity)this;
//        excelExportEntity.setKey(super.getKey());
//        excelExportEntity.setWidth(super.getWidth());
//        excelExportEntity.setHeight(super.getHeight());
//        excelExportEntity.setExportImageType(super.getExportImageType());
//        excelExportEntity.setOrderNum(super.getOrderNum());
//        excelExportEntity.setWrap(super.isWrap());
//        excelExportEntity.setNeedMerge(super.isNeedMerge());
//        excelExportEntity.setMergeVertical(super.isMergeVertical());
//        excelExportEntity.setMergeRely(super.getMergeRely());
//        excelExportEntity.setColumnHidden(super.isColumnHidden());
        return (ExcelExportEntity)this;

    }

    public List<ExcelExportEntity> superList(){
        return plusList.stream().map(PlusExcelExportEntity::excelExportEntity).collect(Collectors.toList());
    }
}
