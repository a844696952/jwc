package com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class YcVerifaceOriginDataBatchReceiveBean {

    private String  schoolID;
    private String  catchDate;
    private List<YcOriginalDataBean> beans;
}
