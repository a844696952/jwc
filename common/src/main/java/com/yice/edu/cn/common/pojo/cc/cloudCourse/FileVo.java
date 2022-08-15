package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class FileVo {
    private Integer id;
    private String name;
    private String path;
    private String target;
}
