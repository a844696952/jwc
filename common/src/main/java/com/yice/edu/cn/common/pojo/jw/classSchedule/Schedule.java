package com.yice.edu.cn.common.pojo.jw.classSchedule;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 提供课程导出
 * 周一至周日
 */
@Data
public class Schedule implements Serializable {
    @Excel(name = "节数")
    private String num;
    @Excel(name = "周一", height = 20.00 , width=20.00)
    private String one;
    @Excel(name = "周二", height = 20.00 , width=20.00)
    private String two;
    @Excel(name = "周三", height = 20.00 , width=20.00)
    private String three;
    @Excel(name = "周四", height = 20.00 , width=20.00)
    private String four;
    @Excel(name = "周五", height = 20.00 , width=20.00)
    private String five;
    @Excel(name = "周六", height = 20.00 , width=20.00)
    private String six;
    @Excel(name = "周日", height = 20.00 , width=20.00)
    private String seven;


}
