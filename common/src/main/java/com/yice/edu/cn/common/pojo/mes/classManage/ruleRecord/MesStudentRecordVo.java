package com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord;

import lombok.Data;

/**
 * 电子班牌班级管理学生列表
 */
@Data
public class MesStudentRecordVo {
    private String studentId;//学生id
    private String name;//学生姓名
    private String imgUrl;//学生头像地址
    private Integer addScoreSum;//学生当日加分总和
    private Integer reduceScoreSum;//学生当日扣分总和
    private String initial;//学生姓氏首字母
    private String openId;//学生关联家长的openId
}
