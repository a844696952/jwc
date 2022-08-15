package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import lombok.Data;

@Data
public class UserParam {
    private String userId;
    private int userType;
    private boolean removeRole;
    private String param;
}
