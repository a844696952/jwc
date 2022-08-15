package com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean;

import lombok.Data;
import java.util.List;

@Data
public class YcVerifaceResBean {

    private Boolean  success;
    private String  message;
    private List<Object> beans;
}
