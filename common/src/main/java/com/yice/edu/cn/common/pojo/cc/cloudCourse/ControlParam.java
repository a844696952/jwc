package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import lombok.Data;

@Data
public class ControlParam {
    //1->开 0->关
    private Integer command;
    private String param;
    private Long padUID;
    private String target;
}
