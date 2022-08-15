package com.yice.edu.cn.common.pojo.general.user;

import lombok.Data;

/**
 * 用户，可以教师也可以是学生
 */
@Data
public class User {
    private String id;
    private String name;
    private String sex;
    private String imgUrl;
    private String email;
    private String nationName;
    private String provinceName;
    private String cityName;
    private String countyName;
    private String address;
    private String birthDate;
    private String schoolId;
}
