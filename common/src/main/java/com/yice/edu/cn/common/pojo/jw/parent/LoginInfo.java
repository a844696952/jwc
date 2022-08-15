package com.yice.edu.cn.common.pojo.jw.parent;

import lombok.Data;

import java.util.Date;

@Data
public class LoginInfo {
    private int errorNum=0;//错误次数
    private Date errorTime;//错误时间
    private boolean isLogin=true;//能否登录
}
