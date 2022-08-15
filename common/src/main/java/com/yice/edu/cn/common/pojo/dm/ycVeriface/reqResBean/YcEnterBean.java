package com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean;

import lombok.Data;

@Data
public class YcEnterBean {

    private String userID;
    private String schoolId;
    private String img_base64;//务必传base64字符串格式图片
    private String index;//人员匹配位序
}
