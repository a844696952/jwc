package com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean;

import lombok.Data;
@Data
public class YcVerifacePersonBean {
   private String userId;
   private String result;
   private String similarity;//相似度
   private String index;//校验位序
   private String personType;//人员类型
   private Double[] fea_128;//特征值
}
