package com.yice.edu.cn.gateway;

import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.pojo.Constant;
import org.junit.Test;

public class Temp {

    @Test
    public void testPwd(){
        System.out.println(DigestUtil.sha1Hex(DigestUtil.md5Hex("yceduSuccess")));
    }
}
