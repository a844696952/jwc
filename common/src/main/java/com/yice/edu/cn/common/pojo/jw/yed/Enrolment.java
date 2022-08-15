package com.yice.edu.cn.common.pojo.jw.yed;
import lombok.Data;

@Data
public class Enrolment {
    private String area;//地区名
    private Double rate;//升学率
    private Integer graduationCount; //升学人数
    private Integer studentCount;   //升学前总人数
    private String educationBureauId;//教育局角色Id
    private Integer gradeId;//年级Id
    private Integer typeId;//类型Id(初中121，高中122，小学120)
    private String enrolmentYear; //升学时间
}
