package com.yice.edu.cn.common.pojo.jw.teacher;

import lombok.Data;

/**
 * 该对象提供给班级获取任课老师列表
 */
@Data
public class Teacher4Classes {
    private String id;
    private String name;
    private String imgUrl;
    private String tel;
    private String post;
    private String courseName;
}
