package com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class YcVerifaceDoorChangeResBean {

    private List<YcVerifacePersonBean> resetList =new ArrayList() ;
    private List<YcVerifacePersonBean> insetList =new ArrayList() ;
    private List<YcVerifacePersonBean> deleteList =new ArrayList() ;
    private List<YcVerifacePersonBean> updateList =new ArrayList() ;
}
