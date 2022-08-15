package com.yice.edu.cn.tap.feignClient.ycveriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="ws",contextId = "wsYcVerifaceFeign",path = "/wsYcVeriface")
public interface WsYcVerifaceFeign {
    @PostMapping("/toldDevicePeopleHaveChange")
    void toldDevicePeopleHaveChange(YcVerifaceDevice userList);
}
