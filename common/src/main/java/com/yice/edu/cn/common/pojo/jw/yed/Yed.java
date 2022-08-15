package com.yice.edu.cn.common.pojo.jw.yed;

import lombok.Data;

/**
 * 大屏幕上的新生人数占比和教育装备部的场地数量
 */

@Data
public class Yed {
    private String area;//地区名
    private Double rate;//人数占比
    private Double changes;//占比变化
    private Integer last;//今年人数
    private Integer year;//去年人数
    private String educationBureauId;//教育局角色Id
    private Integer gradeId;//年级Id
    private Integer typeId;//类型Id(初中121，高中122，小学120)
    private Integer admissionDate;//入学时间(年份)
    private Integer value;//场地类型数量
    private String name;//场地类型名称


}
