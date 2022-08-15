package com.yice.edu.cn.common.pojo.jw.teacher;

import lombok.Data;
import java.util.Date;

@Data
public class LoginErrorInfo {
    private int num;//错误次数
    private Date wrongTime;//错误时间
    private boolean isOk;//是否允许登录
}
