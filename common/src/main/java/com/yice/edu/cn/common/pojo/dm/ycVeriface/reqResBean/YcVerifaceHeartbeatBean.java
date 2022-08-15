package com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import lombok.Data;

import java.util.List;

@Data
public class YcVerifaceHeartbeatBean {
   private String accountId;
   private List<YcVerifaceDevice> beans;
}
