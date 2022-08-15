package com.yice.edu.cn.common.pojo.xw.dormManage.dorm;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;


@Data
public class DormImport implements Serializable {

    @Excel(name="宿舍类型")
    private String dormType;//宿舍类型(1.男生宿舍,2.女生宿舍)
    @Excel(name="宿舍楼")
    private String dormBuildName;
    @Excel(name="楼层")
    private String floor;
    @Excel(name="宿舍名称")
    private String dormName;
    @Excel(name="床位")
    private String bunkName;
    @Excel(name="*姓名")
    private String personName;
    @Excel(name="*学号")
    private String studentNo;
    @Excel(name="*联系方式")
    private String tel;
    @Excel(name="错误信息")
    private String error;
}
