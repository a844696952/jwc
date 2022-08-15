package com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class YcDataReceiveResBean {

    private String  returnCode;
    private String  returnMessage;
    private HashMap bean;
    private List beans;
    private List examples;
    private String accountId;
    private String token;
    private String schoolId;
    private String schoolName;
    private YcVerifaceDoorChangeResBean peopleListChangBean;
}
