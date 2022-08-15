package com.yice.edu.cn.common.pojo.jw.appVersionControl;

import lombok.Data;

@Data
public class FileVersion {
    private Boolean success;
    private String path;
    private Double versionNumber;
    private String versionName;
}
