package com.yice.edu.cn.common.util.jmessage.user;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class UserStateResult extends BaseResult {
    @Expose Boolean login;
    @Expose Boolean online;

    public Boolean getLogin() {
        return login;
    }

    public Boolean getOnline() {
        return online;
    }
}
