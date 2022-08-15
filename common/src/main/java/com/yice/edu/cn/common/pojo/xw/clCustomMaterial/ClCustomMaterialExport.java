package com.yice.edu.cn.common.pojo.xw.clCustomMaterial;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.Api;
import lombok.Data;
@Api("自定义材料导入模板")
@Data
public class ClCustomMaterialExport {
    @Excel(name = "学生姓名",height = 20,width = 30)
    private String stuName;
    @Excel(name = "学籍号",height = 20,width = 30)
    private String stuCode;
    @Excel(name = "自定义表头",height = 20,width = 30)
    private String custom1;
    @Excel(name = "自定义表头",height = 20,width = 30)
    private String custom2;
    @Excel(name = "...",height = 20,width = 30)
    private String custom3;
    @Excel(name = "...",height = 20,width = 30)
    private String custom4;

}
