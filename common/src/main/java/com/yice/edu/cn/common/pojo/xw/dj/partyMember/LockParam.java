package com.yice.edu.cn.common.pojo.xw.dj.partyMember;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dengfengfeng
 */
@Data
public class LockParam {

    private boolean lock;

    private String password;

    public String getPasswordOrDefault(){
        return StringUtils.isNotBlank(password) ?password:"123456";
    }

}
