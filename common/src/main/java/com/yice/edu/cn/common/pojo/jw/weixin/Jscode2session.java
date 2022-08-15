package com.yice.edu.cn.common.pojo.jw.weixin;

import lombok.Data;

@Data
public class Jscode2session {
    private String appId;
    private String secret;
    private String grantType;
    private String jsCode;
    private Boolean needAll;//是否需要返回整个对象
}
