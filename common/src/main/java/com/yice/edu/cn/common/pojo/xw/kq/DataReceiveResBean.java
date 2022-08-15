package com.yice.edu.cn.common.pojo.xw.kq;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.common.pojo.xw.kq
 * @Author: Administrator
 * @CreateTime: 2019-03-01 10:30
 * @Description: 中移数据接收响应对象
 */
@Data
public class DataReceiveResBean {

    private String  returnCode;
    private String  returnMessage;
    private HashMap bean;
    private List beans;
}
