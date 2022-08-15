package com.yice.edu.cn.common.pojo.ts.jMessage;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * 业务需求返回的对象
 *
 */
@Data
public class UserRigisterApiResult{
	
    @Expose String username;
    @Expose JsonObject error;

    public String getUsername() {
        return username;
    }

    public JsonObject getError() {
        return error;
    }

    public boolean hasError() {
        return null != error;
    }

    public String getErrorMessage() {
        if(null != error) {
            return error.get("message").getAsString();
        }
        return null;
    }

    public int getErrorCode() {
        if(null != error) {
            return error.get("code").getAsInt();
        }
        return -1;
    }
	

}
