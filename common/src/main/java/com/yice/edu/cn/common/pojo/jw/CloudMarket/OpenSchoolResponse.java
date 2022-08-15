package com.yice.edu.cn.common.pojo.jw.CloudMarket;

import lombok.Data;

@Data
public class OpenSchoolResponse {
    private String resultCode;
    private String resultMsg;
    private String encryptType;
    private String instanceId;
    private AppInfo appInfo;
    @Data
    public static class AppInfo{
        private String frontEndUrl;
        private String adminUrl;
        private String userName;
        private String password;
        private String ip;
        private String memo;
    }
}
