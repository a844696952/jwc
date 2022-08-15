package com.yice.edu.cn.common.util.jmessage.resource;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class DownloadResult extends BaseResult {

    @Expose String url;

    public String getUrl() {
        return url;
    }
}
