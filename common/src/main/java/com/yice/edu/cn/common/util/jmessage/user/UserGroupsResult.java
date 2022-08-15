package com.yice.edu.cn.common.util.jmessage.user;

import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;
import com.yice.edu.cn.common.util.jmessage.group.GroupInfoResult;

public class UserGroupsResult extends BaseResult{

    private GroupInfoResult[] groups = null;

    public static UserGroupsResult fromResponse(ResponseWrapper responseWrapper) {
        UserGroupsResult  result = new UserGroupsResult();
        if (responseWrapper.isServerResponse()) {
            result.groups = _gson.fromJson(responseWrapper.responseContent, GroupInfoResult[].class);
        } else {
            // nothing
        }
        result.setResponseWrapper(responseWrapper);
        return result;
    }

    public GroupInfoResult[] getGroups() {
        return groups;
    }
}
